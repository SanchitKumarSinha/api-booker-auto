package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService;
import constants.Status_Code;
import io.restassured.response.Response;
import services.GenerateTokenService;
import utilities.DataGenerator;
import java.util.Map;

public class FMCTest3 {

	// String emailId;
	String token;
	Response res;
	BaseService baseService = new BaseService();
	String emailId = DataGenerator.getEmailId();
	String fullName = DataGenerator.getFullName();
	String phoneNumber = DataGenerator.getPhoneNumber();
	String password = "pass@123";
	String otp;
	GenerateTokenService generateTokenService = new GenerateTokenService();

	@Test
	private void createToken() {

		Response res = generateTokenService.getTokenResponse();
		System.out.println(res.asPrettyString());
		token = res.jsonPath().getString("accessToken");
		System.out.println(token);
		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		Assert.assertTrue(token.length() > 0);

	}

	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	private void emailSingup() {
		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", emailId);

		res = generateTokenService.getEmailSignupResponse(emailSignUpPayLoad);
		otp = res.jsonPath().getString("content.otp");
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
	}

	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	private void verifyOtp() {
		if (otp == null) {
			JSONObject emailSignUpPayLoad = new JSONObject();
			emailSignUpPayLoad.put("email_id", emailId);
			otp = generateTokenService.getOtpFromEmailSignUpResponse(emailSignUpPayLoad);
		}

		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id", emailId);
		verifyOtpPayload.put("full_name", fullName);
		verifyOtpPayload.put("phone_number", phoneNumber);
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);

		res = generateTokenService.getVerifyOtpResponse(verifyOtpPayload);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		int userId = res.jsonPath().getInt("content.userId");
		System.out.println(res.asPrettyString());

		userId = generateTokenService.getUserId(emailId, "pass@123");
		System.out.println(userId);

	}
	
	@Test
	public void verifyOtp1() {
		int userId = generateTokenService.getUserId(emailId, "pass123");
		System.out.println(userId);
	}

}
