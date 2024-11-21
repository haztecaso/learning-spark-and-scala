import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col}

object SparkApp {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Simple Application")
      .master("local[1]")
      .getOrCreate()
    
    spark.conf.set("spark.sql.shuffle.partitions", "2")

    val data = spark
      .read
      .option("inferSchema", "true")
      .option("header", "true")
      .csv("data/bike-data/201508_trip_data.csv")
      .repartition(2)

    println(s"Columnas del dataframe: ${data.columns.mkString(", ")}")

    println(s"número de entradas: ${data.count()}")

    data
      .groupBy(col("Start Date"))
      .count()
      .show()

    spark.stop()
  }
}
