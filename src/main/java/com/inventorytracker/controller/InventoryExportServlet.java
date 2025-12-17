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

@WebServlet("/inventory/export")
public class InventoryExportServlet extends HttpServlet {

    private final InventoryItemDAO dao = new InventoryItemDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String format = req.getParameter("format");
            if (format == null) format = "csv";

            List<InventoryItem> items = dao.findAll();

            if ("json".equalsIgnoreCase(format)) {
                byte[] bytes = ExportUtil.toJsonBytes(items);
                resp.setContentType("application/json");
                resp.setHeader("Content-Disposition", "attachment; filename=\"inventory.json\"");
                resp.getOutputStream().write(bytes);
            } else {
                byte[] bytes = ExportUtil.toCsvBytes(items);
                resp.setContentType("text/csv");
                resp.setHeader("Content-Disposition", "attachment; filename=\"inventory.csv\"");
                resp.getOutputStream().write(bytes);
            }
        } catch (Exception e) {
            throw new ServletException("Failed to export inventory.", e);
        }
    }
}
