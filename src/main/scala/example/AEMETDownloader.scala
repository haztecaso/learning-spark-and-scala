import io.circe
import io.circe.Decoder
import io.circe.generic.auto._
import sttp.client3._
import sttp.client3.circe._
import sttp.model.Uri

import java.io.File
import java.io.PrintWriter

case class AEMETResponse200(descripcion: String, estado: Int, datos: String, metadatos: String)

case class Estacion(indicativo: String, nombre: String, provincia: String, altitud: Int, latitud: String, longitud: String)

object AEMETDownloader {
  val apiKey = sys.env.getOrElse("AEMET_API_KEY", {
      throw new RuntimeException("AEMET_API_KEY env variable is not set.")
  }) 

  val baseUri = Uri.parse("https://opendata.aemet.es/opendata/api/").getOrElse(???)

  def mkApiRequest(p: String, ps: String*) = basicRequest
    .get(baseUri.addPath(p :: ps.toList))
    .header("accept", "application/json")
    .header("api_key", apiKey)
    .response(asJson[AEMETResponse200])

  def mkDataRequest[ResponseType: Decoder](uri: Uri) = basicRequest
    .get(uri)
    .response(asJson[ResponseType])

  def executeRequest[T](
    request: Request[Either[ResponseException[String, circe.Error], T], Any], 
    backend: SttpBackend[Identity, Any], description: String
  ): T = request
    .send(backend)
    .body
    .getOrElse { 
      throw new RuntimeException(s"Error en la petición ($description)") 
    }

  def getData[ResponseType: Decoder](backend:SttpBackend[Identity, Any], p:String, ps:String*): ResponseType = {
    val response = executeRequest(
      mkApiRequest(p,ps:_*),
      backend,
      "Solicitar datos de estaciones"
    )

    val datosUri = Uri
      .parse(response.datos)
      .getOrElse(throw new RuntimeException("URI inválida"))

    executeRequest(
      mkDataRequest[ResponseType](datosUri),
      backend,
      "Obtener y parsear datos de estaciones"
    )

  }

  def main(args: Array[String]): Unit = {
    val backend = HttpClientSyncBackend()
    val estaciones = getData[List[Estacion]](backend, "valores", "climatologicos", "inventarioestaciones", "todasestaciones")
    println(estaciones)
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
