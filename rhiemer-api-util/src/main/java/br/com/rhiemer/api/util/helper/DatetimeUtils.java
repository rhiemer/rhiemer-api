package br.com.rhiemer.api.util.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Reúne funções utilitárias relacionadas a data e hora
 * 
 * @author rodrigo.hiemer
 * 
 */
public final class DateTimeUtils {

	public static class OrdinalDiaSemana {
		public static int PRIMEIRO = 1;
		public static int SEGUNDO = 2;
		public static int TERCEIRO = 3;
		public static int QUARTO = 4;
		public static int ULTIMO = 5;
	}

	public static final String REST_DATE_FORMAT = "dd-MM-yyyy";
	public static final String REST_DATE_TIME_FORMAT = "dd-MM-yyyyHH:mm:ss";	
	
	public static final String MACHINE_DATE_FORMAT = "yyyy-MM-dd";
	public static final String MACHINE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * Formato de data em que são exibidas mensagens para os usuários (leitura
	 * humana)
	 */
	public static final String HUMAN_DATE_FORMAT = "dd/MM/yyyy";
	/**
	 * Formato de data/hora em que são exibidas mensagens para os usuários
	 * (leitura humana)
	 */
	public static final String HUMAN_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
	public static final String HUMAN_DATE_TIME_FORMAT_MLS = "dd/MM/yyyy HH:mm:ss.SSS";
	public static final String HUMAN_TIME_FORMAT_MLS = "dd/MM/yyyy HH:mm:ss.SSS";

	public static final String TIME_ZONE_BRASIL = "America/Sao_Paulo";

	private DateTimeUtils() {
	}

	/**
	 * Recebe um Calendar, remove seus campos referentes a horas (hora, minuto,
	 * segundo, milisegundos)
	 * 
	 * @param cal
	 *            Instância a ser limpa
	 */
	public static void limparHoras(Calendar cal) {
		if (cal != null) {
			cal.set(Calendar.HOUR, cal.getActualMinimum(Calendar.HOUR));
			cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
			cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
			cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
		}
	}

	public static Date diffDates(Date dateFim, Date dateIni) {
		Instant instantFim = Instant.ofEpochMilli(dateFim.getTime());
		Instant instantIni = Instant.ofEpochMilli(dateIni.getTime());
		return new Date(ChronoUnit.MILLIS.between(instantIni, instantFim));
	}

	public static String diffDatesFormat(Date dateFim, Date dateIni) {
		Instant instantFim = Instant.ofEpochMilli(dateFim.getTime());
		Instant instantIni = Instant.ofEpochMilli(dateIni.getTime());
		String timeString = String.format("%02d:%02d:%02d,%03d", ChronoUnit.HOURS.between(instantIni, instantFim),
				ChronoUnit.MINUTES.between(instantIni, instantFim), ChronoUnit.SECONDS.between(instantIni, instantFim),
				ChronoUnit.MILLIS.between(instantIni, instantFim));
		return timeString;
	}

