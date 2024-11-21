package example
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col}

object SparkApp {
  def main(args: Array[String]): Unit = {
    println("Inicializando sesión de spark")
    val spark = SparkSession.builder
      .appName("Simple Application")
      .master("local[1]")
      .getOrCreate()

    val data = spark.read.textFile("readme.md")
    val numAs = data.filter(col("value").contains("a")).count()
    println(s"resultados:\n $numAs")
    spark.stop()
  }
}
