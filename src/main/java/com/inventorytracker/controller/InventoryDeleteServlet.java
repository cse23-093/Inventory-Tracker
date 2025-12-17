package com.inventorytracker.controller;

import com.inventorytracker.dao.InventoryItemDAO;
import com.inventorytracker.dao.InventoryItemDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/inventory/delete")
public class InventoryDeleteServlet extends HttpServlet {

    private final InventoryItemDAO dao = new InventoryItemDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = parseIntSafe(req.getParameter("id"), -1);
            if (id >= 0) {
                dao.delete(id);
            }
            resp.sendRedirect(req.getContextPath() + "/inventory");
        } catch (Exception e) {
            throw new ServletException("Failed to delete item.", e);
        }
    }

    private int parseIntSafe(String value, int fallback) {
        try { return Integer.parseInt(value); } catch (Exception e) { return fallback; }
    }
}
