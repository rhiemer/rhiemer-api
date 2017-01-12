package br.com.rhiemer.api.jpa.listener;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class ListenerEntity {
	
	
	@PrePersist
	public void onPrePersist(Object data)
	{
		ListenerEntityFire listenerEntityFire = CDI.current().select(ListenerEntityFire.class).get();
		listenerEntityFire.fire(ListenerEnum.PrePersist,data);
	}
	
	
	@PreUpdate
	public void onPreUpdate(Object data)
	{
		ListenerEntityFire listenerEntityFire = CDI.current().select(ListenerEntityFire.class).get();
		listenerEntityFire.fire(ListenerEnum.PreUpdate,data);
	}
	
	@PreRemove
	public void onPreRemove(Object data)
	{
		ListenerEntityFire listenerEntityFire = CDI.current().select(ListenerEntityFire.class).get();
		listenerEntityFire.fire(ListenerEnum.PreRemove,data);
	}
	
	@PostPersist
	public void onPostPersist(Object data)
	{
		ListenerEntityFire listenerEntityFire = CDI.current().select(ListenerEntityFire.class).get();
		listenerEntityFire.fire(ListenerEnum.PostPersist,data);
	}
	
	@PostUpdate
	public void onPostUpdate(Object data)
	{
		ListenerEntityFire listenerEntityFire = CDI.current().select(ListenerEntityFire.class).get();
		listenerEntityFire.fire(ListenerEnum.PostUpdate,data);
	}
	
	@PostRemove
	public void onPostRemove(Object data)
	{
		ListenerEntityFire listenerEntityFire = CDI.current().select(ListenerEntityFire.class).get();
		listenerEntityFire.fire(ListenerEnum.PostRemove,data);
	}
	
	@PostLoad
	public void onPostLoad(Object data)
	{
		ListenerEntityFire listenerEntityFire = CDI.current().select(ListenerEntityFire.class).get();
		listenerEntityFire.fire(ListenerEnum.PostLoad,data);
	}




}
