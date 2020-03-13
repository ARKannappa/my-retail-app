package com.myretail.myretailapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.myretailapp.MyRetailAppApplication;
import com.myretail.myretailapp.dto.ProductPrice;
import com.myretail.myretailapp.dto.ProductPriceInfo;
import com.myretail.myretailapp.service.PricingManager;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK,classes={ MyRetailAppApplication.class })
class PricingControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PricingManager pricingManager;

    @Before
    public void setup() {

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getProductInfoTest() throws Exception {

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        ProductPrice price = new ProductPrice(10,"USD");
        ProductPriceInfo prodInfo = new ProductPriceInfo("12345","someName",price);
        when(pricingManager.getPricingInfo("12345")).thenReturn(prodInfo);
        this.mockMvc.perform(get("/myretail/product/{sku}", "12345")
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("12345"))
                .andExpect(jsonPath("$.name").value("someName"))
                .andExpect(jsonPath("$.current_price.value").value(10.0))
                .andExpect(jsonPath("$.current_price.currency_code").value("USD"));
    }

    @Test
    void updateProductInfo() throws Exception{

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        ProductPrice price = new ProductPrice(10,"USD");
        ProductPriceInfo updateProdInfo = new ProductPriceInfo();
        updateProdInfo.setSku("12345");
        updateProdInfo.setProductPrice(price);
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(put("/myretail/product/{sku}", "12345")
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content( mapper.writeValueAsString(updateProdInfo)).characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("12345"))
                .andExpect(jsonPath("$.current_price.value").value(10.0))
                .andExpect(jsonPath("$.current_price.currency_code").value("USD"));
    }


}