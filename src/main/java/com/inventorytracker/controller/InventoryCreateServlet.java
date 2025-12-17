package com.inventorytracker.controller;

import com.inventorytracker.dao.InventoryItemDAO;
import com.inventorytracker.dao.InventoryItemDAOImpl;
import com.inventorytracker.model.InventoryItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/inventory/create")
public class InventoryCreateServlet extends HttpServlet {

    private final InventoryItemDAO dao = new InventoryItemDAOImpl();

    // Show create form
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("mode", "create");
        req.setAttribute("item", new InventoryItem());
        req.getRequestDispatcher("/views/inventory-form.jsp").forward(req, resp);
    }

    // Perform create
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            InventoryItem item = new InventoryItem();
            item.setName(req.getParameter("name"));
            item.setCategory(req.getParameter("category"));
            item.setQuantity(parseIntSafe(req.getParameter("quantity"), -1));

            String error = item.validate();
            if (error != null) {
                req.setAttribute("mode", "create");
                req.setAttribute("item", item);
                req.setAttribute("error", error);
                req.getRequestDispatcher("/views/inventory-form.jsp").forward(req, resp);
                return;
            }

            dao.create(item);
            resp.sendRedirect(req.getContextPath() + "/inventory");
        } catch (Exception e) {
            throw new ServletException("Failed to create item.", e);
        }
    }

    private int parseIntSafe(String value, int fallback) {
        try { return Integer.parseInt(value); } catch (Exception e) { return fallback; }
    }
}
