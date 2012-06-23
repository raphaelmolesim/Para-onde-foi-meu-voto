classes = eval "{ #{File.read("classes_merge.txt")} }"
file = File.read "projects_database.txt"
file = file.force_encoding("ISO8859-1").gsub("\"", "\'").gsub(",", "")
lines = file.split("\r\n").select { |l| ['PL', 'PLO', 'PLD'].include? l.split('#').first }.
  select{ |l| l.split('#')[2][6,10].to_i > 2008 }

map = classes.inject({}) do |r,i| 
  classe, array_projetos = i
  array_projetos.each do |proj|
    r[proj] = classe
  end
  r
end

require "csv"

csv = lines.collect do |line|
  array = line.split("#")
  proj_id = array[0,3].join("#")
  titulo = array[4] #ementa
  desc = array[5] #palavra-chave
  classe = map[proj_id] || "TBD"
  #puts [proj_id, titulo, desc, classe].join(" | ")
  [proj_id, titulo, desc, classe]
end
CSV.open("final.csv", "w") { |c| csv.each { |a| c << a } }