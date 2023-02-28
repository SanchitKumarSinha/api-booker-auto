package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService;
import constants.Status_Code;
import io.restassured.response.Response;
import utilities.DataGenerator;
import java.util.Map;

public class FMCTest {

	String emailId;
	String token;
	Response res;
	BaseService baseService = new BaseService();
	
	/*
	 * private void createToken1() { RestAssured.baseURI =
	 * "http://Fmc-env.eba-5akrwvvr.us-east-1.elasticbeanstalk.com";
	 * 
	 * res = RestAssured.given() .header("Accept", "application/json") .when()
	 * .get("/fmc/token");
	 * 
	 * System.out.println(res.asPrettyString());
	 * 
	 * token = res.jsonPath().getString("accessToken"); System.out.println(token); }
	 */
	
	private void createToken() {		
		Map<String, String> headerMap= baseService.getHeaderWithoutAuth();
		res = baseService.executeGetAPI("/fmc/token", headerMap);
		token = res.jsonPath().getString("accessToken");
		System.out.println(token);
		
	}
	
	/*
	 * private String emailSingup1(String emailId) { JSONObject emailSignUpPayLoad =
	 * new JSONObject(); emailSignUpPayLoad.put("email_id", emailId);
	 * 
	 * res = given() .header("Content-Type", "application/json")
	 * .header("Authorization", "Bearer " + token) .body(emailSignUpPayLoad) .when()
	 * .post("/fmc/email-signup-automation");
	 * 
	 * Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED); return
	 * res.jsonPath().getString("content.otp"); }
	 */
	
	@SuppressWarnings("unchecked")
	private String emailSingup(String emailId) {
		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", emailId);
		
		Map<String, String> headerMap= baseService.getHeaderHavingAuth(token);
		res = baseService.executePostAPI("/fmc/email-signup-automation", headerMap, emailSignUpPayLoad);
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
		return res.jsonPath().getString("content.otp");
	}
	
	/*
	 * private void verifyOtp1(String emailId, String fullName, String phoneNumber,
	 * String password, String otp) { JSONObject verifyOtpPayload = new
	 * JSONObject(); verifyOtpPayload.put("email_id", emailId);
	 * verifyOtpPayload.put("full_name", fullName);
	 * verifyOtpPayload.put("phone_number", phoneNumber);
	 * verifyOtpPayload.put("password", password); verifyOtpPayload.put("otp", otp);
	 * 
	 * res = given() .header("Content-Type", "application/json")
	 * .header("Authorization", "Bearer " + token) .body(verifyOtpPayload) .when()
	 * .put("/fmc/verify-otp/");
	 * 
	 * Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
	 * System.out.println(res.asPrettyString());
	 * 
	 * }
	 */
	
	@SuppressWarnings("unchecked")
	private void verifyOtp(String emailId, String fullName, String phoneNumber, String password, String otp) {
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id", emailId);
		verifyOtpPayload.put("full_name", fullName);
		verifyOtpPayload.put("phone_number", phoneNumber);
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);
		
		Map<String, String> headerMap= baseService.getHeaderHavingAuth(token);
		res = baseService.executePutAPI("/fmc/verify-otp/", headerMap, verifyOtpPayload);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		
	}

	@Test
	public void signupTest() {
		String emailId = DataGenerator.getEmailId();
		String fullName = DataGenerator.getFullName();
		String phoneNumber = DataGenerator.getPhoneNumber();
		String password = "pass@123";
		
		createToken();
		String otp = emailSingup(emailId);
		System.out.println(otp);
		verifyOtp(emailId, fullName, phoneNumber, password, otp);
	}

}
