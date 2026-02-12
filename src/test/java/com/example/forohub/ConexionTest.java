package com.example.forohub;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest(properties = {"spring.flyway.enabled=false"})
class ConexionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void probarConexion() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("¡Conexión exitosa!: " + connection.getMetaData().getDatabaseProductName());
        }
    }
}