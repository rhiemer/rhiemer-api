package br.com.rhiemer.api.util.trace;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class AdvancedStopWatch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5277315217635107997L;

	/**
	 * Identifier of this stop watch. Handy when we have output from multiple
	 * stop watches and need to distinguish between them in log or console
	 * output.
	 */
	private final String id;

	private final Queue<AdvancedTaskInfo> taskList = new LinkedList<AdvancedTaskInfo>();
	private final Stack<AdvancedTaskInfo> runningTasks = new Stack<AdvancedTaskInfo>();
	private long totalTimeMillis = 0;

	/**
	 * Construct a new stop watch with the given id. Does not start any task.
	 * 
	 * @param id
	 *            identifier for this stop watch. Handy when we have output from
	 *            multiple stop watches and need to distinguish between them.
	 */
	public AdvancedStopWatch(String id) {
		this.id = id;
	}

	/**
	 * Formatar milisegundos em hh:mm:ss,sss
	 * 
	 * @param milis
	 * @return
	 */
	private String formatarTempo(long milis) {
		int hrs = (int) TimeUnit.MILLISECONDS.toHours(milis) % 24;
		int min = (int) TimeUnit.MILLISECONDS.toMinutes(milis) % 60;
		int sec = (int) TimeUnit.MILLISECONDS.toSeconds(milis) % 60;
		int milSec = (int) milis % 1000;

		String timeString = String.format("%02d:%02d:%02d,%03d", hrs, min, sec,
				milSec);
		return timeString;
	}

	/**
	 * Start a named task. The results are undefined if {@link #stop()} or
	 * timing methods are called without invoking this method.
	 * 
	 * @param taskName
	 *            the name of the task to start
	 * @see #stop()
	 */
	public void start(String taskName) throws IllegalStateException {
		long timeMills = System.currentTimeMillis();
		AdvancedTaskInfo taskInfo = new AdvancedTaskInfo(taskName, timeMills,
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS")
						.format(new Date(timeMills)));

		runningTasks.push(taskInfo);

	}

	/**
	 * Stop the current task. The results are undefined if timing methods are
	 * called without invoking at least one pair {@link #start()} /
	 * {@link #stop()} methods.
	 * 
	 * @see #start()
	 */
	public void stop() throws IllegalStateException {

		long lastTime = System.currentTimeMillis();
		AdvancedTaskInfo taskInfo = runningTasks.pop();
		taskInfo.setTimeMillis(lastTime);
		taskInfo.setEndDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS")
				.format(new Date(lastTime)));
		this.totalTimeMillis += taskInfo.getTimeMillis();
		this.taskList.add(taskInfo);

	}

	public void stop(String status) throws IllegalStateException {

		getCurrentTask().setStatus(status);
		stop();

	}

	/**
	 * Return the total time in milliseconds for all tasks.
	 */
	public long getTotalTimeMillis() {
		return this.totalTimeMillis;
	}

	/**
	 * Return the total time in seconds for all tasks.
	 */
	public double getTotalTimeSeconds() {
		return this.totalTimeMillis / 1000.0;
	}

	/**
	 * Return the total time in minutes for all tasks
	 * 
	 * @return minutes
	 */
	public double getTotalTimeMinutes() {
		return this.getTotalTimeSeconds() / 60;
	}

	/**
	 * Return a short description of the total running time.
	 */
	public String shortSummary() {
		return new StringBuilder("Análise da Requisição").append(" '")
				.append(this.id).append("' ").append(escreverTempoDeExecucao())
				.toString();

	}

	public String escreverTempoDeExecucao() {
		StringBuilder sb = new StringBuilder("tempo (millis) = ");
		try {
			sb.append(getTotalTimeMillis());
			if (getTotalTimeMillis() >= 1000) {
				String timeString = formatarTempo(getTotalTimeMillis());
				sb.append(" | ").append(timeString);
			}

		} catch (Exception e) {
			sb.append(getTotalTimeMillis());
		}
		return sb.toString();
	}

	public AdvancedTaskInfo getCurrentTask() {
		return runningTasks.peek();
	}

	public boolean hasTasks() {
		return !taskList.isEmpty();
	}

	/**
	 * Return a string with a table describing all tasks performed. For custom
	 * reporting, call getTaskInfo() and use the task info directly.
	 */
	public String prettyPrint() {
		if (!taskList.isEmpty()) {
			StringBuilder sb = new StringBuilder(shortSummary());
			sb.append('\n');

			sb.append("----------------------------------------------------------------------------------\n");
			sb.append("t\t\t\t\t%\t\tStatus\tMétodo\t|ID\t|Intervalo\n");
			sb.append("----------------------------------------------------------------------------------\n");
			NumberFormat pf = NumberFormat.getPercentInstance();
			pf.setMinimumIntegerDigits(3);
			pf.setGroupingUsed(false);

			List<String> stringsList = new ArrayList<>();
			while (!taskList.isEmpty()) {
				AdvancedTaskInfo task = taskList.poll();
				StringBuilder sbLista = new StringBuilder();
				sbLista.append(formatarTempo(task.getTimeMillis())).append("\t");
				sbLista.append(
						pf.format(task.getTimeSeconds() / getTotalTimeSeconds()))
						.append("\t");
				sbLista.append(task.getStatus()).append("\t");
				sbLista.append(task.getTaskName()).append("\t|");
				sbLista.append(task.getStartDate()).append(" - ");
				sbLista.append(task.getEndDate());
				sbLista.append("\n");
				stringsList.add(0,sbLista.toString());
			}
			
			stringsList.forEach(t->sb.append(t));
			
			return sb.toString();
		}
		return "";
	}

	/**
	 * Inner class to hold data about one task executed within the stop watch.
	 */
	public static final class AdvancedTaskInfo  implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -365470510139969486L;
		private String taskName;
		private String startDate;
		private String endDate;
		private long timeMillis;
		private long startTimeMillis;
		private String status;

		AdvancedTaskInfo(String taskName, long startTimeMillis, String startDate) {
			this.taskName = taskName;
			this.startTimeMillis = startTimeMillis;
			this.startDate = startDate;
		}

		/**
		 * Return the name of this task.
		 */
		public String getTaskName() {
			return this.taskName;
		}

		/**
		 * Return the time in milliseconds this task took.
		 */
		public long getTimeMillis() {
			return this.timeMillis;
		}

		/**
		 * Return the time in seconds this task took.
		 */
		public double getTimeSeconds() {
			return this.timeMillis / 1000.0;
		}

		public String getStartDate() {
			return startDate;
		}

		public long getStartTimeMillis() {
			return startTimeMillis;
		}

		public void setTimeMillis(long timeMillis) {
			this.timeMillis = timeMillis - startTimeMillis;
		}

		public String print(long totalTimeSeconds) {
			StringBuilder sb = new StringBuilder("[" + taskName + "] took ");

			sb.append(getTimeMillis());
			long percent = Math.round((100.0 * getTimeSeconds())
					/ totalTimeSeconds);
			sb.append(" = ").append(percent).append("%");
			return sb.toString();
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}
}
