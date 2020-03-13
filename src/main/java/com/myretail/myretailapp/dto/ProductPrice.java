package com.myretail.myretailapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
public class ProductPrice {

    @NotNull(message = "Please provide a price")
    private double value;

    @JsonProperty("currency_code")
    private String currencyCode;

}
