package voto

import scala.reflect.BeanInfo
import scala.io.Source
import scala.collection.mutable.ListBuffer
import sjson.json.Serializer.{ SJSON => serializer }
import java.io.PrintWriter
import java.io.File
import scala.collection.mutable.Map

@BeanInfo
case class Salario(gabinete : String, vereador_id : Int, funcionario : String, cargo : String, valor : String, valorFormatado : String)

object Salario {

  def apply(gabinete : String, vereador : String, funcionario : String, cargo : String, valor : String) : Salario = {
    Salario(gabinete.trim, MapaDeVereadores.resolve(vereador.trim), funcionario.trim, cargo.trim, valor.trim, "R$ " + valor.trim)
  }
}

object Salarios {

  val salarios : List[Salario] = Source.fromInputStream(this.getClass.getResourceAsStream("/salarios.csv")).getLines.toList.
    map(line => {
      val s = line.split(";")
      Salario(s(0), s(1), s(2), s(3), s(4))
    })

  def main(args : Array[String]) {
    val writer = new PrintWriter(new File("salarios.json"))

    writer.print(new String(serializer.out(salarios)))
    writer.flush
    writer.close
  }

}