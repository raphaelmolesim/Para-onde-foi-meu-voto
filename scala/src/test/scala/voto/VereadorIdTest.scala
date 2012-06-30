package voto

import org.junit.Test
import org.junit.Assert

class VereadorIdTest {

  @Test
  def encontraUmVereador() {
    Assert.assertEquals(0, MapaDeVereadores("1#Nome").resolve("Nome"))
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