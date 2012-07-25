package voto

import scala.reflect.BeanInfo
import scala.io.Source
import scala.collection.mutable.ListBuffer
import sjson.json.Serializer.{ SJSON => serializer }
import java.io.PrintWriter
import java.io.File
import scala.collection.mutable.Map

@BeanInfo
case class Projeto(categoria : String, descricaoCategoria : String, tipo : String, numero : Int, ano : String, data : String, ementa : String, palavras : List[String], autores : List[Int])

object Projeto {

  def apply(categoria : String, tipo : String, numero : Int, ano : String, data : String, ementa : String, palavras : List[String], autores : List[Int]) : Projeto = {
    Projeto(categoria, descricao(categoria), tipo, numero, ano, data, ementa, palavras, autores)
  }

  def descricao(categoria : String) = {
    categoria match {
      case "fiscalizacao" => "Fiscalização"
      case "dev" => "Desenvolvimento"
      case "transito" => "Trânsito"
      case "seguranca" => "Segurança"
      case "data" => "Datas Comemorativas"
      case "aumento" => "Aumentos de Salarial"
      case "ambiente" => "Meio Ambiente"
      case "saude" => "Saúde"
      case "direito" => "Garantia de Direitos"
      case "outros" => "Outros"
      case "muda_nome" => "Denomina Espaços Públicos"
      case "regulamentacao" => "Regulamentação"
      case "lixo" => "Reciclagem"
      case "cultura" => "Cultura"
      case "seguraca" => "Segurança"
      case "edu" => "Educação"
      case "habita" => "Habitação"
      case "espaco_pub" => "Espaço Público"
      case "TBD" => "A ser definido"
      case outra => throw new RuntimeException("Categoria %s não possui mapeamento".format(outra))
    }
  }
}

object Projetos {

  val classes : Map[String, String] = Source.fromInputStream(this.getClass.getResourceAsStream("/base_etiquetada-10-07-12-extrato.csv")).getLines.toList.
    foldLeft(Map[String, String]())((map, line) => {
      val s = line.split("\t")
      map += (s(1).trim -> s(0).trim)
      map
    })

  def main(args : Array[String]) {

    val w = new PrintWriter(new File("projetos_nao_categorizados.tsv"))
    val projetos : List[Projeto] = Source.fromInputStream(this.getClass.getResourceAsStream("/projects_database.txt")).getLines.
      toList.tail.filter(line => line.startsWith("PL") || line.startsWith("PDL") || line.startsWith("PLO")).filter(line => {
        line.split("#")(2).split("/")(2) > "2008"
      }).
      map(line => {
        val sLine : Array[String] = parse(line)
        val tipo = sLine(0)
        val numero = sLine(1).toInt
        val data = sLine(2)
        val temp = sLine(2).split("/")
        val ano = if (temp.size > 2) temp(2) else ""
        val palavras = if (sLine.size > 5) sLine(5).split("%").toList else List()
        val autores : List[Int] = sLine(3).split("%").toList.filter(nome => {
          !List("ADMINISTRACAO", "SAUDE", "CONSTITUICAO", "MESA", "EDUCACAO", "DEMOCRATAS", "Executivo", "PART.", "PARTIDO", "TRIBUNAL DE CONTAS DO MUNICIPIO", "POLITICA URBANA,METROPOLITANA,MEIO AMB.").foldLeft(false)((acc, blackList) => { nome.startsWith(blackList) || acc })
        }).map(MapaDeVereadores.resolve)
        val idProjeto = tipo + "#" + numero + "#" + data
        val categoria : String = classes.getOrElse(idProjeto, { w.println("TBD\t" + idProjeto + "\tTBD\t" + sLine(4) + " " + sLine(5)); "TBD" })
        Projeto(categoria, tipo, numero, ano, data, sLine(4), palavras, autores)
      })
    w.flush
    w.close
    val writer = new PrintWriter(new File("projetos.json"))
    println(projetos.length)

    writer.print(new String(serializer.out(projetos)))
    writer.flush
    writer.close

  }

  private def parse(line : String) : Array[java.lang.String] = {
    val buffer = ListBuffer[String]()
    var str = "";
    line.toCharArray.foreach(c => {
      c match {
        case '#' => { buffer += str; str = "" }
        case char => str += char
      }
    })
    buffer.toArray
  }

}

