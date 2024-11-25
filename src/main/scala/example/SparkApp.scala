import org.apache.spark.sql.functions.{col, to_date}
import org.apache.spark.sql.SparkSession

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
      // .repartition(2)

    println(s"Columnas del dataframe: ${data.columns.mkString(", ")}")

    println(s"número de entradas: ${data.count()}")

    val datetimeFormat = "M/d/yyyy H:mm"

    val data_by_date = data
      .withColumn("start_date", to_date(col("Start Date"), datetimeFormat))
      .groupBy(col("start_date"))
      .sum()

    // data_by_date.explain()

    data_by_date.show()

    spark.stop()
  }
}
