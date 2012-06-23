class CreateAvaliacaos < ActiveRecord::Migration
  def change
    create_table :avaliacaos do |t|
      t.string :vereador
      t.text :fator

      t.timestamps
    end
  end
end
