package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    @Override
    public void add(User user) {
        try (Connection connection = DBConnector.getConnection();) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "INSERT INTO users (name, email, hash_password)" +
                            "VALUES (?, ?, ?)"
            );
            stmnt.setString(1, user.getName());
            stmnt.setString(2, user.getEmail());
            stmnt.setString(3, user.getPassword());

            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(int id) {
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?"
            );
            stmnt.setInt(1, id);
            stmnt.executeQuery();
            ResultSet resultSet = stmnt.getResultSet();
            System.out.println(resultSet.first());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?;"
            );
            stmnt.setInt(1, id);
            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM users;"
            );
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                allUsers.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;

    }

    private User map(ResultSet rs) throws SQLException {
        User user = new User("", "", "");
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("hash_password"));
        return user;
    }

    public int getLastId() {
        int lastId = 0;
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM users;"
            );
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    public void removeAll() {
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "DELETE FROM users WHERE 1=1"
            );
            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
