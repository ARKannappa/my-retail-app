/*
* Base Configuration for cassandra and rest clients.
* */
package com.myretail.myretailapp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraCqlSessionFactoryBean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@Slf4j
public class AppConfig {

    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.port}")
    private int port;

    @Value("${read.timeout}")
    private int readTimeout;

    @Value("${connect.timeout}")
    private int connectTimeout;

    /*
     * Factory bean that creates the com.datastax.driver.core.Session instance
     */
    @Bean
    public CassandraCqlClusterFactoryBean cluster() {

        CassandraCqlClusterFactoryBean cluster = new CassandraCqlClusterFactoryBean();
        cluster.setContactPoints(contactPoints);
        cluster.setPort(port);
        cluster.setMetricsEnabled(false);
        log.debug("Cassandra Config : {}:{}",contactPoints,port);
        return cluster;
    }

    /*
     * Factory bean that creates the com.datastax.driver.core.Session instance
     */
    @Bean
    public CassandraCqlSessionFactoryBean session() {

        CassandraCqlSessionFactoryBean session = new CassandraCqlSessionFactoryBean();

        session.setCluster(cluster().getObject());
        session.setKeyspaceName(keyspace);
        log.debug("Cassandra keyspace : {}",keyspace);
        return session;
    }

    /*
     * Build restTemplate to make Rest calls
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        //additional configuration
        builder.setConnectTimeout(Duration.ofSeconds(connectTimeout));
        builder.setReadTimeout(Duration.ofSeconds(readTimeout));
        return builder.build();
    }
}

