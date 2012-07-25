require 'factory_girl'

FactoryGirl.define do
  factory :project do
    initialize_with { new({ 'ano' => '2009', 'tipo' => 'PL', 'numero' => 1,
      'autores' => [1], 'categoria' => 'regulamentacao', 'data' => '03/02/2009',
      'ementa' => 'ementa1', 'palavras' => [ 'palavra1' ] }) }
  end
end