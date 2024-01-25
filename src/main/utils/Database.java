/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.utils;

import java.sql.*;


/**
 * The Database class.
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class Database {
    private Connection connection = null;

    /**
     * Creates a new Database instance.
     *
     * @throws SQLException the sql exception
     */
    public Database() throws SQLException{
        this.connection = DriverManager.getConnection(Constants.dbUrl);
    }

    /**
     * Gets database connection.
     *
     * @return Sql {@link Connection} if successfully connected, throws error if otherwise
     * @throws Exception the exception
     */
    public Connection getConnection() throws Exception {
        if(connection == null){
            throw new Exception("Connection could not be established with the database!");
        }
        return connection;
    }

    /**
     * Initializes database tables.
     *
     * @param dropTable checks whether to drop all tables before creating tables. Set to {@link true} if to drop all tables, set to {@link false} if otherwise
     * @throws Exception the exception
     */
    public void initDBTables(boolean dropTable) throws Exception {
        Connection connection = this.getConnection();
        Statement statement = connection.createStatement();
        if(dropTable){
            for(String sql : new Helper().readFile("dropDB.sql").split(";")){
                statement.addBatch(sql);
            }
        }
        for(String sql : new Helper().readFile("createDB.sql").split(";")){
            statement.addBatch(sql);
        }
        statement.executeBatch();
        statement.close();
        connection.close();
    }
}
