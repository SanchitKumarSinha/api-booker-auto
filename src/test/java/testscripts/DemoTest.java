package testscripts;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DemoTest {
	
	@Test
	public void phoneNumbersTypeTest() {
		RestAssured.baseURI = "https://0e686aed-6e36-4047-bcb4-a2417455c2d7.mock.pstmn.io/";
		
		Response res = RestAssured.given()
		.headers("Accept", "application/json")
		.when()
		.get("/test");
		
		System.out.println(res.asPrettyString());
		List<String> listOfType = res.jsonPath().getList("phoneNumbers.type");
		System.out.println(listOfType);
		
	}
	
	@Test
	public void phoneNumbersTest() {
		RestAssured.baseURI = "https://0e686aed-6e36-4047-bcb4-a2417455c2d7.mock.pstmn.io/";
		
		Response res = RestAssured.given()
		.headers("Accept", "application/json")
		.when()
		.get("/test");
		
		System.out.println(res.asPrettyString());
		List<Object> listOfPhoneNumber = res.jsonPath().getList("phoneNumbers");
		System.out.println(listOfPhoneNumber.size());
		System.out.println(listOfPhoneNumber);
		
		Map<String, String> mapOfPhoneNumber = (Map<String, String>)listOfPhoneNumber.get(0);
		System.out.println(mapOfPhoneNumber.get("type")+"--"+mapOfPhoneNumber.get("number"));
		
		for(Object obj : listOfPhoneNumber) {
			if(mapOfPhoneNumber.get("type").equals("iPhone"))
				Assert.assertTrue(mapOfPhoneNumber.get("number").equals("3456"));
			else if (mapOfPhoneNumber.get("type").equals("home"))
				Assert.assertTrue(mapOfPhoneNumber.get("number").equals("0123"));
			
			System.out.println(mapOfPhoneNumber.get("type")+"--"+mapOfPhoneNumber.get("number"));
		} 
		
	}

}
