
package com.myretail.myretailapp.dto.redsky;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "is_retail_ticketed"
})
public class Packaging {

    @JsonProperty("is_retail_ticketed")
    private Boolean isRetailTicketed;

    @JsonProperty("is_retail_ticketed")
    public Boolean getIsRetailTicketed() {
        return isRetailTicketed;
    }

    @JsonProperty("is_retail_ticketed")
    public void setIsRetailTicketed(Boolean isRetailTicketed) {
        this.isRetailTicketed = isRetailTicketed;
    }

}