	public static Date diffDatesStr(String dateFim, String dateIni, String format) {
		if (!StringUtils.isBlank(dateFim) && !StringUtils.isBlank(dateIni)) {
			try {
				return diffDates(new SimpleDateFormat(format).parse(dateFim),
						new SimpleDateFormat(format).parse(dateIni));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		} else
			return null;
	}

	public static String diffDatesStrFormat(String dateFim, String dateIni, String format) {
		try {
			return diffDatesFormat(new SimpleDateFormat(format).parse(dateFim),
					new SimpleDateFormat(format).parse(dateIni));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Recebe um Date, remove seus campos referentes a horas (hora, minuto,
	 * segundo, milisegundos) e retorna uma instância somente os campos de Data
	 * (dia, mês, ano)
	 * 
	 * @param dt
	 *            Data a ser limpa
	 * 
	 * @return Instância sem dados de hora
	 */
	public static Date limparHoras(Date dt) {
		if (dt == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			limparHoras(cal);
			return cal.getTime();
		}
	}

	/**
	 * Retorna um Calendar com a data de início da última semana cinematográfica
	 * que teve início na sexta-feira. A partir desta data as semanas
	 * cinematográficas passaram a compreender a quinta-feira de uma semana até
	 * a quarta feira da outra. Excepcionalmente em virtude disto, a semana
	 * cinematográfica que teve início do dia 07/03/2014 teve apenas 6 dias,
	 * compreendendo do dia 07/03/2014 (sexta-feira) até o dia 12/03/2014
	 * (quarta-feira)
	 * 
	 * @return Instância de Calendar com a data 07/Março/2014 (sem horário)
	 */
	public static Calendar getDataMudancaSemanaCinematografica() {
		final int anoMudancaSemanaCinematografica = 2014;
		final int diaMudancaSemanaCinematografica = 7;
		Calendar dtUltimaSemCinematograficaComecaSexta = Calendar.getInstance();

		dtUltimaSemCinematograficaComecaSexta.set(anoMudancaSemanaCinematografica, Calendar.MARCH,
				diaMudancaSemanaCinematografica);
		DateTimeUtils.limparHoras(dtUltimaSemCinematograficaComecaSexta);
		return dtUltimaSemCinematograficaComecaSexta;
	}

	/**
	 * @param ano
	 *            Ano da data
	 * @param mes
	 *            Mês da data (será subtraído 1 para adequar ao java.util.Date)
	 * @param dia
	 *            Dia da data
	 * 
	 * @return Retorna instância de Calendar com a data preenchida (sem info de
	 *         horas)
	 */
	public static Calendar getCalendar(int ano, int mes, int dia) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(ano, mes - 1, dia);
		return c;
	}

	/**
	 * Formata uma data passada como dd/MM/yyyy
	 * 
	 * @param cal
	 *            Calendar a ser formatado
	 * 
	 * @return String com data no formato dd/MM/yyyy. Ex: 14/05/2014. Caso o
	 *         {@code Calendar} passado seja nulo, então será retornada a String
	 *         "Data não informada"
	 */
	public static String getDataFormatada(Calendar cal) {
		if (cal != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.format(cal.getTime());
		} else {
			return "Data não informada";
		}
	}

	/**
	 * @return Data de ontem com elementos de horas zerados
	 */
	public static Calendar getOntem() {
		Calendar ontem = Calendar.getInstance();
		ontem.add(Calendar.DAY_OF_MONTH, -1);
		limparHoras(ontem);
		return ontem;
	}

	/**
	 * @return Data corrente com elementos de horas zerados
	 */
	public static Calendar getHoje() {
		Calendar hoje = Calendar.getInstance();
		limparHoras(hoje);
		return hoje;
	}

	/**
	 * @return Data corrente com elementos de horas zerados
	 */
	public static Calendar getAmanha() {
		Calendar amanha = Calendar.getInstance();
		amanha.add(Calendar.DAY_OF_MONTH, 1);
		limparHoras(amanha);
		return amanha;
	}

	/***
	 * Calcula o dia da semana ordinal em um mês. Ex. Primeiro domingo de abril
	 * em 2000, último sábado de julho em 1979
	 * 
	 * @param ano
	 *            Ano que será analisado
	 * @param mes
	 *            Mês que será analisado
	 * @param ordinal
	 *            Ordem do dia da semana procurado
	 * @param diaSemana
	 *            Dia da Semana procurado
	 * 
	 * @return A data que corresponde ao {@code ordinal} dia da semana
	 *         {@code diaSemana} no mês de {@code mes} do ano de {@code ano}
	 */
	public static Calendar getDiaSemanaOrdinal(int ano, int mes, int ordinal, int diaSemana) {

		Calendar ini = null;
		// Se não foi solicitado o último [diaSemana]
		if (ordinal != OrdinalDiaSemana.ULTIMO) {
			// Inicia a variável com o primeiro dia do mês/ano
			ini = DateTimeUtils.getCalendar(ano, mes, 1);
			// Seta o Calendar para o primeiro [diaSemana] passado
			while (ini.get(Calendar.DAY_OF_WEEK) != diaSemana) {
				ini.add(Calendar.DAY_OF_MONTH, 1);
			}
			ini.add(Calendar.DAY_OF_MONTH, (ordinal - 1) * 7);
		}
		// Se foi solicitado o ÚLTIMO [dia_semana] do mês então muda-se a lógica
		// ligeiramente
		else {
			// Inicia a variável com o primeiro dia do mês/ano
			ini = DateTimeUtils.getCalendar(ano, mes, 1);
			// adiciona um mês e volta um dia
			ini.add(Calendar.MONTH, 1);
			ini.add(Calendar.DAY_OF_MONTH, -1);
			// Seta o Calendar para o primeiro [diaSemana] que for encontrado
			while (ini.get(Calendar.DAY_OF_WEEK) != diaSemana) {
				ini.add(Calendar.DAY_OF_MONTH, -1);
			}
		}
		return ini;
	}

	/***
	 * Retorna o dia em que cai o domingo de páscoa em um determinado ano
	 * utilizando o algoritmo conhecido como Algoritmo de Meeus/Jones/Butcher
	 * 
	 * A Páscoa é celebrada no primeiro domingo após a primeira lua cheia que
	 * ocorre depois do equinócio da Primavera (no hemisfério norte, outono no
	 * hemisfério sul), ou seja, é equivalente à antiga regra de que seria o
	 * primeiro Domingo após o 14º dia do mês lunar de Nissan. Poderá assim
	 * ocorrer entre 22 de Março e 25 de Abril.
	 * 
	 * @param ano
	 *            Ano para o qual se queira saber qual a data
	 * @return Calendar com a data em que se comemora o domingo de páscoa do ano
	 *         informado
	 */
	public static Calendar getDomingoPascoa(int ano) {
		int a = ano % 19;
		int b = ano / 100;
		int c = ano % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + 2 * e + 2 * i - h - k) % 7;
		int m = (a + 11 * h + 22 * l) / 451;
		int mes = (h + l - 7 * m + 114) / 31;
		int dia = ((h + l - 7 * m + 114) % 31) + 1;

		return DateTimeUtils.getCalendar(ano, mes, dia);
	}

	/***
	 * Retorna o dia em que cai a terça de carnaval em um determinado ano
	 * utilizando o algoritmo conhecido como Algoritmo de Meeus/Jones/Butcher
	 * 
	 * @param ano
	 *            Ano para o qual se queira obter a data do carnaval
	 * 
	 * @return Calendar com a data em que se comemora a terça de carnaval do ano
	 *         informado
	 */
	public static Calendar getTercaCarnaval(int ano) {
		Calendar carnaval = DateTimeUtils.getDomingoPascoa(ano);
		carnaval.add(Calendar.DAY_OF_MONTH, -47);
		return carnaval;
	}

}
