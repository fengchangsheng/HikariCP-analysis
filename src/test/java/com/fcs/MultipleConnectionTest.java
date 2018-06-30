package com.fcs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by fengcs on 2018/6/25.
 */
public class MultipleConnectionTest{

    private HikariDataSource ds;

    @Before
    public void setup() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/design");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("fengcs");
        config.setMinimumIdle(1);
        config.setMaximumPoolSize(3);

        ds = new HikariDataSource(config);
    }

    @After
    public void teardown() {
        ds.close();
    }

    @Test
    public void testMulConnection() throws InterruptedException {
        ConnectionThread connectionThread = new ConnectionThread();
        Thread thread = null;
        for (int i = 0; i < 5; i++) {
            thread = new Thread(connectionThread, "thread-con-"+i);
            thread.start();
            thread.join();
        }
//        Thread.sleep(5000);
    }

    private class ConnectionThread implements Runnable{

        @Override
        public void run() {
            Connection connection = null;
            try {
                connection = ds.getConnection();
                Statement statement =  connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select id from tb_user");
                String firstValue;
                System.out.println("<=============");
                System.out.println("==============>"+Thread.currentThread().getName() + ":");
                while (resultSet.next()) {
                    firstValue = resultSet.getString(1);
                    System.out.print(firstValue);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
