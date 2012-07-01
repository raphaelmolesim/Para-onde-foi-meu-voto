class HomeController < ApplicationController
  def index
    @nomes = Repository.instance.nome_vereadores
    @partidos = Repository.instance.partidos
  end
  
  def vereador
    @vereador = Repository.instance.vereador_por_slug(params[:id])
    @projetos = Repository.instance.projetos_por_vereador(@vereador['apelidos'])
    @salarios = Repository.instance.salarios_por_vereador(@vereador['apelidos'])
    @gastos = Repository.instance.gastos_por_vereador(@vereador['apelidos'])
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
