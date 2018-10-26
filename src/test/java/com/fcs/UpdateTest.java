package com.fcs;

import com.fcs.common.MultiTask;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Test for update table set num = num + 1 where id = ? (primary key)
 * Created by fengcs on 2018/6/21.
 */
public class UpdateTest {

    private HikariDataSource ds;

    @Before
    public void setup() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/design");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("fengcs");
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(6);

        ds = new HikariDataSource(config);
    }

    @After
    public void teardown() {
        ds.close();
    }

    @Test
    public void testUpdate() throws SQLException, InterruptedException {
        final Connection connection = ds.getConnection();
        final Statement statement =  connection.createStatement();
        MultiTask multiTask = new MultiTask();
        multiTask.timeTasks(100, new Runnable() {
            @Override
            public void run() {
                try {
                    int status = statement.executeUpdate("update tb_user set num = num + 1 where id=1");
                    System.out.println(status);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
