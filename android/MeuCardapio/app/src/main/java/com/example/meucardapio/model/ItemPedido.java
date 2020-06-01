package com.example.meucardapio.model;

public class ItemPedido {


        private int idProduto;
        private String nomeProduto;
        private int quantidade;
        private Double preco;

        public ItemPedido() {
        }

        public int getIdProduto() {
            return idProduto;
        }

        public void setIdProduto(int idProduto) {
            this.idProduto = idProduto;
        }

        public String getNomeProduto() {
            return nomeProduto;
        }

        public void setNomeProduto(String nomeProduto) {
            this.nomeProduto = nomeProduto;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public Double getPreco() {
            return preco;
        }

        public void setPreco(Double preco) {
            this.preco = preco;
        }


    public boolean equals(ItemPedido itemPedido) {
        boolean isEqual=false;
        if(itemPedido!=null && itemPedido instanceof ItemPedido) {
            isEqual=(this.idProduto==itemPedido.idProduto);
        }
        return isEqual;
    }



}


