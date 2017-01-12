package br.com.rhiemer.api.jpa.converter;

import javax.persistence.AttributeConverter;

import br.com.rhiemer.api.jpa.enums.EnumSimNao;

public class SimNaoConverter implements AttributeConverter<Boolean, Character> {

	@Override
	public Character convertToDatabaseColumn(Boolean attribute) {
		return EnumSimNao.getByValorBooleano(attribute).getAbreviacao().charAt(0);
	}

	@Override
	public Boolean convertToEntityAttribute(Character dbData) {
		return (dbData.equals(EnumSimNao.SIM.getAbreviacao().charAt(0)));
	}

}
