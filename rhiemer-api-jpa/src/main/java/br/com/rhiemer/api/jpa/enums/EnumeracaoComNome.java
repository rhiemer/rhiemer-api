package br.com.rhiemer.api.jpa.enums;

import java.io.Serializable;

/**
 * Interface que deve ser implementada pelos enums que contém código e
 * descrição.
 */
public interface EnumeracaoComNome extends Serializable {

	String getNome();
}
