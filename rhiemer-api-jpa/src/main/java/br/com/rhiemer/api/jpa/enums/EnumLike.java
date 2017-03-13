package br.com.rhiemer.api.jpa.enums;

public enum EnumLike {
	
	
	
	INICIO("%%s"),INICO_FIM("%%s%"),FIM("%s%");
	
	private String format;
	
	private EnumLike(String format)
	{
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	public String format(String value)
	{
		return String.format(this.format,value);
	}

}
