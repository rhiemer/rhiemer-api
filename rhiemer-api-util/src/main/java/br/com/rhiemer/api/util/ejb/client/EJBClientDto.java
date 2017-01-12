package br.com.rhiemer.api.util.ejb.client;

import br.com.rhiemer.api.util.enums.EnumTypeEJBClient;

public class EJBClientDto {
	
	private  EnumTypeEJBClient tipo=EnumTypeEJBClient.STATELESS;
	private  Class<?> classe;
	private  String app;
	private  String modulo;
	private  String beanName;
	private  String distinctName;	
	
	public Class<?> getClasse() {
		return classe;
	}
	public void setClasse(Class<?> classe) {
		this.classe = classe;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getDistinctName() {
		return distinctName;
	}
	public void setDistinctName(String distinctName) {
		this.distinctName = distinctName;
	}
	public EnumTypeEJBClient getTipo() {
		return tipo;
	}
	public void setTipo(EnumTypeEJBClient tipo) {
		this.tipo = tipo;
	}
	
	public static BuilderEJBClientDto createBuilder()
	{
		return new BuilderEJBClientDto();
	}

	
	public static class BuilderEJBClientDto{
		
		private  EnumTypeEJBClient tipo;
		private  Class<?> classe;
		private  String app;
		private  String modulo;
		private  String beanName;
		private  String distinctName;
		
		public BuilderEJBClientDto setTipo(EnumTypeEJBClient tipo) {
			this.tipo = tipo;
			return this;
		}
		public BuilderEJBClientDto setClasse(Class<?> classe) {
			this.classe = classe;
			return this;
		}
		public BuilderEJBClientDto setApp(String app) {
			this.app = app;
			return this;
		}
		public BuilderEJBClientDto setModulo(String modulo) {
			this.modulo = modulo;
			return this;
		}
		public BuilderEJBClientDto setBeanName(String beanName) {
			this.beanName = beanName;
			return this;
		}
		public BuilderEJBClientDto setDistinctName(String distinctName) {
			this.distinctName = distinctName;
			return this;
		}
		public EJBClientDto  builder() {
			EJBClientDto ejbDto = new EJBClientDto();
			ejbDto.setTipo(this.tipo);
			ejbDto.setClasse(this.classe);
			ejbDto.setApp(this.app);
			ejbDto.setModulo(this.modulo);
			ejbDto.setBeanName(this.beanName);
			ejbDto.setDistinctName(this.distinctName);
			return ejbDto;
		}
		
	}

}
