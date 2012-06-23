package voto

import scala.reflect.BeanInfo
import scala.io.Source
import scala.collection.mutable.ListBuffer
import sjson.json.Serializer.{ SJSON => serializer }
import java.io.PrintWriter
import java.io.File
import dispatch.json.JsValue
import scala.util.parsing.json.JSON

@BeanInfo
case class Gasto(vereador : String, descricao : String, valor : String, valorFormatado : String)

object Gasto {
  def apply(vereador : String, descricao : String, valor : String) : Gasto = {
    Gasto(vereador : String, descricao.trim, valor.trim, "R$ " + valor.trim)
  }

}

object Gastos {

  def main(args : Array[String]) {

    val value = List("Adilson_armando_carvalho_amadeu_512.json",
      "Adolfo_quintas_goncalves_neto_509.json",
      "Aguinaldo_timotheo_pereira_506.json",
      "Alfredo_alves_cavalcante_526.json",
      "Anibal_de_freitas_filho_680.json",
      "Antonio_carlos_rodrigues_266.json",
      "Antonio_de_paiva_monteiro_filho_248.json",
      "Antonio_donato_madormo_514.json",
      "Antonio_floriano_ferreira_pesaro_673.json",
      "Antonio_goulart_dos_reis_253.json",
      "Arselino_roque_tatto_242.json",
      "Atilio_francisco_da_silva_270.json",
      "Aurelio_fernandez_miguel_494.json",
      "Aurelio_nomura_249.json",
      "Carlos_alberto_eugenio_apolinario_275.json",
      "Carlos_alberto_pletz_neder_255.json",
      "Celso_do_carmo_jatene_273.json",
      "Claudio_do_prado_nogueira_513.json",
      "Claudio_gomes_fonseca_276.json",
      "Claudio_roberto_barbosa_de_souza_496.json",
      "Dalton_silvano_do_amaral_256.json",
      "David_bezerra_ribeiro_soares_682.json",
      "Domingos_odone_dissei_257.json",
      "Edir_sales_678.json",
      "Eliseu_gabriel_de_pieri_277.json",
      "Francisco_chagas_francilino_292.json",
      "Francisco_macena_da_silva_502.json",
      "Gilberto_tanos_natalini_278.json",
      "Gilson_almeida_barreto_246.json",
      "Italo_cardoso_de_araujo_483.json",
      "Jamil_murad_668.json",
      "Jose_americo_ascencio_dias_295.json",
      "Jose_de_paula_neto_667.json",
      "Jose_ferreira_dos_santos_298.json",
      "Jose_police_neto_501.json",
      "Jose_roberto_nazello_de_alvarenga_tripoli_243.json",
      "Jose_rolim_da_silva_527.json",
      "Jose_souza_dos_santos_674.json",
      "Juliana_cardoso_669.json",
      "Juscelino_jose_ataliba_antonio_gadelha_515.json",
      "Marco_aurelio_de_almeida_cunha_670.json",
      "Marcus_vinicius_de_almeida_ferreira_518.json",
      "Marta_maria_freire_da_costa_499.json",
      "Milton_ferreira_da_silva_675.json",
      "Milton_leite_da_silva_259.json",
      "Nelson_attila_russomanno_498.json",
      "Noemi_pereira_nonato_cavalcante_505.json",
      "Paulo_jesus_frange_260.json",
      "Paulo_sergio_abou_anni_497.json",
      "Ricardo_teixeira_525.json",
      "Sandra_regina_carbone_tadeu_mudalen_671.json",
      "Sebastiao_soares_de_farias_508.json",
      "Senival_pereira_de_moura_521.json",
      "Ushitaro_kamia_425.json",
      "Wadih_jorge_mutran_240.json").foldLeft(List[Gasto]())((acc, novo) => acc ++ parse(novo))

    val writer = new PrintWriter(new File("gastos.json"))

    writer.print(new String(serializer.out(value)))
    writer.flush
    writer.close
  }

  private def parse(file : String) : List[Gasto] = {

    val a = JSON.parseFull(Source.fromInputStream(
      this.getClass.getResourceAsStream("/dadosVereadores/" + file)).getLines.mkString("\n"))
    val nome = a.get match {
      case m : Map[String, Map[String, String]] => m("vereador")("nome")
    }
    val value = a.get match {
      case m : Map[String, List[Map[String, String]]] => m("despesas").map(map => Gasto(nome, map("descricao"), map("total")))
    }
    value.filterNot(_.descricao == "Total")
  }

}