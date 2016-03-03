package artezio.mkuznetsova

import java.util.Date

import com.mongodb.Mongo
import com.mongodb.casbah.Imports._
import org.apache.hadoop.conf.Configuration
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}
import org.bson.BSONObject

/**
 * Created by mkuznetsova on 12/8/2015.
 */
object MainObject extends App {
  println("!!! Start !!!")

  val sparkConf = new SparkConf().setAppName("stress_zones").setMaster("local[2]")
  val sc = new SparkContext(sparkConf)

  val numClusters = 5
  val numIterations = 20

  /* Kmeans from texp file */
  //  val pulse3 = sc.textFile("D:/bigdata_pulse3.cvs")
  //  val parsedDate3 = pulse3.map(s => Vectors.dense(s.split(' ').map(_.toDouble))).cache()
  //  val clusters3 = KMeans.train(parsedDate3, numClusters, numIterations)


  /** ******  MONGO ****/
  val mongoConfig = new Configuration()
  //mongoConfig.set("mongo.input.uri", "mongodb://192.168.1.32:27017/cardio.user_date")
  mongoConfig.set("mongo.input.uri", "mongodb://localhost:27017/cardio.user_date")
  mongoConfig.set("mongo.job.input.format", "com.mongodb.hadoop.MongoInputFormat")

  val mongoRDD = sc.newAPIHadoopRDD(mongoConfig, classOf[com.mongodb.hadoop.MongoInputFormat], classOf[Object], classOf[BSONObject])
  val bsons = mongoRDD.values
  val filtredBsons = bsons.filter(b => Double.unbox(b.get("pulse")) > 70)

  val vectors = filtredBsons.map(s => Vectors.dense(Array(Double.unbox(s.get("location_lan")), Double.unbox(s.get("location_lon"))))).cache()

  println("--------------------------- KMeans start ---------------------")
  val clusters = KMeans.train(vectors, numClusters, numIterations)
  println("--------------------------- KMeans end  -----------------------")

  val vector = clusters.clusterCenters

  //var mongo = new Mongo("192.168.1.32")
  val mongo = new Mongo("localhost")
  val db = mongo.getDB("cardio")
  val coll = db.getCollection("pulse")

  vector.map(s => {
    val obj = new BasicDBObject()
    obj.put("date", new Date().getTime().toString)
    obj.put("lan", s.toArray.apply(0).toString)
    obj.put("lon", s.toArray.apply(1).toString)
    coll.insert(obj)
    coll.save(obj)
  })

  println("!!! Finish !!!")
}


