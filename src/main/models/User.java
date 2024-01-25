/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.models;

import java.sql.Date;

/**
 * The User model.
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class User {
    private int id;
    private String username;
    private String password;
    private Date createdOn;

    /**
     * Creates a new User instance.
     *
     * @param id        the user id
     * @param username  the username
     * @param password  the user password
     * @param createdOn the user creation date
     */
    public User(int id, String username, String password, Date createdOn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdOn = createdOn;
    }

    /**
     * Creates a new User instance.
     *
     * @param username  the username
     * @param password  the user password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets created on.
     *
     * @return the created on
     */
    public Date getCreatedOn() {
        return createdOn;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
