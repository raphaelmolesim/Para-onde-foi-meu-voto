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
case class Vereador(nome : String, partido : String, ramais : List[String], gabinete : String, sala : String, apelidos : List[String])

object Vereadores {

  val v : List[List[String]] = Source.fromInputStream(this.getClass.getResourceAsStream("/vereador-v2.txt")).getLines.toList.
    map(line => {
      val sLine = line.split("#")
      if (sLine.size < 3)
        List()
      else
        (List(sLine(1)) ++ sLine(2).split("%") ++ sLine(3).split("%")).map(_.toLowerCase)
    })

  val vereadores : List[Vereador] = parse(XML.load(this.getClass.getResourceAsStream("/Lista_VEreadores.xml")) \\ "Row")

  def parse(nodes : NodeSeq) : List[Vereador] = {
    val buffer = ListBuffer[Vereador]()
    nodes.foreach(vereador => {
      val nome = (vereador \ "NOME_PARLAMENTAR").head.text.toLowerCase
      val partido = (vereador \ "PARTIDO").head.text
      val ramais = (vereador \ "RAMAL").head.text.split("-").toList.map(_.trim)
      val gabinete = (vereador \ "GV").head.text
      val sala = (vereador \ "SALA").head.text
      val apelidos = v.filter(_.contains(nome)).headOption.getOrElse(List())
      buffer += Vereador(nome, partido, ramais, gabinete, sala, apelidos)
    })
    buffer.toList
  }

  def main(args : Array[String]) {
    val writer = new PrintWriter(new File("vereadores.json"))

    writer.print(new String(serializer.out(vereadores)))
    writer.flush
    writer.close
  }

}