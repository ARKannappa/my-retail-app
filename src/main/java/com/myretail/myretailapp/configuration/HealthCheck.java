package com.myretail.myretailapp.configuration;

import com.myretail.myretailapp.model.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck implements HealthIndicator {

    @Autowired
    PricingService pricingService;

    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down()
                    .withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    public int check() {
        try{
            pricingService.getPrice("13860425");
        } catch (Exception e){
            return 500;
        }

        return 0;
    }
}
