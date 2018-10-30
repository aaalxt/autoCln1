package util;

import org.test4j.module.database.dbop.DBOperator;

public class DBOperatorCommon {
	
	private static DBOperator dbo;
	
	public static DBOperator getInstance(){
		if (dbo != null) return dbo;
		else {
			dbo = new DBOperator();
			return dbo;
		}
	}
}
