package dataAccess;

import dataAccess.libs.DBConnection;
import dataAccess.libs.DBQuery;

public abstract class DAO {
	protected DBConnection conn = new DBConnection();
	protected DBQuery dbQuery = new DBQuery();
}
