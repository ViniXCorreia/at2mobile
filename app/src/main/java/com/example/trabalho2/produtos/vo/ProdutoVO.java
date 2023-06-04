package com.example.trabalho2.produtos.vo;

public class ProdutoVO {
    private int id;
    private String descricao;
    private String imagem;
    private double preco;
    private String titulo;
    private int calorias;
    private int gluten;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    public int getGluten() {
        return gluten;
    }

    public void setGluten(int gluten) {
        this.gluten = gluten;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
