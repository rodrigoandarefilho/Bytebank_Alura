package br.com.alura.bytebank;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection recuperarConexao() {
        try {
            return createDataSource().getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private HikariDataSource createDataSource() {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost/byte_bank");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("1234");
        hikariConfig.setMaximumPoolSize(10);

        return new HikariDataSource(hikariConfig);
    }
}
