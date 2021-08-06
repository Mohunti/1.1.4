package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import static jm.task.core.jdbc.util.Util.getConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String myTable = "CREATE TABLE if not exists users"
                + "(id BIGINT(12) NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(40),"
                + "lastName VARCHAR(40),"
                + "age TINYINT(2),"
                + "PRIMARY KEY(ID))";
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(myTable);
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("rollback выполнен");
            } catch (SQLException E) {
                System.out.println("rollback не выполнен");
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE if exists users ");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("rollback выполнен");
            } catch (SQLException E) {
                System.out.println("rollback не выполнен");
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO users (name, lastName, age) Values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, String.valueOf(age));
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("rollback выполнен");
            } catch (SQLException E) {
                System.out.println("rollback не выполнен");
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM users WHERE Id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("rollback выполнен");
            } catch (SQLException E) {
                System.out.println("rollback не выполнен");
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                listUser.add(user);
                connection.commit();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("rollback выполнен");
            } catch (SQLException E) {
                System.out.println("rollback не выполнен");
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(listUser);
        return listUser;
    }

    public void cleanUsersTable() {
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            String SQL = "TRUNCATE TABLE users";
            statement.executeUpdate(SQL);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("rollback выполнен");
            } catch (SQLException E) {
                System.out.println("rollback не выполнен");
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

