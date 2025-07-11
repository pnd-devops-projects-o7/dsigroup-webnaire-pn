package com.dsi_group.modu_shop_bs_microservice_product.domain.product_application.services;

import java.time.Instant;

public interface AuditableDate {
    Integer getId();
    void setCreatedAt(Instant createdAt);
    Instant getCreatedAt();
    void setUpdatedAt(Instant updatedAt);
    Instant getUpdatedAt();
}
