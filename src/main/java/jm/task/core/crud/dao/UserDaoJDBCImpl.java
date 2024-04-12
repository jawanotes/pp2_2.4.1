package jm.task.core.crud.dao;

import jm.task.core.crud.model.User;
import jm.task.core.crud.util.Util;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    private static final String TABLE_NAME = "User";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String AGE_FIELD = "age";
    private static final String LASTNAME_FIELD = "lastname";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" +
                ID_FIELD + " BIGINT primary key auto_increment, " +
                NAME_FIELD + " VARCHAR(10), " +
                LASTNAME_FIELD + " VARCHAR(10), " +
                AGE_FIELD + " TINYINT) ENGINE=InnoDB   DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT";

        executeQuery(queryString);
    }

    public void dropUsersTable() {
        String queryString = "DROP TABLE IF EXISTS " + TABLE_NAME;

        executeQuery(queryString);
    }

    public void saveUser(String name, String lastName, byte age) {
        String queryString = "INSERT INTO " + TABLE_NAME +
                " (" + NAME_FIELD + "," + LASTNAME_FIELD + "," + AGE_FIELD + ") " +
                "VALUES (?,?,?)";
        Map<Integer, String> parameters = new HashMap<>();

        parameters.put(1, name);
        parameters.put(2, lastName);
        parameters.put(3, String.valueOf(age));
        executeQuery(queryString, parameters);
    }

    public void removeUserById(long id) {
        String queryString = "DELETE FROM " + TABLE_NAME +
                " WHERE " + TABLE_NAME + "." + ID_FIELD + "=?";
        Map<Integer, String> parameters = new HashMap<>();

        parameters.put(1, String.valueOf(id));
        executeQuery(queryString, parameters);
    }

    public List<User> getAllUsers() {
        String queryString ="SELECT * FROM " + TABLE_NAME;
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
        String queryString = "TRUNCATE TABLE " + TABLE_NAME;

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
