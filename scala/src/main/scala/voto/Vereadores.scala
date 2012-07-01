package voto

import scala.reflect.BeanInfo
import scala.xml.XML
import scala.xml.NodeSeq
import scala.collection.mutable.ListBuffer
import scala.io.Source
import java.io.PrintWriter
import java.io.File
import sjson.json.Serializer.{ SJSON => serializer }

@BeanInfo
case class Vereador(id : Int, nome : String, partido : String, ramais : List[String], gabinete : String, sala : String, foto : String, twitter : String)

object Vereadores {

  val fotos : List[(Int, String)] = Source.fromInputStream(this.getClass.getResourceAsStream("/vereadores_extra")).getLines.toList.map(line => {
    val s = line.split("\\|")
    (MapaDeVereadores.resolve(s(0)), s(2).trim)
  })

  val twitter : List[(Int, String)] = Source.fromInputStream(this.getClass.getResourceAsStream("/vereadores_extra")).getLines.toList.map(line => {
    val s = line.split("\\|")
    (MapaDeVereadores.resolve(s(0)), s(1).trim)
  })

  val vereadores : List[Vereador] = parse(XML.load(this.getClass.getResourceAsStream("/Lista_VEreadores.xml")) \\ "Row")

  def parse(nodes : NodeSeq) : List[Vereador] = {
    val buffer = ListBuffer[Vereador]()
    nodes.foreach(vereador => {
      val nome = (vereador \ "NOME_PARLAMENTAR").head.text.toLowerCase
      val id = MapaDeVereadores.resolve(nome)
      val partido = (vereador \ "PARTIDO").head.text
      val ramais = (vereador \ "RAMAL").head.text.split("-").toList.map(_.trim)
      val gabinete = (vereador \ "GV").head.text
      val sala = (vereador \ "SALA").head.text
      val foto = fotoPara(id)
      val twitter = twitterPara(id)
      buffer += Vereador(id, nome, partido, ramais, gabinete, sala, foto, twitter)
    })
    buffer.toList
  }

  def fotoPara(id : Int) : String = {
    fotos.filter(_._1 == id).headOption.getOrElse(("", ""))._2
  }

  def twitterPara(id : Int) : String = {
    twitter.filter(_._1 == id).headOption.getOrElse(("", ""))._2
  }

  def main(args : Array[String]) {
    val writer = new PrintWriter(new File("vereadores.json"))

    writer.print(new String(serializer.out(vereadores)))
    writer.flush
    writer.close
  }

}