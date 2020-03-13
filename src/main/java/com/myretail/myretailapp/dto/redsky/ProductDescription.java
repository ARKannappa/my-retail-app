
package com.myretail.myretailapp.dto.redsky;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "title",
    "downstream_description",
    "bullet_description"
})
public class ProductDescription {

    @JsonProperty("title")
    private String title;
    @JsonProperty("downstream_description")
    private String downstreamDescription;
    @JsonProperty("bullet_description")
    private List<String> bulletDescription = null;

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("downstream_description")
    public String getDownstreamDescription() {
        return downstreamDescription;
    }

    @JsonProperty("downstream_description")
    public void setDownstreamDescription(String downstreamDescription) {
        this.downstreamDescription = downstreamDescription;
    }

    @JsonProperty("bullet_description")
    public List<String> getBulletDescription() {
        return bulletDescription;
    }

    @JsonProperty("bullet_description")
    public void setBulletDescription(List<String> bulletDescription) {
        this.bulletDescription = bulletDescription;
    }

}
