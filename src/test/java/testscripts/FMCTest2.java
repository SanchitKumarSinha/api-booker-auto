package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService;
import constants.Status_Code;
import io.restassured.response.Response;
import utilities.DataGenerator;
import java.util.Map;

public class FMCTest2 {

	//String emailId;
	String token;
	Response res;
	BaseService baseService = new BaseService();
	String emailId = DataGenerator.getEmailId();
	String fullName = DataGenerator.getFullName();
	String phoneNumber = DataGenerator.getPhoneNumber();
	String password = "pass@123";
	String otp;
	
	@Test
	private void createToken() {		
		Map<String, String> headerMap= baseService.getHeaderWithoutAuth();
		res = baseService.executeGetAPI("/fmc/token", headerMap);
		token = res.jsonPath().getString("accessToken");
		System.out.println(token);
		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	private void emailSingup() {
		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", emailId);
		
		Map<String, String> headerMap= baseService.getHeaderHavingAuth(token);
		res = baseService.executePostAPI("/fmc/email-signup-automation", headerMap, emailSignUpPayLoad);
		
		otp = res.jsonPath().getString("content.otp");
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	private void verifyOtp() {		
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

}
