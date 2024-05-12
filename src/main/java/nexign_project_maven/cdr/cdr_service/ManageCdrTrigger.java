package nexign_project_maven.cdr.cdr_service;

import java.sql.*;
import org.h2.tools.TriggerAdapter;
import java.sql.Connection;
import java.sql.SQLException;


public class ManageCdrTrigger extends TriggerAdapter  {
    @Override
    public void fire(Connection connection, ResultSet resultSet, ResultSet resultSet1) throws SQLException {
        if (resultSet1.next()) {
            String sb = resultSet1.getString("call_type") + "," +
                    resultSet1.getString("subscriber_number") + "," +
                    resultSet1.getString("contact_number") + "," +
                    resultSet1.getLong("start_time") + "," +
                    resultSet1.getLong("end_time");

            CdrGenerator.generateCdrFiles(sb);
        }
    }
}
