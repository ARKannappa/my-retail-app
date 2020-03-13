/*
* Component to call redsky api to get product information based on sku
* */
package com.myretail.myretailapp.rest;

import com.myretail.myretailapp.dto.redsky.Product;
import com.myretail.myretailapp.dto.redsky.RedskyResponse;
import com.myretail.myretailapp.exception.ExternalServiceClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ProductServiceClient {

    @Value("${product.baseUrl}")
    String productBaseUrl;

    @Value("${product.info.path}")
    String productInfoPath;

    @Value("${product.exclusions}")
    String productExclusions;

    @Value("${retry.recursive.redirects:true}")
    boolean retryRedirectsEnabled;

    @Autowired
    RestTemplate restTemplate;

    @Cacheable("productBySku")
    public Product getProduct(String sku)
    {
        try{
            ResponseEntity<RedskyResponse> response = restTemplate.getForEntity(productBaseUrl+productInfoPath+productExclusions, RedskyResponse.class,sku);
            if(retryRedirectsEnabled){
                while(response != null && response.getStatusCode() == HttpStatus.MOVED_PERMANENTLY && response.getHeaders().getLocation() != null){
                    log.warn("Redsky thrown 301 with redirect url {} ",response.getHeaders().getLocation());
                    response = restTemplate.getForEntity(response.getHeaders().getLocation(), RedskyResponse.class);
                }
            }
            if(response != null && response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
                return response.getBody().getProduct();
            } else if(response != null && response.getStatusCode() != null) {
                log.error("Redsky thrown status code other than 200 - {} ",response.getStatusCode() );
                throw new ExternalServiceClientException("EXT-SRV-CLT",response.getStatusCode() + " thrown calling " + productBaseUrl);
            }
            return  null;
        } catch (HttpClientErrorException he){
            // Handle exceptios thrown from external system
            if(he.getStatusCode() == HttpStatus.NOT_FOUND){
                return null;
            }
            log.error("Redsky thrown client exception {} ",he.getLocalizedMessage() );
            he.printStackTrace();
            throw new ExternalServiceClientException("EXT-SRV-CLT",he.getStatusCode().getReasonPhrase() + " thrown calling " + productBaseUrl);
        } catch (HttpServerErrorException he){
            // Handle exceptios thrown from external system
            log.error("Redsky thrown server exception {} ",he.getLocalizedMessage() );
            he.printStackTrace();
            throw new ExternalServiceClientException("EXT-SRV-SER",he.getStatusCode().getReasonPhrase() + " thrown calling " + productBaseUrl);
        }
    }


}
