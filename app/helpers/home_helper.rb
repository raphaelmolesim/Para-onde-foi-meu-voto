# -*- encoding : utf-8 -*-
module HomeHelper
  
  def display_categoria categoria, total, sum
    c = categoria.strip
    map = { 
    "fiscalizacao" => "Fiscalização",
    "dev" => "Desenvolvimento", 
    "transito" => "Trânsito",
    "seguranca" => "Segurança",
    "data" => "Datas Comemorativas",
    "aumento" => "Aumentos de Salarial",
    "ambiente" => "Meio Ambiente",
    "saude" => "Saúde",
    "direito" => "Garantia de Direitos",
    "outros" => "Outros",
    "muda_nome" => "Denomina Espaços Públicos",
    "regulamentacao" => "Regulamentação",
    "lixo" => "Reciclagem",
    "cultura" => "Cultura",
    "seguraca" => "Segurança",
    "edu" => "Educação",
    "habita" => "Habitação",
    "espaco_pub" => "Espaço Público",
    "TBD" => "A ser definido" }
    "#{map[c]} (#{((total/sum.to_f) * 100).to_i}%)"
  end
  
  def format_data projetos
    map = projetos.inject({}) { |r, p| cat = p['categoria'].strip ; 
      r[cat] = r[cat] ? r[cat] + 1 : 1 ; r }
    sum = map.values.inject { |r, i| r + i }
    { :children => map.collect do |categoria, total|
      {
        :children => [],
        :data => {
          :playcount => total,
          :"$area" => total
        },
        :id => categoria,
        :name => display_categoria(categoria, total, sum)
      }
    end }  
  end
  
  def display_name name
    name.split(' ').map { |s| s.capitalize }.join(' ')
  end
end
