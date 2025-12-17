package com.inventorytracker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

    @Test
    void lowStock_isTrue_whenQuantityBelow5() {
        InventoryItem item = new InventoryItem("HDMI Cable", 4, "Electronics");
        assertTrue(item.isLowStock());
    }

    @Test
    void lowStock_isFalse_whenQuantityIs5OrMore() {
        InventoryItem item = new InventoryItem("HDMI Cable", 5, "Electronics");
        assertFalse(item.isLowStock());
    }

    @Test
    void validate_returnsError_whenNameMissing() {
        InventoryItem item = new InventoryItem("", 10, "Electronics");
        assertNotNull(item.validate());
    }

    @Test
    void validate_returnsError_whenCategoryMissing() {
        InventoryItem item = new InventoryItem("Keyboard", 10, " ");
        assertNotNull(item.validate());
    }

    @Test
    void validate_returnsError_whenQuantityNegative() {
        InventoryItem item = new InventoryItem("Keyboard", -1, "Electronics");
        assertNotNull(item.validate());
    }

    @Test
    void validate_returnsNull_whenValid() {
        InventoryItem item = new InventoryItem("Keyboard", 10, "Electronics");
        assertNull(item.validate());
    }
}
