package br.com.rhiemer.api.jpa.enums;

import java.io.Serializable;

/**
 * Interface que deve ser implementada pelos enums que contém código e
 * descrição.
 */
public interface EnumeracaoComCodigoDescricao extends Serializable {

	/**
	 * Retorna o chave do ítem da enumeração.
	 * 
	 * @return chave chave do ítem da enumeração
	 */
	Integer getCodigo();

	/**
	 * Retorna a descrição do ítem da enumeração.
	 * 
	 * @return descricao descrição do ítem da enumeração
	 */
	String getDescricao();
}
