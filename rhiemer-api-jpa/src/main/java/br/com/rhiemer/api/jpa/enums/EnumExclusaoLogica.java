package br.com.rhiemer.api.jpa.enums;

import java.math.BigDecimal;

public enum EnumExclusaoLogica implements EnumeracaoComCodigoDescricao {

    ATIVO(0, "ATIVO"), //
    INATIVO(1, "INATIVO"); //

    

    private Integer codigo;
    private String descricao;

    private EnumExclusaoLogica(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Override
    public Integer getCodigo() {
        return codigo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getAsBigDecimal() {
        return new BigDecimal(codigo);
    }

    public static EnumExclusaoLogica getByCodigo(Integer codigo) {
        for (EnumExclusaoLogica exclusao : EnumExclusaoLogica.values()) {
            if (exclusao.getCodigo().equals(codigo)) {
                return exclusao;
            }
        }

        return null;
    }
}
