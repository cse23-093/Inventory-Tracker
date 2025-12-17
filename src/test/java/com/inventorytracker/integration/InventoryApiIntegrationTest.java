package com.inventorytracker.integration;

import com.inventorytracker.controller.InventoryApiServlet;
import com.inventorytracker.util.DBConnectionUtil;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class InventoryApiIntegrationTest {

    private static Tomcat tomcat;
    private static int port;

    @BeforeAll
    static void startServer() throws Exception {
        // --- Setup in-memory DB for the API endpoint ---
        JdbcDataSource h2 = new JdbcDataSource();
        h2.setURL("jdbc:h2:mem:inventory_integration;MODE=MySQL;DB_CLOSE_DELAY=-1");
        h2.setUser("sa");
        h2.setPassword("");

        DataSource ds = h2;
        DBConnectionUtil.setDataSourceForTests(ds);

        try (Connection c = ds.getConnection(); Statement st = c.createStatement()) {
            st.execute("""
                CREATE TABLE inventory_item (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    quantity INT NOT NULL,
                    category VARCHAR(50) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP NULL
                )
            """);
            st.execute("""
                INSERT INTO inventory_item (name, quantity, category) VALUES
                ('HDMI Cable', 3, 'Electronics'),
                ('Office Chair', 7, 'Furniture')
            """);
        }

        // --- Start embedded Tomcat ---
        tomcat = new Tomcat();
        tomcat.setBaseDir("target/tomcat");

        port = 0; // auto
        tomcat.setPort(0);

        // Minimal context
        File docBase = new File("src/main/webapp");
        Context ctx = tomcat.addWebapp("", docBase.getAbsolutePath());

        // Add servlet programmatically (no annotation scanning needed)
        Tomcat.addServlet(ctx, "InventoryApiServlet", new InventoryApiServlet());
        ctx.addServletMappingDecoded("/api/inventory", "InventoryApiServlet");

        tomcat.start();
        port = tomcat.getConnector().getLocalPort();
    }

    @AfterAll
    static void stopServer() throws Exception {
        if (tomcat != null) tomcat.stop();
        DBConnectionUtil.clearDataSourceForTests();
    }

    @Test
    void apiInventory_returnsJson_andContainsSeededItems() throws Exception {
        URL url = new URL("http://localhost:" + port + "/api/inventory");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        assertEquals(200, status);

        StringBuilder body = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) body.append(line);
        }

        String json = body.toString();
        assertTrue(json.startsWith("["));
        assertTrue(json.contains("\"name\":\"HDMI Cable\""));
        assertTrue(json.contains("\"category\":\"Electronics\""));
    }
}
