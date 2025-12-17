package com.inventorytracker.controller;

import com.inventorytracker.dao.InventoryItemDAO;
import com.inventorytracker.dao.InventoryItemDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/inventory")
public class InventoryListServlet extends HttpServlet {

    private final InventoryItemDAO dao = new InventoryItemDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            req.setAttribute("items", dao.findAll());
            req.getRequestDispatcher("/views/inventory-list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Failed to load inventory.", e);
        }
    }
}
