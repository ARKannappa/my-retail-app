
package com.myretail.myretailapp.dto.redsky;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "is_size_chart"
})
public class DisplayOption {

    @JsonProperty("is_size_chart")
    private Boolean isSizeChart;

    @JsonProperty("is_size_chart")
    public Boolean getIsSizeChart() {
        return isSizeChart;
    }

    @JsonProperty("is_size_chart")
    public void setIsSizeChart(Boolean isSizeChart) {
        this.isSizeChart = isSizeChart;
    }

}
