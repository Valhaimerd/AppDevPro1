package Data;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseProvider {
    Connection getConnection() throws SQLException;
}
