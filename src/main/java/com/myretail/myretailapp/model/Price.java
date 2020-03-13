package com.myretail.myretailapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@AllArgsConstructor
@Getter
@Setter
@Table
public class Price {
    @PrimaryKey
    private final String sku;
    private final double price;
    private final String currency;
}
