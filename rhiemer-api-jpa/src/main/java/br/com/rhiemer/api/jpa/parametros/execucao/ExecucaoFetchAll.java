package br.com.rhiemer.api.jpa.parametros.execucao;

import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;

public class ExecucaoFetchAll implements IExecucao {
	
	public static ExecucaoFetchAll  builder()
	{
		return new ExecucaoFetchAll();
	}

}
