package voto

import scala.reflect.BeanInfo
import scala.io.Source
import scala.collection.mutable.ListBuffer
import sjson.json.Serializer.{ SJSON => serializer }
import java.io.PrintWriter
import java.io.File
import scala.collection.mutable.Map
import scala.xml.XML
import java.util.regex.Pattern

@BeanInfo
case class Votacao(tipoVoto : String, vereador : String, tipoProj : String, numero : String, ano : String)

object Votacoes {

  val pattern = Pattern.compile("""(PL|PDL|PLO)\s*(\d+)[^\d]+(\d+)""")

  val votacoes : List[Votacao] = List("Votacoes_2011.xml", "Votacoes_2012.xml").foldLeft(List[Votacao]())((acc, value) => {
    val buffer = ListBuffer[Votacao]()
    (XML.load(this.getClass.getResourceAsStream("/" + value)) \\ "Votacao").
      filter(materia => pattern.matcher(materia.attribute("Materia").head.text).find).
      foreach(votacao => {
        val matcher = pattern.matcher(votacao.attribute("Materia").head.text)
        matcher.find
        val tipoProj = matcher.group(1)
        val numero = matcher.group(2)
        val ano = matcher.group(3)
        (votacao \ "Vereador").foreach(vereador => {
          val nome = vereador.attribute("NomeParlamentar").head.text
          val tipoVoto = vereador.attribute("Voto").head.text
          buffer += Votacao(tipoVoto, nome, tipoProj, numero, ano)
        })

      })
    acc ++ buffer
  })

  def main(args : Array[String]) {
    val writer = new PrintWriter(new File("votacoes.json"))

    writer.print(new String(serializer.out(votacoes)))
    writer.flush
    writer.close
  }

}