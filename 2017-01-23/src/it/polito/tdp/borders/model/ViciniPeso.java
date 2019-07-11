package it.polito.tdp.borders.model;

public class ViciniPeso implements Comparable<ViciniPeso> {
	
	Country c;
	int peso;
	public ViciniPeso(Country c, int peso) {
		super();
		this.c = c;
		this.peso = peso;
	}
	public Country getC() {
		return c;
	}
	public void setC(Country c) {
		this.c = c;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(ViciniPeso altro) {
		
		return -(this.peso-altro.getPeso());
	}

}
