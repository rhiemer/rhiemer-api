package br.com.rhiemer.api.jpa.parametros.execucao;

import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;

public class ExecucaoSemLazy implements IExecucao {
	
	public static ExecucaoSemLazy  builder()
	{
		return new ExecucaoSemLazy();
	}

}
