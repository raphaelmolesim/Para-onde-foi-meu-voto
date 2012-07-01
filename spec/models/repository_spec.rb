require 'spec_helper'

describe Repository do
  
  before(:each) do
    @repo = Repository.instance
    @projetos = [ Factory.build(:project) ]
    @vereadores = [ { "apelidos" => [], "foto" => "", "gabinete" => "7", 
      "nome" => "Nome Vereador", "partido" => "PT",  "ramais" => ["4269"], 
      "sala" => "1006", "twitter" => "https:\/\/twitter.com\/abouannipv", "id" => 1 } ]
    @salarios = [ { "cargo" => "ASSISTENTE PARLAMENTAR", "funcionario" => "VIVIANE SOARES",
      "gabinete" => "1", "valor" => "1.645,71", "valorFormatado" => "R$ 1.645,71", 
      "vereador_id" => 1 } ]
    @gastos = [ { "descricao" => "A - COMBUSTIVEL", "valor" => "23.030,18",
      "valorFormatado" => "R$ 23.030,18", "vereador_id" => 1 } ]
    @repo.instance_variable_set(:"@projetos", @projetos)
    @repo.instance_variable_set(:"@vereadores", @vereadores)
    @repo.instance_variable_set(:"@salarios", @salarios)
    @repo.instance_variable_set(:"@gastos", @gastos)
  end
  
  it "should find projetos por vereador id" do
    projetos = Repository.projetos_por_vereador(1)
    projetos.first.should == @projetos.first
  end
  
  it "should get a list with nomes dos vereadores" do
    nomes = Repository.nome_vereadores
    nomes.first.should == "Nome Vereador"
  end
  
  it "should find salarios por vereador" do
    salarios = Repository.salarios_por_vereador(1)
    salarios.first.should == @salarios.first
  end
  
  it "should find gastos por gastos" do
    gastos = Repository.gastos_por_vereador(1)
    gastos.first.should == @gastos.first
  end
  
end