/**
 * 
 */
package br.com.rhiemer.api.util.dto;

import java.util.List;

import br.com.rhiemer.api.util.pojo.PojoAbstract;

/**
 * @author Ricardo M P SIlva / Raphael Jansen
 * :: RN INFO :: Queiroz Galvão ::
 * 
 * @version 1
 * @since 12.01.2010
 */
public class PagerList<Entity> extends PojoAbstract {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Pager pager;
	
	private List<Entity> entities;
	
	public PagerList(List<Entity> entities, Pager pager) {
		super();
		this.pager = pager;
		this.entities = entities;
	}


	public Pager getPager() {
		return pager;
	}

	public List<Entity> getEntities() {
		return entities;
	}

}
