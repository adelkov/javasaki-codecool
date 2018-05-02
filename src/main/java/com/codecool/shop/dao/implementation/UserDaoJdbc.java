package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.User;
import com.codecool.shop.dao.implementation.DBConnector;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class UserDaoJdbc {
    void add(User user){
        String query ="INSERT INTO users (name, email, hash_password)" +
                "VALUES (?,?,?)";
        String[] params = {user.getName(), user.getEmail(), user.getPassword()};
        DBConnector.getInstance().executeQuery(query, params);
    }

    User find(int id){
        String query ="";
        String[] params = {};
        ResultSet res = DBConnector.getInstance().executeSelectQuery(query, params);
        return null;
    }

    void remove(int id){

    }

    List<User> getAll(){


        return null;
    }
}
