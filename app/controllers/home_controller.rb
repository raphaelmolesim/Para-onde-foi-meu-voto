# -*- encoding : utf-8 -*-  
class HomeController < ApplicationController
  def index
    @vereadores = Repository.instance.vereadores
  end
  
  def vereador
    @vereador = Repository.instance.vereador_por_slug(params[:id])
    @projetos = JSON Repository.instance.projetos_por_vereador(@vereador['id']).to_json
    @salarios = Repository.instance.salarios_por_vereador(@vereador['id'])
    @gastos = Repository.instance.gastos_por_vereador(@vereador['id'])
    render :resumo
  end

  def votar
    @aval = Avaliacao.new(:fator => params[:fator], :vereador => params[:id])
    @aval.save!
    flash[:notice] = "Sua avaliação sobre este vereador foi armazenada com sucesso!"
    redirect_to :ranking
  end
  
  def share
  end
  
  def ranking
    @ranking = Avaliacao.all.inject({}) { |r, a| r[a.vereador] = r[a.vereador] ?
        r[a.vereador] + a.fator.to_i : a.fator.to_i ; r}.sort { |i1, i2| i2.last <=> i1.last }
  end

end
