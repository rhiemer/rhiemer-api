package br.com.rhiemer.api.dbunit.operation;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.CompositeOperation;
import org.dbunit.operation.DatabaseOperation;

import br.com.rhiemer.api.util.helper.Helper;

public class CompositeOperationAPP extends CompositeOperation implements ICompositeOperationAPP {

	private DatabaseOperation[] _actionsInverse = null;
	private DatabaseOperation[] _actions = null;

	public CompositeOperationAPP(DatabaseOperation action1, DatabaseOperation action2) {
		super(action1, action2);
		_actionsInverse = new DatabaseOperation[] { action1 };
		_actions = new DatabaseOperation[] { action2 };
	}

	public CompositeOperationAPP(DatabaseOperation[] actions) {
		super(actions);
		_actions = actions;
	}

	public CompositeOperationAPP(DatabaseOperation[] actionsInverse, DatabaseOperation[] actions) {
		super(actions);
		_actionsInverse = actionsInverse;
		_actions = actions;
	}

	/* (non-Javadoc)
	 * @see br.com.rhiemer.api.dbunit.operation.ICompositeOperationAPP#execute(org.dbunit.database.IDatabaseConnection, org.dbunit.dataset.IDataSet)
	 */
	@Override
	public void execute(IDatabaseConnection connection, IDataSet... dataSet)
			throws DatabaseUnitException, SQLException {
		List<IDataSet> dataSets = Helper.convertArgs(dataSet);
		for (int i = 0; i < _actionsInverse.length; i++) {
			DatabaseOperation action = _actionsInverse[i];
			for (int i2 = dataSets.size() - 1; i2 >= 0; i2--) {
				action.execute(connection, dataSets.get(i2));
			}
		}

		for (int i = 0; i < _actions.length; i++) {
			DatabaseOperation action = _actions[i];
			for (int i2 = 0; i2 < dataSets.size(); i2++) {
				action.execute(connection, dataSets.get(i2));
			}
		}
	}

	/* (non-Javadoc)
	 * @see br.com.rhiemer.api.dbunit.operation.ICompositeOperationAPP#executeByDataSet(org.dbunit.database.IDatabaseConnection, org.dbunit.dataset.IDataSet)
	 */
	@Override
	public void executeByDataSet(IDatabaseConnection connection, IDataSet... dataSet)
			throws DatabaseUnitException, SQLException {
		List<IDataSet> dataSets = Helper.convertArgs(dataSet);
		for (IDataSet dataSetLoop : dataSets) {
			;
			for (int i2 = 0; i2 < _actionsInverse.length; i2++) {
				_actionsInverse[i2].execute(connection, dataSetLoop);

			}
			for (int i2 = 0; i2 < _actions.length; i2++) {
				_actions[i2].execute(connection, dataSetLoop);
			}

		}

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getClass().getName()).append("[");
		sb.append("_actions=").append(this._actions == null ? "null" : Arrays.asList(this._actions).toString());
		sb.append("_actionsInverse=")
				.append(this._actionsInverse == null ? "null" : Arrays.asList(this._actionsInverse).toString());
		sb.append("]");
		return sb.toString();
	}

}
