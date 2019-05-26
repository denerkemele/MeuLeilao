package br.com.senac.enumerador;

public enum TipoFormatoData {
	DIA_MES_ANO_HORA24_MINUTO_SEGUNDO("dd/MM/yyyy kk:mm:ss");
	
	private String formato;
	
	TipoFormatoData(String formato){
		this.formato = formato;
	}
	
	public String val(){
		return this.formato;
	}
}
