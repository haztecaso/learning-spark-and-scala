import sttp.client3.{HttpClientSyncBackend, basicRequest, RequestT}
import sttp.model.Uri
import java.io.{File, PrintWriter}

object AEMETDownloader {
  def main(args: Array[String]): Unit = {
    val url = "https://opendata.aemet.es/opendata/api"

    println(s"descargando datos de $url")

    val aemetApiKey = sys.env.getOrElse("AEMET_API_KEY", ???)

    val backend = HttpClientSyncBackend()

    val request = basicRequest.get(Uri.parse(url).getOrElse {
      throw new IllegalArgumentException(s"URL inválida: $url")
    })

    val response = request.send(backend)
    response.body match {
      case Right(data) =>
        println("Datos descargados con éxito.")
        saveToFile("data/aemet.json", data)
        println("Datos guardados en 'data.json'")
      case Left(error) =>
        println(s"Error al descargar los datos:\n$error")
    }
    backend.close()
  }

  def saveToFile(filename: String, data: String): Unit = {
    val file = new File(filename)
    val writer = new PrintWriter(file)
    try {
      writer.write(data)
    } finally {
      writer.close()
    }
  }
}
