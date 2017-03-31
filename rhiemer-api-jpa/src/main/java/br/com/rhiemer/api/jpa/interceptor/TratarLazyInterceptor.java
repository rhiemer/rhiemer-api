package br.com.rhiemer.api.jpa.interceptor;

import static br.com.rhiemer.api.jpa.interceptor.SequencialInterceptorJPA.TRATAR_LAZY;

import java.util.Collection;
import java.util.List;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import br.com.rhiemer.api.jpa.annotations.SemTratarLazy;
import br.com.rhiemer.api.jpa.annotations.TratarLazy;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.helper.HelperLazy;
import br.com.rhiemer.api.jpa.parametros.execucao.ExecucaoFetchAll;
import br.com.rhiemer.api.jpa.parametros.execucao.ExecucaoLazy;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.helper.Helper;

@Interceptor
@TratarLazy
@Priority(TRATAR_LAZY)
public class TratarLazyInterceptor {

	@AroundInvoke
	public Object tratarLazyEntry(InvocationContext invocationContext) throws Exception {
		if (invocationContext.getMethod().getAnnotation(SemTratarLazy.class) != null
				|| invocationContext.getTarget().getClass().getAnnotation(SemTratarLazy.class) != null) {
			return invocationContext.proceed();
		}

		Object result = invocationContext.proceed();
		if (result == null) {
			return result;
		} else if (!Helper.isInstanceOrCollection(result, Entity.class)) {
			return result;
		}

		boolean fetchAll = false;

		if (invocationContext.getParameters() != null && invocationContext
				.getParameters()[invocationContext.getParameters().length - 1] instanceof IExecucao[]) {
			List<IExecucao> args = Helper.convertArgs(
					(IExecucao[]) invocationContext.getParameters()[invocationContext.getParameters().length - 1]);
			if (args.stream().filter(ExecucaoLazy.class::isInstance).findFirst().isPresent())
				return result;
			fetchAll = args.stream().filter(ExecucaoFetchAll.class::isInstance).findFirst().isPresent();
		}

		EntityManager em = (EntityManager) Helper.getValueMethodOrField(invocationContext.getTarget(), "entityManager");

		if (result instanceof Collection) {
			if (fetchAll) {
				return HelperLazy.copyCollectionFully((Collection) result);
			} else if (em != null) {
				HelperLazy.unproxyCollection((Collection) result, em);
				return result;
			}

		} else {
			if (fetchAll) {
				return HelperLazy.copyDTOFully(result);
			} else if (em != null) {
				return HelperLazy.unproxy(result);
			}
		}

		return result;
	}

}
