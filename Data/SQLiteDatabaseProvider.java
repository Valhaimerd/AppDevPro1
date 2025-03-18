package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDatabaseProvider implements IDatabaseProvider {
    private static final String DATABASE_PATH = "jdbc:sqlite:Data/java.db";

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_PATH);
    }
}
