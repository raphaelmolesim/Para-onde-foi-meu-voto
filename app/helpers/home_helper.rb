module HomeHelper
  
  def format_data projetos
    map = projetos.inject({}) { |r, p| r[p['categoria']] = r[p['categoria']] ? 
      r[p['categoria']] + 1 : 1 ; r }
    { :children => map.collect do |categoria, total|
      {
        :children => [],
        :data => {
          :playcount => total,
          :"$area" => total
        },
        :id => categoria,
        :name => categoria
      }
    end }  
  end
  
end
