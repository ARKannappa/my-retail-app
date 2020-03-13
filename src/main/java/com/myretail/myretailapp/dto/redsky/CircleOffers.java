
package com.myretail.myretailapp.dto.redsky;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown =  true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "universal_offer_exists",
    "non_universal_offer_exists"
})
public class CircleOffers {

    @JsonProperty("universal_offer_exists")
    private Boolean universalOfferExists;
    @JsonProperty("non_universal_offer_exists")
    private Boolean nonUniversalOfferExists;

    @JsonProperty("universal_offer_exists")
    public Boolean getUniversalOfferExists() {
        return universalOfferExists;
    }

    @JsonProperty("universal_offer_exists")
    public void setUniversalOfferExists(Boolean universalOfferExists) {
        this.universalOfferExists = universalOfferExists;
    }

    @JsonProperty("non_universal_offer_exists")
    public Boolean getNonUniversalOfferExists() {
        return nonUniversalOfferExists;
    }

    @JsonProperty("non_universal_offer_exists")
    public void setNonUniversalOfferExists(Boolean nonUniversalOfferExists) {
        this.nonUniversalOfferExists = nonUniversalOfferExists;
    }

}
