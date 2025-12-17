package com.inventorytracker.dao;

import com.inventorytracker.model.InventoryItem;
import com.inventorytracker.util.DBConnectionUtil;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemDAOTest {

    private static DataSource ds;
    private InventoryItemDAO dao;

    @BeforeAll
    static void setupDb() throws Exception {
        JdbcDataSource h2 = new JdbcDataSource();
        h2.setURL("jdbc:h2:mem:inventory_test;MODE=MySQL;DB_CLOSE_DELAY=-1");
        h2.setUser("sa");
        h2.setPassword("");
        ds = h2;

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
                ('USB Keyboard', 12, 'Electronics'),
                ('HDMI Cable', 3, 'Electronics')
            """);
        }
    }

    @AfterAll
    static void teardown() {
        DBConnectionUtil.clearDataSourceForTests();
    }

    @BeforeEach
    void init() {
        dao = new InventoryItemDAOImpl();
    }

    @Test
    void findAll_returnsSeededItems() throws Exception {
        List<InventoryItem> items = dao.findAll();
        assertTrue(items.size() >= 2);
    }

    @Test
    void create_insertsItem_andFindByIdWorks() throws Exception {
        InventoryItem item = new InventoryItem("Notebook A4", 2, "Stationery");
        int id = dao.create(item);
        assertTrue(id > 0);

        InventoryItem found = dao.findById(id);
        assertNotNull(found);
        assertEquals("Notebook A4", found.getName());
        assertEquals(2, found.getQuantity());
        assertEquals("Stationery", found.getCategory());
    }

    @Test
    void update_modifiesExistingItem() throws Exception {
        InventoryItem first = dao.findAll().get(0);
        first.setName(first.getName() + " Updated");
        first.setQuantity(first.getQuantity() + 1);

        boolean ok = dao.update(first);
        assertTrue(ok);

        InventoryItem again = dao.findById(first.getId());
        assertEquals(first.getName(), again.getName());
        assertEquals(first.getQuantity(), again.getQuantity());
    }

    @Test
    void delete_removesItem() throws Exception {
        InventoryItem item = new InventoryItem("Temp Item", 1, "Temp");
        int id = dao.create(item);

        assertTrue(dao.existsById(id));
        assertTrue(dao.delete(id));
        assertFalse(dao.existsById(id));
    }
}
