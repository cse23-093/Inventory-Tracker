package com.inventorytracker.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public final class DBConnectionUtil {

    private static volatile DataSource dataSource;

    private DBConnectionUtil() {}

    private static DataSource getDataSource() throws Exception {
        if (dataSource == null) {
            synchronized (DBConnectionUtil.class) {
                if (dataSource == null) {
                    Context ctx = new InitialContext();
                    dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/InventoryDB");
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() throws Exception {
        return getDataSource().getConnection();
    }

    // ✅ TEST SUPPORT (does not affect production)
    public static void setDataSourceForTests(DataSource ds) {
        dataSource = ds;
    }

    public static void clearDataSourceForTests() {
        dataSource = null;
    }
}
