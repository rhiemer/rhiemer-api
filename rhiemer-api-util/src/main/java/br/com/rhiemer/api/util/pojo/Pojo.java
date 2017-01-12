package br.com.rhiemer.api.util.pojo;

import java.io.Serializable;

public interface Pojo extends Serializable,Cloneable, Comparable<Pojo> {

	Object clone();

}
