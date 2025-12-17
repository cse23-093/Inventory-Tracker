<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="pageTitle" value="Inventory"/>
<%@ include file="/views/partials/header.jspf" %>

<div class="d-flex flex-wrap align-items-center justify-content-between gap-2 mb-3">
    <div>
        <h1 class="h3 mb-1">Inventory</h1>
        <div class="muted-hint">Low stock is flagged when quantity is below 5.</div>
    </div>

    <div class="d-flex gap-2">
        <a class="btn btn-outline-secondary"
           href="<c:url value='/inventory/export'><c:param name='format' value='csv'/></c:url>">
            Export CSV
        </a>
        <a class="btn btn-outline-secondary"
           href="<c:url value='/inventory/export'><c:param name='format' value='json'/></c:url>">
            Export JSON
        </a>
        <a class="btn btn-primary" href="<c:url value='/inventory/create'/>">+ Add Item</a>
    </div>
</div>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<c:if test="${empty items}">
    <div class="alert alert-info mb-0">
        No items found. Click <strong>Add Item</strong> to create your first record.
    </div>
</c:if>

<c:if test="${not empty items}">
    <div class="card shadow-sm border-0">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover mb-0 align-middle">
                    <thead class="table-dark">
                    <tr>
                        <th style="width: 80px;">ID</th>
                        <th>Name</th>
                        <th style="width: 130px;">Category</th>
                        <th style="width: 140px;">Quantity</th>
                        <th style="width: 120px;">Status</th>
                        <th style="width: 220px;" class="text-end">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${items}">
                        <tr class="${item.lowStock ? 'table-row-low table-danger' : ''}">
                            <td class="fw-semibold">${item.id}</td>
                            <td>
                                <div class="fw-semibold">${item.name}</div>
                                <c:if test="${item.lowStock}">
                                    <div class="muted-hint">Reorder recommended</div>
                                </c:if>
                            </td>
                            <td>
                                <span class="badge text-bg-secondary">${item.category}</span>
                            </td>
                            <td>
                                <span class="fw-semibold">${item.quantity}</span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.lowStock}">
                                        <span class="badge text-bg-danger">LOW</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge text-bg-success">OK</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary"
                                   href="<c:url value='/inventory/update'><c:param name='id' value='${item.id}'/></c:url>">
                                    Edit
                                </a>

                                <form class="d-inline"
                                      action="<c:url value='/inventory/delete'/>"
                                      method="post"
                                      onsubmit="return confirm('Delete this item?');">
                                    <input type="hidden" name="id" value="${item.id}"/>
                                    <button type="submit" class="btn btn-sm btn-outline-danger">
                                        Delete
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:if>

<%@ include file="/views/partials/footer.jspf" %>
