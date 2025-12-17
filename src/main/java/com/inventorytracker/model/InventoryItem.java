package com.inventorytracker.model;

import java.util.Objects;

public class InventoryItem {

    private int id;
    private String name;
    private int quantity;
    private String category;

    public InventoryItem() {}

    public InventoryItem(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public InventoryItem(int id, String name, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Business rules / methods
    public boolean isLowStock() {
        return quantity < 5;
    }

    public String normalizeName() {
        if (name == null) return null;
        name = name.trim();
        return name;
    }

    public String normalizeCategory() {
        if (category == null) return null;
        category = category.trim();
        return category;
    }

    public boolean isValid() {
        return validate() == null;
    }

    /**
     * @return null if valid, otherwise an error message for UI feedback
     */
    public String validate() {
        normalizeName();
        normalizeCategory();

        if (name == null || name.isBlank()) return "Item name is required.";
        if (name.length() > 100) return "Item name must be at most 100 characters.";
        if (category == null || category.isBlank()) return "Category is required.";
        if (category.length() > 50) return "Category must be at most 50 characters.";
        if (quantity < 0) return "Quantity cannot be negative.";
        return null;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryItem)) return false;
        InventoryItem that = (InventoryItem) o;
        return id == that.id &&
                quantity == that.quantity &&
                Objects.equals(name, that.name) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, category);
    }
}
