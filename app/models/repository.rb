require 'singleton'

class Repository
  include Singleton
  
  def initialize
    @vereadores = JSON File.read("#{Rails.root}/db/vereadores.json")
    @projetos = JSON File.read("#{Rails.root}/db/projetos.json")
    @projetos = @projetos.map! { |p| Project.new(p) }
    @salarios = JSON File.read("#{Rails.root}/db/salarios.json")
    @gastos = JSON File.read("#{Rails.root}/db/gastos.json")
  end
  
  def vereadores
    @vereadores
  end
 
  def vereador_por_id vereador_id
    @vereadores.select{ |v| v['id'] == vereador_id }.first
  end

  def projetos_por_partido()
    data = @projetos.map{ |p| p.autores.map{ |autor| (vereador_por_id(autor) || {})['partido'] }.
      map{ |partido| { partido => p.categoria} } }.flatten
    data.inject({}) do |r, item| 
      k, v = item.first 
      if k
        r[k] ||= {} 
        r[k][v] ||= 0 
        r[k][v] += 1
      end
      r
    end
  end

  def projetos_por_vereador(vereador_id)
    @projetos.select { |p| p.autores.include? vereador_id }
  end
  
  def nome_vereadores
    @vereadores.collect { |v| v['nome'].split(" ").collect {|n| n.capitalize }.join(" ") }
  end
  
  def salarios_por_vereador(vereador_id)
    @salarios.select { |s| s['vereador_id'] == vereador_id }
  end
  
  def gastos_por_vereador vereador_id
    @gastos.select { |g| g['vereador_id'] == vereador_id }
  end
  
  def partidos
    @vereadores.collect { |v| v['partido'] }.uniq
  end
  
  def vereador_por_slug(slug)
    @vereadores.select{ |v| v['nome'].parameterize == slug }.first
  end
  
  class << self
    def vereadores
      @vereadores
    end
  
    def projetos_por_vereador(vereador_id)
      instance.projetos_por_vereador(vereador_id)
    end
    
    def nome_vereadores
      instance.nome_vereadores
    end
  
    def vereador_por_slug(slug)
      intance.vereador_por_slug(slug)
    end
  
    def salarios_por_vereador(vereador_id)
      instance.salarios_por_vereador(vereador_id)
    end

    def gastos_por_vereador(vereador_id)
      instance.gastos_por_vereador(vereador_id)
    end
    
    def partidos
      instance.partidos
    end
  end
end
