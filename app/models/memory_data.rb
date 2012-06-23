require 'singleton'

class MemoryData
  include Singleton
  
  def initialize
    @vereadores = JSON File.read("#{Rails.root}/db/vereadores.json")
  end
  
  def vereadores
    @vereadores
  end
  
  def nome_vereadores
    @vereadores.collect { |v| v['nome'].split(" ").collect {|n| n.capitalize }.join(" ") }
  end
  
  def partidos
    @vereadores.collect { |v| v['partido'] }.uniq
  end
end
