package voto

import scala.io.Source

import scala.collection.mutable.Map
import java.io.PrintWriter
import java.io.File
import sjson.json.Serializer.{ SJSON => serializer }

case class MapaDeVereadores(content : String) {
  private var verId = 0;

  val vereadores : Map[String, Int] = content.split("\n").toList.
    foldLeft(Map[String, Int]())((map, line) => {
      val splittedLine = line.split("#")
      val names = splittedLine.size match {
        case 2 => List(splittedLine(1))
        case 3 => (List(splittedLine(1)) ++ splittedLine(2).split("%"))
        case _ => (List(splittedLine(1)) ++ splittedLine(2).split("%") ++ splittedLine(3).split("%"))
      }
      names.map(nome => Chars.limpa(nome.trim)).filter(_.length != 0).toSet[String].toList.foreach(nome =>
        if (map.contains(nome))
          throw new RuntimeException("dados quebrado para nome [%s]".format(nome))
        else
          map.put(nome, verId))
      verId += 1;
      map
    })

  def resolve(nome : String) : Int = vereadores.get(Chars.limpa(nome)).getOrElse(throw new Exception("Nome %s não está cadastrado".format(nome)))
}

object MapaDeVereadores {
  def apply() : MapaDeVereadores = {
    MapaDeVereadores(Source.fromInputStream(MapaDeVereadores.this.getClass.getResourceAsStream("/vereador-v2.txt")).getLines.mkString("\n"))
  }

  def main(args : Array[String]) {
    val writer = new PrintWriter(new File("mapa_de_vereadores.json"))
    writer.print(new String(serializer.out(MapaDeVereadores().vereadores)))
    writer.flush
    writer.close

  }
}