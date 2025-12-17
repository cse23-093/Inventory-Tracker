package com.inventorytracker.dao;

import com.inventorytracker.model.InventoryItem;

import java.util.List;

public interface InventoryItemDAO {

    // CRUD
    int create(InventoryItem item) throws Exception;
    InventoryItem findById(int id) throws Exception;
    List<InventoryItem> findAll() throws Exception;
    boolean update(InventoryItem item) throws Exception;
    boolean delete(int id) throws Exception;

    // Optional helpers (useful for UI/logic/tests)
    boolean existsById(int id) throws Exception;
    int countAll() throws Exception;
}
