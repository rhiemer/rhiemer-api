package br.com.rhiemer.api.jpa.dao.factory;

import javax.enterprise.context.Dependent;

import br.com.rhiemer.api.jpa.dao.DaoJPAImpl;
import br.com.rhiemer.api.util.annotations.BeanDiscovery;
import br.com.rhiemer.api.util.annotations.DaoAPIBean;





@DaoAPIBean
@BeanDiscovery
@Dependent
public class DaoAPI extends DaoJPAImpl  {



}
