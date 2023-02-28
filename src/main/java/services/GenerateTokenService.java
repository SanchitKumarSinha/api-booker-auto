package services;

import java.util.Map;

import org.json.simple.JSONObject;

import base.BaseService;
import constants.APIEndPoints;
import io.restassured.response.Response;
import utilities.DataGenerator;

public class GenerateTokenService extends BaseService{
	
	String emailId = DataGenerator.getEmailId();
	
	public Response getTokenResponse() {
		Map<String, String> headerMap= getHeaderWithoutAuth();
		//Response res = executeGetAPI(APIEndPoints.TOKEN, headerMap);
		//return res;
		return executeGetAPI(APIEndPoints.TOKEN, headerMap);
	}
	
	public String getToken() {
		Response res = getTokenResponse();
		return res.jsonPath().get("accessToken");
	}
	
	public Response getEmailSignupResponse(JSONObject emailSignUpPayLoad) {
		Map<String, String> headerMap= getHeaderHavingAuth(getToken());
		return executePostAPI(APIEndPoints.EMAIL_SINGUP, headerMap, emailSignUpPayLoad);
	}
	
	public Response getEmailSignupResponse() {
		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", emailId);
		
		Map<String, String> headerMap= getHeaderHavingAuth(getToken());
		return executePostAPI(APIEndPoints.EMAIL_SINGUP, headerMap, emailSignUpPayLoad);
	}
	
	public String getOtpFromEmailSignUpResponse(JSONObject emailSignUpPayLoad) {
		Response res = getEmailSignupResponse(emailSignUpPayLoad);
		//String otp = res.jsonPath().getString("content.otp");
		//return otp;
		return res.jsonPath().getString("content.otp");
	}
	
	public Response getVerifyOtpResponse(JSONObject verifyOtpPayload) {
		Map<String, String> headerMap= getHeaderHavingAuth(getToken());
		return executePutAPI(APIEndPoints.VERIFY_OTP, headerMap, verifyOtpPayload);
	}
	
	public int getUserIdFromVerifyOtpResponse(JSONObject verifyOtpPayload) {
		Map<String, String> headerMap= getHeaderHavingAuth(getToken());
		Response res = executePutAPI(APIEndPoints.VERIFY_OTP, headerMap, verifyOtpPayload);
		return res.jsonPath().getInt("content.userId");
	}
	
	@SuppressWarnings("unchecked")
	public int getUserId(String emailId, String password) {
		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", emailId);
		String otp = getOtpFromEmailSignUpResponse(emailSignUpPayLoad);
		
		String fullName = DataGenerator.getFullName();
		String phoneNumber = DataGenerator.getPhoneNumber();
		
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id", emailId);
		verifyOtpPayload.put("full_name", fullName);
		verifyOtpPayload.put("phone_number", phoneNumber);
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);
		
		return getUserIdFromVerifyOtpResponse(verifyOtpPayload);
	}

}
