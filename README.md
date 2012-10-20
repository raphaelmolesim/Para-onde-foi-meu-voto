Para Onde Foi Meu Voto?
=======================


Instruções
----------

##Aplicativo Web

#####Pré-requisitos:
	
* Ruby Virtual Machine (RVM)
	Instalar o Ruby na sua máquina, por favor utilize o manual: 
	[Tutorial de Instalação do RVM e do Ruby](https://github.com/danielvlopes/ruby-unix/tree/master/pt "Tutorial de Instalação do RVM e do Ruby")

##### Instalação

	rvm install 1.9.2-p290
	git clone git@github.com:raphaelmolesim/Para-onde-foi-meu-voto
	cd Para-onde-foi-meu-voto
	bundle install
	rake db:migrate
	rails server

Pronto é só abrir o seu navegador em http://localhost:3000/