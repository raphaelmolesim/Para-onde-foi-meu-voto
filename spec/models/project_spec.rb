require 'spec_helper'

describe Project do
  
  it "should set all json values" do
    project = Project.new({ 'ano' => '2009', 'tipo' => 'PL', 'numero' => 1,
      'autores' => ['vereador1'], 'categoria' => 'regulamentacao', 'data' => '03/02/2009',
      'ementa' => 'ementa1', 'palavras' => [ 'palavra1' ] })
    project.ano.should == '2009'
    project.tipo.should == 'PL'
    project.numero.should == 1
    project.autores.should == ['vereador1']
    project.categoria.should == 'regulamentacao'
    project.data.should == '03/02/2009'
    project.ementa.should == 'ementa1'
    project.palavras.should == ['palavra1']
  end
  
  it "should have an id" do
    project = Project.new({ 'ano' => '2009', 'tipo' => 'PL', 'numero' => 1 })
    project.id.should == 'PL0001-2009'
  end
  
end
