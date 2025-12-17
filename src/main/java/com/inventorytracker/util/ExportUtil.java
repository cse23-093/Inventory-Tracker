package com.inventorytracker.util;

import com.inventorytracker.model.InventoryItem;

import java.nio.charset.StandardCharsets;
import java.util.List;

public final class ExportUtil {

    private ExportUtil() {}

    private static String escapeCsv(String value) {
        if (value == null) return "";
        String v = value.replace("\"", "\"\"");
        if (v.contains(",") || v.contains("\n") || v.contains("\r") || v.contains("\"")) {
            return "\"" + v + "\"";
        }
        return v;
    }

    public static byte[] toCsvBytes(List<InventoryItem> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("id,name,quantity,category,lowStock\n");
        for (InventoryItem i : items) {
            sb.append(i.getId()).append(",");
            sb.append(escapeCsv(i.getName())).append(",");
            sb.append(i.getQuantity()).append(",");
            sb.append(escapeCsv(i.getCategory())).append(",");
            sb.append(i.isLowStock()).append("\n");
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private static String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public static byte[] toJsonBytes(List<InventoryItem> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int idx = 0; idx < items.size(); idx++) {
            InventoryItem i = items.get(idx);
            sb.append("{")
                    .append("\"id\":").append(i.getId()).append(",")
                    .append("\"name\":\"").append(escapeJson(i.getName())).append("\",")
                    .append("\"quantity\":").append(i.getQuantity()).append(",")
                    .append("\"category\":\"").append(escapeJson(i.getCategory())).append("\",")
                    .append("\"lowStock\":").append(i.isLowStock())
                    .append("}");
            if (idx < items.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
