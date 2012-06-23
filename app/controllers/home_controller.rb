class HomeController < ApplicationController
  def index
    @nomes = MemoryData.instance.nome_vereadores
    @partidos = MemoryData.instance.partidos
  end
  
  def vereador
    @vereador = MemoryData.instance.vereador_por_slug(params[:id])
    @projetos = MemoryData.instance.projetos_propostos_por(@vereador['apelidos'])
    @salarios = MemoryData.instance.salarios_por(@vereador['apelidos'])
    @gastos = MemoryData.instance.gastos_por(@vereador['apelidos'])
    render :resumo
  end

  def votar
    @aval = Avaliacao.new(:fator => params[:fator], :vereador => params[:id])
    @aval.save!
    redirect_to :share
  end
  
  def share
  end
  
  def ranking
    @vereadores = Avaliacao.all.inject({}) { |r, a| r[a.vereador] = r[a.vereador] ?
        r[a.vereador] + a.fator.to_i : a.fator.to_i ; r}.sort { |i1, i2| i2.last <=> i1.last }
  end

end
