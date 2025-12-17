package com.inventorytracker.controller;

import com.inventorytracker.dao.InventoryItemDAO;
import com.inventorytracker.dao.InventoryItemDAOImpl;
import com.inventorytracker.model.InventoryItem;
import com.inventorytracker.util.ExportUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/inventory")
public class InventoryApiServlet extends HttpServlet {

    private final InventoryItemDAO dao = new InventoryItemDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<InventoryItem> items = dao.findAll();
            byte[] bytes = ExportUtil.toJsonBytes(items);

            resp.setContentType("application/json");
            resp.getOutputStream().write(bytes);
        } catch (Exception e) {
            throw new ServletException("Failed to fetch inventory API.", e);
        }
    }
}
