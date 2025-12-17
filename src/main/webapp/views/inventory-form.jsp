<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="pageTitle" value="${mode == 'update' ? 'Update Item' : 'Add Item'}"/>
<%@ include file="/views/partials/header.jspf" %>

<c:choose>
    <c:when test="${mode == 'update'}">
        <c:url var="formAction" value="/inventory/update"/>
    </c:when>
    <c:otherwise>
        <c:url var="formAction" value="/inventory/create"/>
    </c:otherwise>
</c:choose>

<div class="d-flex align-items-center justify-content-between mb-3">
    <div>
        <h1 class="h3 mb-1">
            <c:out value="${mode == 'update' ? 'Update Item' : 'Add Item'}"/>
        </h1>
        <div class="muted-hint">Enter item details. Quantity below 5 will be flagged as low stock.</div>
    </div>
    <a class="btn btn-outline-secondary" href="<c:url value='/inventory'/>">Back</a>
</div>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<div class="card shadow-sm border-0">
    <div class="card-body">
        <form action="${formAction}" method="post" class="row g-3">

            <c:if test="${mode == 'update'}">
                <input type="hidden" name="id" value="${item.id}"/>
            </c:if>

            <div class="col-12">
                <label class="form-label">Item Name</label>
                <input type="text"
                       name="name"
                       class="form-control"
                       placeholder="e.g. HDMI Cable"
                       value="${item.name}"
                       maxlength="100"
                       required/>
            </div>

            <div class="col-md-6">
                <label class="form-label">Category</label>
                <input type="text"
                       name="category"
                       class="form-control"
                       placeholder="e.g. Electronics"
                       value="${item.category}"
                       maxlength="50"
                       required/>
            </div>

            <div class="col-md-6">
                <label class="form-label">Quantity</label>
                <input type="number"
                       name="quantity"
                       class="form-control"
                       min="0"
                       value="${item.quantity}"
                       required/>
                <div class="form-text">Low stock: quantity &lt; 5</div>
            </div>

            <div class="col-12 d-flex gap-2">
                <button type="submit" class="btn btn-primary">
                    <c:out value="${mode == 'update' ? 'Save Changes' : 'Create Item'}"/>
                </button>
                <a class="btn btn-outline-secondary" href="<c:url value='/inventory'/>">Cancel</a>

                <c:if test="${mode == 'update' && item.lowStock}">
                    <span class="align-self-center badge text-bg-danger ms-auto">LOW STOCK</span>
                </c:if>
            </div>

        </form>
    </div>
</div>

<%@ include file="/views/partials/footer.jspf" %>
