package com.fcs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * Created by fengcs on 2018/6/21.
 */
public class BasicTest {

    private HikariDataSource ds;

    @Before
    public void setup() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/design");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("fengcs");
        config.setMinimumIdle(1);
        config.setMaximumPoolSize(2);

        ds = new HikariDataSource(config);
    }

    @After
    public void teardown() {
        ds.close();
    }

    @Test
    public void connectionTest() throws SQLException {
        Connection connection = ds.getConnection();
        Statement statement =  connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select id from tb_user");
        String firstValue;
        while (resultSet.next()) {
            firstValue = resultSet.getString(1);
            System.out.println(firstValue);
        }
    }


}
