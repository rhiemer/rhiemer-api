package br.com.rhiemer.api.util.helper;


import java.util.Map;

import br.com.rhiemer.api.util.exception.APPSystemException;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public class HelperPojo {

	public static <T extends PojoKeyAbstract> T newInstancePrimaryKey(Class<T> classe, Object... params) {
		T result = Helper.newInstance(classe, params);
		if (result != null)
			return result;

		try {
			result = classe.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			throw new APPSystemException(e1);
		}
		
		Map<String, Class<?>> _primaryKeys = Helper.getPrimaryKeyList(classe);
		Object[] _chaves = null;
		if (_primaryKeys.size() <= 1)
			_chaves = Helper.convertArgsArray(Object.class, params);
		else
			_chaves = params;
			
		result.setPrimaryKeyArrayAtributo(_chaves);
		return result;

	}

}
