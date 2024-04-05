package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    private static final String tableName = "User";
    private static final String idField = "id";
    private static final String nameField = "name";
    private static final String ageField = "age";
    private static final String lastnameField = "lastname";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS " + tableName +
                " (" +
                idField + " BIGINT primary key auto_increment, " +
                nameField + " VARCHAR(10), " +
                lastnameField + " VARCHAR(10), " +
                ageField + " TINYINT) ENGINE=InnoDB   DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT";

        executeQuery(queryString);
    }

    public void dropUsersTable() {
        String queryString = "DROP TABLE IF EXISTS " + tableName;

        executeQuery(queryString);
    }

    public void saveUser(String name, String lastName, byte age) {
        String queryString = "INSERT INTO " + tableName +
                " (" + nameField + "," + lastnameField + "," + ageField + ") " +
                "VALUES (?,?,?)";
        Map<Integer, String> parameters = new HashMap<>();

        parameters.put(1, name);
        parameters.put(2, lastName);
        parameters.put(3, String.valueOf(age));
        executeQuery(queryString, parameters);
    }

    public void removeUserById(long id) {
        String queryString = "DELETE FROM " + tableName +
                " WHERE " + tableName + "." + idField + "=?";
        Map<Integer, String> parameters = new HashMap<>();

        parameters.put(1, String.valueOf(id));
        executeQuery(queryString, parameters);
    }

    public List<User> getAllUsers() {
        String queryString ="SELECT * FROM " + tableName;
        List<User> userList = new ArrayList<>();
        User user;

        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(queryString)) {

            while (resultSet.next()) {
                user = new User(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Byte.parseByte(resultSet.getString(4)));
                user.setId(resultSet.getLong(1));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        String queryString = "TRUNCATE TABLE " + tableName;

        executeQuery(queryString);
    }

    private void executeQuery(String query) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeQuery(String query, Map<Integer, String> parameters) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            fillStatementParameters(statement, parameters).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement fillStatementParameters(PreparedStatement statement, Map<Integer, String> parameters) throws SQLException {
        if (parameters != null) {
            Set<Integer> keySet = parameters.keySet();
            for (Integer key : keySet) {
                statement.setString(key, parameters.getOrDefault(key, ""));
            }
        }
        return statement;
    }
}
