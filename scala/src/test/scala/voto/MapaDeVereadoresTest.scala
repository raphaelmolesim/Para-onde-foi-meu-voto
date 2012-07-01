package voto

import org.junit.Test
import org.junit.Assert

class MapaDeVereadoresTest {

  @Test
  def encontraUmVereador() {
    Assert.assertEquals(0, MapaDeVereadores("1#Nome").resolve("Nome"))
  }

  @Test
  def encontraUmVereadorComParenteses() {
    MapaDeVereadores.resolve("José Ferreira (Zelão)")
  }

  @Test
  def encontraMarcoAurelioCunha() {
    MapaDeVereadores.resolve("Marco Aurélio Cunha")
  }

  @Test
  def encontraMarcoAurelioCunhaBLaBLaBLa() {
    MapaDeVereadores.resolve("marco aurelio cunha")
  }

  @Test
  def encontraMarcoAurelioCunhaDeNovo() {
    MapaDeVereadores.resolve("MARCO AURELIO CUNHA")
  }

  @Test
  def encontraMarcoAurelioCunhaMaisUmaVez() {
    MapaDeVereadores.resolve("MARCO AURELIO CUNHA ")
  }

  @Test
  def encontraRussomano() {
    MapaDeVereadores.resolve("Russomano")
  }

  @Test
  def naoDeveExplodirSeNomeEApelidosForemIguais() {
    Assert.assertEquals(0, MapaDeVereadores("1#Nome#Nome").resolve("Nome"))
  }

  @Test
  def deveEncontrarNomesEmMaiusculas() {
    MapaDeVereadores("1#Abou Anni#Nome").resolve("ABOU ANNI")
  }

  @Test
  def naoDeveExplodirSeNomeEApelidosForemIguaisApesarDosAcentos() {
    Assert.assertEquals(0, MapaDeVereadores("1#Nóme#Nome").resolve("Nome"))
  }

  @Test(expected = classOf[Exception])
  def naoDeveEncontrarVereadorBlank() {
    MapaDeVereadores("1#Nome# #").resolve(" ")
  }

  @Test(expected = classOf[Exception])
  def naoDeveEncontrarVereadorVazio() {
    MapaDeVereadores("1#Nome##\n2#Nome2##").resolve("")
  }

  @Test(expected = classOf[RuntimeException])
  def naoDeveSobrescreverONomeDoVereador() {
    Assert.assertEquals(0, MapaDeVereadores("1#Nome\n2#Nome").resolve("Nome"))
  }

  @Test
  def deveRemoverAcentos() {
    Assert.assertEquals(0, MapaDeVereadores("1#Nóme").resolve("Nome"))
    Assert.assertEquals(0, MapaDeVereadores("1#Nome").resolve("Nóme"))
  }

  @Test
  def deveRemoverAcentosNoApelido() {
    val mapa = MapaDeVereadores("1#Nome#Apélido")
    Assert.assertEquals(0, mapa.resolve("Apelido"))
    Assert.assertEquals(0, mapa.resolve("Apélido"))
  }

  @Test
  def deveRemoverAcentosNoNomesMalucos() {
    val mapa = MapaDeVereadores("1#Nome#Apélido#Nóme maluco")
    Assert.assertEquals(0, mapa.resolve("Nome maluco"))
    Assert.assertEquals(0, mapa.resolve("Nóme maluco"))
  }

  @Test
  def encontraUmVereadorComNomeComEspacos() {
    Assert.assertEquals(0, MapaDeVereadores("1#Nome com espaco").resolve("Nome com espaco"))
  }

  @Test
  def encontraUmVereadorQueTenhaApelido() {
    Assert.assertEquals(0, MapaDeVereadores("1#Nome#Apelido#").resolve("Nome"))
    Assert.assertEquals(0, MapaDeVereadores("1#Nome#Apelido#").resolve("Apelido"))
  }

  @Test
  def encontraUmVereadorQueTenhaApelidos() {
    val ids = MapaDeVereadores("1#Nome#Apelido%Apelido2#")
    Assert.assertEquals(0, ids.resolve("Nome"))
    Assert.assertEquals(0, ids.resolve("Apelido"))
    Assert.assertEquals(0, ids.resolve("Apelido2"))
  }

  @Test
  def encontraUmVereadorQueTenhaApelidosComEspaco() {
    val ids = MapaDeVereadores("1#Nome#Apelido%Apelido 2#")
    Assert.assertEquals(0, ids.resolve("Nome"))
    Assert.assertEquals(0, ids.resolve("Apelido"))
    Assert.assertEquals(0, ids.resolve("Apelido 2"))
  }

  @Test
  def encontraUmVereadorQueTenhaApelidosComEspacoQuandoExisteMaisDeUmVereador() {
    val ids = MapaDeVereadores("1#Nome#Apelido%Apelido 2\n2#Nome2#Apelido maluco#")
    Assert.assertEquals(0, ids.resolve("Nome"))
    Assert.assertEquals(0, ids.resolve("Apelido"))
    Assert.assertEquals(0, ids.resolve("Apelido 2"))

    Assert.assertEquals(1, ids.resolve("Nome2"))
    Assert.assertEquals(1, ids.resolve("Apelido maluco"))
  }

  @Test
  def encontraUmVereadorQueTenhaApelidosComEspacoQuandoExisteMaisDeUmVereadorEOutrosNomes() {
    val ids = MapaDeVereadores("1#Nome#Apelido%Apelido 2#Mais Outro Nome\n2#Nome2#Apelido maluco#Outro Nome#")
    Assert.assertEquals(0, ids.resolve("Nome"))
    Assert.assertEquals(0, ids.resolve("Apelido"))
    Assert.assertEquals(0, ids.resolve("Apelido 2"))
    Assert.assertEquals(0, ids.resolve("Mais Outro Nome"))

    Assert.assertEquals(1, ids.resolve("Nome2"))
    Assert.assertEquals(1, ids.resolve("Apelido maluco"))
    Assert.assertEquals(1, ids.resolve("Outro Nome"))
  }

}