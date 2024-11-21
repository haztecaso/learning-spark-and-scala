# Learning Spark and Scala at the same time

## Entrypoint

- I'm writing my Spark programs in
  [*SparkApp*](./src/main/scala/example/SparkApp.scala) main function.
- (WIP) I plan to develop a downloading program to fetch data from *AEMET*, in
  [*AEMETDownloader*](./src/main/scala/example/AEMETDownloader.scala).

## References

### Scala
- Scala package and environment managed using 
  [*sbt*](https://www.scala-sbt.org/) trough 
  [*nix flakes*](https://nixos.wiki/wiki/Flakes) and
  [*sbt-derivation*](https://github.com/zaninime/sbt-derivation/tree/master).

### Spark 

- I'm following this book: *Spark: The Definitive Guide* (code and data in
  github repo.
  [databricks/Spark-The-Definitive-Guide](https://github.com/databricks/Spark-The-Definitive-Guide/tree/master))
- I'm also consulting the [spark docs](https://spark.apache.org/docs/latest/) a
  lot.
