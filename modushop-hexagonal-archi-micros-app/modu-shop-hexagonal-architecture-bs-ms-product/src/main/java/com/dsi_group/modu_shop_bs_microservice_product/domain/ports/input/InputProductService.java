package com.dsi_group.modu_shop_bs_microservice_product.domain.ports.input;

import com.dsi_group.modu_shop_bs_microservice_product.domain.models.BusinessDecreaseStockRequest;
import com.dsi_group.modu_shop_bs_microservice_product.domain.models.BusinessProduct;
import com.dsi_group.modu_shop_bs_microservice_product.domain.models.BusinessProductRequest;
import com.dsi_group.modu_shop_bs_microservice_product.domain.models.BusinessSuccessResponse;
import com.dsi_group.modu_shop_bs_microservice_product.infra.adapters.input.dtos.request.ProductPatchRequest;

import java.util.Collection;

public interface InputProductService {
    BusinessProduct useCaseRegisterProduct(BusinessProductRequest businessProductRequest);
    BusinessProduct useCaseUpdateProduct(Integer id, BusinessProductRequest businessProductRequest);
    BusinessSuccessResponse useCaseDeleteProduct(Integer productId);
    BusinessProduct useCaseGetProductById(Integer productId);
    Collection<BusinessProduct> useCaseGetAllProducts();
    Collection<BusinessProduct> useCaseGetProductsByCategory(Integer categoryId);
    BusinessProduct useCaseDecreaseProductStock(Integer productId, BusinessDecreaseStockRequest decreaseStockRequest);
}
