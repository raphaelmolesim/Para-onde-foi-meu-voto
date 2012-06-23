class HomeController < ApplicationController
  def index
    @nomes = MemoryData.instance.nome_vereadores
    @partidos = MemoryData.instance.partidos
  end
  
  def vereador
    @vereador = MemoryData.instance.vereador_por_slug(params[:id])
    @projetos = MemoryData.instance.projetos_propostos_por(@vereador['nome'])
    render :resumo
  end
end
