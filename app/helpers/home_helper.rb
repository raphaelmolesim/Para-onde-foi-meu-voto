# -*- encoding : utf-8 -*-
module HomeHelper
  
  def display_categoria categoria, total, sum
    map = { 
    "fiscalização" => "Fiscalização",
    "dev" => "Desenvolvimento", 
    "transito" => "Trânsito",
    "segurança" => "Segurança",
    "data" => "Datas Comemorativas",
    "aumento" => "Aumentos de Salarial",
    "ambiente" => "Meio Ambiente",
    "seguranca" => "Segurança",
    "saude" => "Saúde",
    "direito" => "Garantia de Direitos",
    "outros" => "Outros",
    "muda_nome" => "Denomina Espaços Públicos",
    "regulamentação" => "Regulamentação",
    "lixo" => "Reciclagem",
    "cultura" => "Cultura",
    "seguraca" => "Segurança",
    "edu" => "Educação",
    "habita" => "Habitação",
    "espaco_pub" => "Espaço Público",
    "TBD" => "A ser definido" }
    "#{map[categoria]} (#{((total/sum.to_f) * 100).to_i}%)"
  end
  
  def format_data projetos
    map = projetos.inject({}) { |r, p| r[p['categoria']] = r[p['categoria']] ? 
      r[p['categoria']] + 1 : 1 ; r }
    sum = map.values.inject { |r, i| r + i }
    { :children => map.collect do |categoria, total|
      {
        :children => [],
        :data => {
          :playcount => total,
          :"$area" => total
        },
        :id => display_categoria(categoria, total, sum),
        :name => display_categoria(categoria, total, sum)
      }
    end }  
  end
  
end
