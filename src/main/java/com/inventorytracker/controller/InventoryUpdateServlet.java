package com.inventorytracker.controller;

import com.inventorytracker.dao.InventoryItemDAO;
import com.inventorytracker.dao.InventoryItemDAOImpl;
import com.inventorytracker.model.InventoryItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/inventory/update")
public class InventoryUpdateServlet extends HttpServlet {

    private final InventoryItemDAO dao = new InventoryItemDAOImpl();

    // Show edit form with prefilled data
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = parseIntSafe(req.getParameter("id"), -1);
            if (id < 0) {
                resp.sendRedirect(req.getContextPath() + "/inventory");
                return;
            }

            InventoryItem item = dao.findById(id);
            if (item == null) {
                resp.sendRedirect(req.getContextPath() + "/inventory");
                return;
            }

            req.setAttribute("mode", "update");
            req.setAttribute("item", item);
            req.getRequestDispatcher("/views/inventory-form.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Failed to load item for update.", e);
        }
    }

    // Perform update
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            InventoryItem item = new InventoryItem();
            item.setId(parseIntSafe(req.getParameter("id"), -1));
            item.setName(req.getParameter("name"));
            item.setCategory(req.getParameter("category"));
            item.setQuantity(parseIntSafe(req.getParameter("quantity"), -1));

            if (item.getId() < 0) {
                resp.sendRedirect(req.getContextPath() + "/inventory");
                return;
            }

            String error = item.validate();
            if (error != null) {
                req.setAttribute("mode", "update");
                req.setAttribute("item", item);
                req.setAttribute("error", error);
                req.getRequestDispatcher("/views/inventory-form.jsp").forward(req, resp);
                return;
            }

            dao.update(item);
            resp.sendRedirect(req.getContextPath() + "/inventory");
        } catch (Exception e) {
            throw new ServletException("Failed to update item.", e);
        }
    }

    private int parseIntSafe(String value, int fallback) {
        try { return Integer.parseInt(value); } catch (Exception e) { return fallback; }
    }
}
