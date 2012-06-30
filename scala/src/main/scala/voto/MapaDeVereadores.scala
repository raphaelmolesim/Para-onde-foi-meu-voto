package voto

import scala.io.Source
import scala.collection.mutable.Map

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
      names.foreach(nome => map.put(nome, verId))
      verId += 1;
      map
    })

  def resolve(nome : String) : Int = vereadores.get(nome).getOrElse(throw new RuntimeException("Nome %s não está cadastrado".format(nome)))
}

object MapaDeVereadores {
  def apply() : MapaDeVereadores = {
    MapaDeVereadores(Source.fromInputStream(MapaDeVereadores.this.getClass.getResourceAsStream("/vereador-v2.txt")).getLines.mkString("\n"))
  }
}