package artezio.cardio

import java.text.SimpleDateFormat
import java.util
import java.util.{Calendar, Date}

import com.mongodb.Mongo
import com.mongodb.casbah.Imports._
import org.apache.hadoop.conf.Configuration
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}
import org.bson.{BasicBSONObject, BSONObject}

import scala.util.parsing.json.JSONObject

/**
 * Created by mkuznetsova on 12/8/2015.
 */
object StressZones extends App {
  println("!!! Start !!!")
  val format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")

  val sparkConf = new SparkConf().setAppName("stress_zones")//.setMaster("local[2]")
  val sc = new SparkContext(sparkConf)

  val numClusters = 5
  val numIterations = 20

  val mongoConfig = new Configuration()
//  mongoConfig.set("mongo.input.uri", "mongodb://192.168.1.32:27017/cardio.data")
  mongoConfig.set("mongo.input.uri", "mongodb://192.168.1.32:27017/cardio_test_db.pulse_data_test")
  mongoConfig.set("mongo.job.input.format", "com.mongodb.hadoop.MongoInputFormat")

  val mongoRDD = sc.newAPIHadoopRDD(mongoConfig, classOf[com.mongodb.hadoop.MongoInputFormat], classOf[Object], classOf[BSONObject])
  val bsons = mongoRDD.values

  val calendar = Calendar.getInstance();
  calendar.add(Calendar.HOUR, -24);
  val earlyDate = calendar.getTime();

 // val filtredBsons = bsons.filter(b => Integer.decode(b.get("pulse").toString) > 70 && (format.parse(b.get("dt").toString)).after(earlyDate))
 // val filtredBsons = bsons.filter(b => Integer.decode(b.get("pulse").toString) > 70 && (format.parse(b.get("date").toString)).before(new Date()))
   val filtredBsons = bsons.filter(b => Integer.decode(b.get("heartRateMeasurement").toString) > 70
                                        && b.get("longitude")!=null
                                        && b.get("latitude")!=null
                                       )


  val vectors = filtredBsons.map(s => Vectors.dense(Array(Double.unbox(s.get("longitude")), Double.unbox(s.get("latitude"))))).cache()
//  val vectors = filtredBsons.map(s => Vectors.dense(Array(Double.unbox(s.get("location_lan")), Double.unbox(s.get("location_lon"))))).cache()

  println("--------------------------- KMeans start ---------------------")
  val clusters = KMeans.train(vectors, numClusters, numIterations)
  println("--------------------------- KMeans end  -----------------------")

  val vector = clusters.clusterCenters


  var mongo = new Mongo("192.168.1.32")
 // val mongo = new Mongo("localhost")
  //val db = mongo.getDB("cardio")
  val db = mongo.getDB("cardio_test_db")
  val coll = db.getCollection("stress")

  val obj = new BasicDBObject()
  obj.put("date", new Date())
   val list = new util.ArrayList[BasicDBObject]()

  vector.map(s => {
    val loc = new BasicDBObject()
    loc.put("lon",s.toArray.apply(0).toString)
    loc.put("lat",s.toArray.apply(1).toString)
    list.add(loc)
  })

  obj.put("locations", list)

  coll.insert(obj)
  coll.save(obj)

  println("!!! Finish !!!")
}


