package br.com.senac.model;

public class Item {

	private String descricao;
	private double valorMinimo;
	
	public Item(String item, double valorMinimo) {
		super();
		this.descricao = item;
		this.valorMinimo = valorMinimo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String item) {
		this.descricao = item;
	}

	public double getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	
}
