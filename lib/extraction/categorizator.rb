def proj_id (proj_name)
  temp, data = proj_name.split("--")
  if temp =~ /[A-z]{3}/i
    sigla = temp[0,3]
    num = temp[3,9]
  else
    sigla = temp[0,2]
    num = temp[2,9]
  end
  "#{sigla}##{num}##{data}"
end


classes = eval "{ #{File.read("classes.txt")} }"
file = File.read "projects_database.txt"
file = file.force_encoding("ISO8859-1")
lines = file.split("\r\n").select { |l| ['PL', 'PLO', 'PLD'].include? l.split('#').first }.
  select{ |l| l.split('#')[2][6,10].to_i > 2008 }

begin
  index = rand(lines.size)
  splitted = lines[index].split("#")
  part = splitted[0,3].join("#")
  array = classes.values.flatten.collect{ |i| proj_id(i) }
  next if array.include?(part)
  
  puts "==> #{splitted[4]}"
  puts ""
  puts "==> #{splitted[5]}"
  
  puts "==> #{ classes.keys.join ' | ' }"
  
  command = gets.chomp
  
  exit if command == "exit"
  classes[command] = classes[command] || []
  classes[command] << "#{splitted[0]}##{splitted[1]}##{splitted[2]}"
  File.open("classes.txt", "w") { |f| f.puts classes.collect{ |k,v| " \"#{k}\" => #{v}, " } } 
end while true




