/*
* Base controller to retrieve or update price for a given Sku.
*
* */

package com.myretail.myretailapp.controller;

import com.myretail.myretailapp.dto.ProductPriceInfo;
import com.myretail.myretailapp.service.PricingManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/myretail")
public class PricingController {

    @Autowired
    PricingManager pricingManager;

    @GetMapping(value = "/product/{sku}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductPriceInfo> getProductInfo(@PathVariable String sku,
                                                           @RequestHeader HttpHeaders headers) {
        log.debug("GET price info for {}. ",sku);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(pricingManager.getPricingInfo(sku));
    }

    @PutMapping(value = "/product/{sku}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductPriceInfo> updateProductInfo(@PathVariable String sku,
                                                              @RequestBody ProductPriceInfo priceInfo,
                                                              @RequestHeader HttpHeaders headers) {
        log.debug("PUT price info for {}. ",sku);
        pricingManager.updatePricingInfo(priceInfo);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(priceInfo);
    }
}
