class Project
  
  attr_accessor :id, :ano, :data, :autores, :categoria, :ementa, :numero, :palavras, :tipo, 
    :descricaoCategoria
  
  def initialize json
    json.each do |key, value|
      send("#{key}=".to_sym, json[key])
    end
    @id = "#{tipo.upcase}#{'%04d' % numero}-#{ano}" if tipo and numero and ano
  end
  
end
