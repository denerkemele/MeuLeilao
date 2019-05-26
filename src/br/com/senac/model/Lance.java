package br.com.senac.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.senac.enumerador.TipoFormatoData;
import br.com.senac.enumerador.TipoMoeda;

// API: Comparable
public class Lance implements Comparable<Lance>{
	
	private String competidor;
	private double valor;
	//API: LocalDateTime
	private LocalDateTime data;
	DateTimeFormatter formatador = DateTimeFormatter.ofPattern(TipoFormatoData.DIA_MES_ANO_HORA24_MINUTO_SEGUNDO.val());
	
	public Lance() {
		super();
	}

	public Lance(String competidor, double valor) {
		super();
		this.competidor = competidor;
		this.valor = valor;
		this.data = LocalDateTime.now();
	}

	public String getCompetidor() {
		return competidor;
	}

	public void setCompetidor(String competidor) {
		this.competidor = competidor;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		//API: LocalDateTime
		return data.format(formatador) + " - Competidor: "+competidor+" / Lance de: "+ TipoMoeda.REAL.val() +" "+valor;
	}

	@Override
	public int compareTo(Lance outroLance) {
		return (this.getValor() < outroLance.getValor()? -1 :
			(this.getValor() > outroLance.getValor()? 1 : 
				outroLance.getData().compareTo(this.getData())));
	}

}
