package com.edugobeti.gerenciamentodepessoas.enuns;

public enum TipoEndereco {

	PRINCIPAL(1, "Principal"),
	RECADO(2, "Recado"),
	TRABALHO(3, "Trabalho");
	
	Integer cod;
	String desc;
	
	private TipoEndereco(Integer cod, String desc){
		this.cod = cod;
		this.desc = desc;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
	
	public static TipoEndereco toEnum(Integer id) {
		if(id == null) {
			return null;
		}
		for (TipoEndereco x : TipoEndereco.values()) {
			if(id.equals(x.getCod())){
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + id);
	}
}
