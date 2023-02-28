package testscripts;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.response.Response;
import pojo.request.reportFMC.AddReport;
import services.LoginService;
import services.ReportService;
import utilities.DataGenerator;

public class FMCReportAPITest {
	LoginService loginService = new LoginService();
	String emailId = DataGenerator.getEmailId();
	String password = "pass@123";
	int userId;
	ReportService reportService = new ReportService();
	AddReport addReportPayload;
	Response res;
	int reportId;
	
	
	
	@BeforeMethod
	public void login() {
		Response res = loginService.login(emailId, password);
		userId = res.jsonPath().getInt("content.userId");
	}
	
	@Test
	public void verfiyAddReportTest() {
		addReportPayload = reportService.addReportRequest(userId);
		
		res = reportService.addReportResponse(userId, addReportPayload);
		System.out.println(res.asPrettyString());
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertEquals(res.jsonPath().getString("status"), "Success");
		Assert.assertEquals(res.jsonPath().getString("message"), "Report created successfully");
		reportId = res.jsonPath().getInt("content");
		Assert.assertTrue(reportId > 0);
		System.out.println(reportId);
	}

}
