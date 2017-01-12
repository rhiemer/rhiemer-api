package br.com.rhiemer.api.jpa.enums;


public enum EnumSimNao implements EnumeracaoComCodigoDescricao  {

    SIM(0,"S","Sim",true), //
    NAO(1,"N","Não",false); //    

    private Integer codigo;
    private String abreviacao;
    private String descricao;
    private Boolean valorBooleano;

    private EnumSimNao(Integer codigo,String abreviacao, String descricao,Boolean valorBooleano) {
        this.codigo = codigo;
        this.abreviacao = abreviacao;
        this.descricao = descricao;
        this.valorBooleano=valorBooleano;
    }
    
	public String getAbreviacao() {
		return abreviacao;
	}

	public Boolean getValorBooleano() {
		return valorBooleano;
	}

	@Override
	public Integer getCodigo() {
		// TODO Auto-generated method stub
		return this.codigo;
	}

	@Override
	public String getDescricao() {
		// TODO Auto-generated method stub
		return this.descricao;
	}

   

    public boolean isSim() {
        return EnumSimNao.values()[0].descricao.equalsIgnoreCase(this.descricao);
    }

    public boolean isNao() {
        return EnumSimNao.values()[1].descricao.equalsIgnoreCase(this.descricao);
    }

    public static EnumSimNao getByAbreviacao(String abreviacao) {
        if ("S".equalsIgnoreCase(abreviacao)) {
            return EnumSimNao.SIM;
        }

        return EnumSimNao.NAO;
    }
    
    public static EnumSimNao getByACodigo(Integer codigo) {
        if (new Integer(1).equals(codigo)) {
            return EnumSimNao.SIM;
        }

        return EnumSimNao.NAO;
    }
    
    public static EnumSimNao getByValorBooleano(Boolean valorBooleano) {
        if (Boolean.TRUE.equals(valorBooleano)) {
            return EnumSimNao.SIM;
        }

        return EnumSimNao.NAO;
    }





   

}