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
classes_jonas = eval "{ #{File.read("classes_jonas.txt")} }"

classes_jonas.each do |k, v|
  classes[k] ||= []
  classes[k] = (classes[k] + v).collect { |i| i.include?("--") ? proj_id(i) : i }.uniq
end

File.open("classes_merge.txt", "w") { |f| f.puts classes.collect{ |k,v| " \"#{k}\" => #{v}, " } } 

