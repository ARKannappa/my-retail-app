package com.myretail.myretailapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPriceInfo extends ApiResponse{

    @NotEmpty(message = "Please provide sku information")
    @JsonProperty("id")
    private String sku;

    @JsonProperty("name")
    private String productDescription;

    @JsonProperty("current_price")
    private ProductPrice productPrice;
}
