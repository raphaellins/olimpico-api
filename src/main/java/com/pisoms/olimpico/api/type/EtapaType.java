package com.pisoms.olimpico.api.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EtapaType {
	
	ELIMINATORIAS, OITAVAS, QUARTAS, SEMIFINAL, FINAL;
	
	@JsonValue
	public String toValue() {
		return name();
	}

}
