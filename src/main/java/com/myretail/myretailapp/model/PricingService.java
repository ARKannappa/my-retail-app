/*
* Service to perform interactions with backend data store
* */

package com.myretail.myretailapp.model;

import com.datastax.driver.core.Session;
import com.myretail.myretailapp.dto.ProductPrice;
import com.myretail.myretailapp.exception.DataNotAvailableException;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PricingService {

    @Autowired
    private Session session;

    /*
    * Get price based on sku
    * */
    public ProductPrice getPrice(String sku){
        CassandraOperations template = new CassandraTemplate(session);
        Price price = template.selectOne(Query.query(Criteria.where("sku").is(sku)), Price.class);
        if(price != null){
            return new ProductPrice(price.getPrice(),price.getCurrency());
        } else {
            log.error("{} not availble in price table",sku);
            throw new DataNotAvailableException("PRC-NOT-AVA","Price for "+sku+ " not available in system. ");

        }

    }

    /*
    * Upsert price information based on sku, If currency not provided default to USD
    * */
    public Price updatePrice(String sku, double price, String currency) {
        CassandraOperations template = new CassandraTemplate(session);
        log.debug("updateing {} in price table.",sku);
        return template.update(new Price(sku,price, StringUtils.isBlank(currency)?"USD":currency));
    }
}
