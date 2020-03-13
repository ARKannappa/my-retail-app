/*
* Service class that handles business logic related to Pricing information for a Product
* */
package com.myretail.myretailapp.service;

import com.myretail.myretailapp.dto.ProductPriceInfo;
import com.myretail.myretailapp.dto.redsky.Product;
import com.myretail.myretailapp.exception.DataNotAvailableException;
import com.myretail.myretailapp.exception.ExternalServiceServerException;
import com.myretail.myretailapp.model.PricingService;
import com.myretail.myretailapp.rest.ProductServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PricingManager {

    @Autowired
    private PricingService pricingService;

    @Autowired
    private ProductServiceClient productService;

    public ProductPriceInfo getPricingInfo(String sku) {
        try {
            // Check product sku is a valid item with Redsky
            Product product = productService.getProduct(sku);
            if (product != null) {
                ProductPriceInfo response = new ProductPriceInfo();
                response.setProductDescription(product.getItem().getProductDescription().getTitle());
                response.setSku(product.getItem().getTcin());
                // get price from cassandra
                response.setProductPrice(pricingService.getPrice(sku));
                return response;
            } else {
                // Data not available with Redsky.Throw back invalid data to client
                log.error("Update product information from Redsky failed for {}",sku);
                throw new DataNotAvailableException("PRD-NOT-AVA", "Product Info for " + sku + " not available in system. ");
            }
        } catch (DataAccessException dae) {
            // Not able to access data in cassandra.
            log.error("Retrieve price information failed for {} with {}.",sku,dae.getLocalizedMessage());
            dae.printStackTrace();
            throw new ExternalServiceServerException("PRD-CAS-AVA", "Data store not available to retrieve price information");
        }
    }


    public void updatePricingInfo(ProductPriceInfo priceInfo) {
        try {
            // Check product sku is a valid item with Redsky
            Product product = productService.getProduct(priceInfo.getSku());
            if (product != null) {
                priceInfo.setProductDescription(product.getItem().getProductDescription().getTitle());
                // update price in cassandra.
                if (pricingService.updatePrice(priceInfo.getSku(), priceInfo.getProductPrice().getValue(), priceInfo.getProductPrice().getCurrencyCode()) == null) {
                    log.error("Update price information failed in cassandra for {}",priceInfo.getSku());
                    throw new DataNotAvailableException("PRC-UPD-FAI", "Price Info for " + priceInfo.getSku() + " not available in system to update ");
                }
            } else {
                log.error("Update price information failed for {}, since data not available in Redsky.",priceInfo.getSku());
                throw new DataNotAvailableException("PRD-NOT-AVA", "Product Info for " + priceInfo.getSku() + " not available in system. ");
            }
        } catch (DataAccessException dae) {
            log.error("Update price information failed for {} with {}.",priceInfo.getSku(),dae.getLocalizedMessage());
            dae.printStackTrace();
            throw new ExternalServiceServerException("PRD-CAS-AVA", "Data store not available to update");
        }
    }
}
