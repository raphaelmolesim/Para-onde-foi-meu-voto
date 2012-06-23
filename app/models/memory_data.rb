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
    @vereadores.select{ |v| v['nome'].parameterize == slug }.first
  end
  
  def projetos_propostos_por(apelidos_do_vereador)
    @projetos.select do |p| 
      autores = p['autores'].collect{ |a| a.downcase }
      aux = (autores + apelidos_do_vereador)
      aux.size != aux.uniq.size
    end
  end
  
  def partidos
    @vereadores.collect { |v| v['partido'] }.uniq
  end
end
