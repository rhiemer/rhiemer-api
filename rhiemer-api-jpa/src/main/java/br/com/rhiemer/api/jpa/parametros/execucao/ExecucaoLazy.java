package br.com.rhiemer.api.jpa.parametros.execucao;

import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;

public class ExecucaoLazy implements IExecucao {
	
	public static ExecucaoLazy  builder()
	{
		return new ExecucaoLazy();
	}

}
