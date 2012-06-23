def transform (classes, lines)
  ret = ""
  classes.each do |categoria, list|
    ret += list.collect do |project| 
      puts "Converting #{project}"
      temp, data = project.split("--")
      if temp =~ /[A-z]{3}/i
        sigla = temp[0,3]
        num = temp[3,9]
      else
        sigla = temp[0,2]
        num = temp[2,9]
      end
      puts "Looking for #{sigla}##{num}##{data}"
      line = lines.select{ |l| l.include? "#{sigla}##{num}##{data}" }.first.split("#")
      titulo, desc = line[4], line[5]
      [categoria, titulo, desc].join("#")
    end.join("\r\n")
  end
  ret
end
