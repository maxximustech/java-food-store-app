/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import main.models.User;
import main.utils.Database;

import java.sql.*;
import java.util.ArrayList;

/**
 * User Data Access Object - Responsible for all methods relating to {@link main.models.User} model
 * @see main.models.User
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class UserDAO {
    /**
     * Finds all users list from database.
     *
     * @return {@link ArrayList} of {@link main.models.User} model
     */
    public ArrayList<User> findAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return users;
            }
            PreparedStatement statement = con.prepareStatement("SELECT * FROM users ORDER BY id DESC");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                users.add(new User(result.getInt("id"),result.getString("username"),
                        result.getString("password"), Date.valueOf(result.getString("createdOn"))));
            }
            return users;
        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Adds a new user to the database.
     *
     * @param user the {@link main.models.User} model to be added
     * @return {@link Boolean} - true if the user was added successfully, false - if otherwise
     */
    public boolean addUser(User user){
        try {
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "INSERT INTO users (username,password,createdOn) VALUES (?,?,DATE('now'))";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setString(1, user.getUsername());
                readyStatement.setString(2, BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
                readyStatement.executeUpdate();
                readyStatement.close();
                connection.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Authenticate as a user.
     *
     * @param username the username
     * @param password the password
     * @return {@link main.models.User} if username and password matches database credentials, {@link null} if otherwise
     */
    public User loginAsUser(String username, String password){
        User user = null;
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return null;
            }
            PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                user = new User(result.getInt("id"),result.getString("username"),
                        result.getString("password"),Date.valueOf(result.getString("createdOn")));
            }
            if(user == null || !BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified){
                return null;
            }
            return user;
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Changes a user password.
     *
     * @param username    the username
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return {@link Boolean} - true if the user password was changed successfully, false - if otherwise
     */
    public boolean changeUserPassword(String username, String oldPassword, String newPassword){
        try{
            User user = loginAsUser(username,oldPassword);
            if(user == null){
                return false;
            }
            Connection connection = new Database().getConnection();
            if (connection == null) {
                return false;
            }
            String sqlQuery = "UPDATE users SET password = ? WHERE id = ?";
            PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
            readyStatement.setString(1, BCrypt.withDefaults().hashToString(12, newPassword.toCharArray()));
            readyStatement.setInt(2, user.getId());
            readyStatement.executeUpdate();
            readyStatement.close();
            connection.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a user from database.
     *
     * @param id the user id
     * @return {@link Boolean} - true if the user was deleted successfully, false - if otherwise
     */
    public boolean deleteUser(int id){
        try {
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "DELETE FROM users WHERE id = ?";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setInt(1, id);
                readyStatement.executeUpdate();
                readyStatement.close();
                connection.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
