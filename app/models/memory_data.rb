require 'singleton'

class MemoryData
  include Singleton
  
  def initialize
    @vereadores = JSON File.read("#{Rails.root}/db/vereadores.json")
    @projetos = JSON File.read("#{Rails.root}/db/projetos.json")
    @salarios = JSON File.read("#{Rails.root}/db/salarios.json")
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
  
  def salarios_por(apelidos_do_vereador)
    @salarios.select do |salario| 
      vereador = salario['vereador'].downcase
      apelidos_do_vereador.include?(vereador)
    end
  end
  
  def partidos
    @vereadores.collect { |v| v['partido'] }.uniq
  end
end
