package com.myretail.myretailapp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.myretailapp.dto.ProductPrice;
import com.myretail.myretailapp.dto.ProductPriceInfo;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = MyRetailAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class MyRetailAppApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testUnauthorized() throws JSONException, JsonProcessingException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/myretail/product/13860429"),
				HttpMethod.GET, entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = mapper.readValue(response.getBody(), Map.class);

		assertEquals(401,  map.get("status"));
		assertEquals("Unauthorized",  map.get("error"));
	}

	@Test
	public void testGetForValidSku() throws JSONException, JsonProcessingException {
		headers.add("Authorization","Basic dXNlcjpwYXNzd29yZA==");

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/myretail/product/13860429"),
				HttpMethod.GET, entity, String.class);

		String expectedResponse = "{\n" +
				"    \"status\": \"200\",\n" +
				"    \"id\": \"13860429\",\n" +
				"    \"name\": \"SpongeBob SquarePants: SpongeBob's Frozen Face-off\",\n" +
				"    \"current_price\": {\n" +
				"        \"value\": 25.0,\n" +
				"        \"currency_code\": \"USD\"\n" +
				"    }\n" +
				"}";
		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
	}

	@Test
	public void testGetForInValidSku() throws JSONException, JsonProcessingException {
		headers.add("Authorization","Basic dXNlcjpwYXNzd29yZA==");

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/myretail/product/somesku"),
				HttpMethod.GET, entity, String.class);

		String expectedResponse = "{\n" +
				"    \"status\": \"404\",\n" +
				"    \"errors\": [\n" +
				"        {\n" +
				"            \"errorCode\": \"PRD-NOT-AVA\",\n" +
				"            \"errorDescription\": \"Data error. Couldn't fulfill this request.com.myretail.myretailapp.exception.DataNotAvailableException: Product Info for somesku not available in system. \"\n" +
				"        }\n" +
				"    ]\n" +
				"}";
		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
	}

	@Test
	public void testUpdateForValidSku() throws JSONException, JsonProcessingException {
		headers.add("Authorization","Basic YWRtaW46cGFzc3dvcmQ=");
		ProductPrice price = new ProductPrice(20,"USD");
		ProductPriceInfo product = new ProductPriceInfo();
		product.setSku("13860428");
		product.setProductPrice(price);

		HttpEntity<ProductPriceInfo> entity = new HttpEntity<ProductPriceInfo>(product, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/myretail/product/13860428"),
				HttpMethod.PUT, entity, String.class);

		String expectedResponse = "{\n" +
				"    \"status\": \"200\",\n" +
				"    \"id\": \"13860428\",\n" +
				"    \"current_price\": {\n" +
				"        \"value\": 20.0,\n" +
				"        \"currency_code\": \"USD\"\n" +
				"    }\n" +
				"}";
		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);

		HttpEntity<String> entity2 = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response2 = restTemplate.exchange(
				createURLWithPort("/myretail/product/13860428"),
				HttpMethod.GET, entity, String.class);

		String expectedResponse2 = "{\n" +
				"    \"status\": \"200\",\n" +
				"    \"id\": \"13860428\",\n" +
				"    \"name\": \"The Big Lebowski (Blu-ray)\",\n" +
				"    \"current_price\": {\n" +
				"        \"value\": 20.0,\n" +
				"        \"currency_code\": \"USD\"\n" +
				"    }\n" +
				"}";
		JSONAssert.assertEquals(expectedResponse2, response2.getBody(), false);
	}

	@Test
	public void testUpdateForInValidSku() throws JSONException, JsonProcessingException {
		headers.add("Authorization","Basic YWRtaW46cGFzc3dvcmQ=");
		ProductPrice price = new ProductPrice(20,"USD");
		ProductPriceInfo product = new ProductPriceInfo();
		product.setSku("someSku");
		product.setProductPrice(price);

		HttpEntity<ProductPriceInfo> entity = new HttpEntity<ProductPriceInfo>(product, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/myretail/product/13860428"),
				HttpMethod.PUT, entity, String.class);

		String expectedResponse = "{\n" +
				"    \"status\": \"404\",\n" +
				"    \"errors\": [\n" +
				"        {\n" +
				"            \"errorCode\": \"PRD-NOT-AVA\",\n" +
				"            \"errorDescription\": \"Data error. Couldn't fulfill this request.com.myretail.myretailapp.exception.DataNotAvailableException: Product Info for someSku not available in system. \"\n" +
				"        }\n" +
				"    ]\n" +
				"}";
		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
	}
	@Test
	public void testUpdateForForbidden() throws JSONException, JsonProcessingException {
		headers.add("Authorization","Basic dXNlcjpwYXNzd29yZA==");
		ProductPrice price = new ProductPrice(20,"USD");
		ProductPriceInfo product = new ProductPriceInfo();
		product.setSku("13860428");
		product.setProductPrice(price);

		HttpEntity<ProductPriceInfo> entity = new HttpEntity<ProductPriceInfo>(product, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/myretail/product/13860428"),
				HttpMethod.PUT, entity, String.class);

		String expectedResponse = "{\n" +
				"    \"status\": 403,\n" +
				"    \"error\": \"Forbidden\",\n" +
				"    \"message\": \"Forbidden\",\n" +
				"    \"path\": \"/myretail/product/13860428\"\n" +
				"}";
		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
	}




	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
