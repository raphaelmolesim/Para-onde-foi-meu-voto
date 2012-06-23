class HomeController < ApplicationController
  def index
    @nomes = MemoryData.instance.nome_vereadores
    @partidos = MemoryData.instance.partidos
  end
end
