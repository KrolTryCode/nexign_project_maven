package nexign_project_maven.cdr;


import java.sql.*;

import org.h2.tools.TriggerAdapter;
import java.sql.Connection;
import java.sql.SQLException;
public class ManageCdr extends TriggerAdapter  {
    @Override
    public void fire(Connection connection, ResultSet resultSet, ResultSet resultSet1) throws SQLException {
        if (resultSet1.next()) {
            StringBuilder sb = new StringBuilder();
            sb.append(resultSet1.getString("call_type")).append(",");
            sb.append(resultSet1.getString("subscriber_number")).append(",");
            sb.append(resultSet1.getString("contact_number")).append(",");
            sb.append(resultSet1.getLong("start_time")).append(",");
            sb.append(resultSet1.getLong("end_time"));

            CdrGenerator.generateCdr(sb.toString());
        }
    }
}
