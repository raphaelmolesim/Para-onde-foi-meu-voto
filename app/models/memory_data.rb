require 'singleton'

class MemoryData
  include Singleton
  
  def initialize
    @vereadores = JSON File.read("#{Rails.root}/db/vereadores.json")
    @projetos = JSON File.read("#{Rails.root}/db/projetos.json")
  end
  
  def vereadores
    @vereadores
  end
  
  def nome_vereadores
    @vereadores.collect { |v| v['nome'].split(" ").collect {|n| n.capitalize }.join(" ") }
  end
  
  def vereador_por_slug(slug)
    @vereadores.collect { |v| v['nome'].parameterize }.select{ |v| v == slug }.first
  end
  
  def projetos_propostos_por(vereador)
    @projetos.select { |p| p['autores'].collect{ |a| a.downcase }.include?(vereador) }
  end
  
  def partidos
    @vereadores.collect { |v| v['partido'] }.uniq
  end
end
