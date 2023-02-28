package services;

import java.util.Map;

import base.BaseService;
import constants.APIEndPoints;
import io.restassured.response.Response;
import pojo.request.reportFMC.AddReport;
import pojo.request.reportFMC.ChildDetails;
import pojo.request.reportFMC.IncidentDetails;
import pojo.request.reportFMC.ReporterDetails;
import utilities.DataGenerator;

public class ReportService extends BaseService {
	GenerateTokenService generateTokenService = new GenerateTokenService();
	DataGenerator dataGenerator = new DataGenerator();
	AddReport addReport = new AddReport();

	String requestId = dataGenerator.getNumber(6);
	String reportDate = dataGenerator.getDate();
	String reporterFullname = dataGenerator.getFullName();
	String reporterAge = dataGenerator.getNumber(2);
	String reporterGender = dataGenerator.getGender();
	String reporterRelation = dataGenerator.getRelationship();
	String parentingType = dataGenerator.getRelationship();
	String contactAddress_type = dataGenerator.getContactAddressType();
	String contactAddress_line_1 = dataGenerator.getContactAddressLine1();
	String contactAddress_line_2 = dataGenerator.getContactAddressLine2();
	String pinCode = dataGenerator.getPincode();
	String country = dataGenerator.getCountry();
	String primaryCountryCode = dataGenerator.getPrimaryCountryCode();
	String primaryContactNumber = dataGenerator.getPhoneNumber();
	String secondaryCountryCode = dataGenerator.getPrimaryCountryCode();
	String secondaryContactNumber = dataGenerator.getPhoneNumber();
	String communicationLanguage = dataGenerator.getLanguage();
	// String status = dataGenerator.getRandonText();

	String childFullName = dataGenerator.getFullName();
	String childAge = dataGenerator.getNumber(2);
	String childGender = dataGenerator.getGender();
	String childNickname = dataGenerator.getNickName();

	String incidentDate = dataGenerator.getDate();
	String incidentLocation = dataGenerator.getContactAddressLine1();

	public AddReport addReportRequest(int userId) {

		ReporterDetails reporterDetails = new ReporterDetails();
		ChildDetails childDetails = new ChildDetails();
		IncidentDetails incidentDetails = new IncidentDetails();

		reporterDetails.setRequest_id(requestId);
		reporterDetails.setUser_id(userId);
		reporterDetails.setReport_date(reportDate);
		reporterDetails.setReporter_fullname(reporterFullname);
		reporterDetails.setReporter_age(Integer.parseInt(reporterAge));
		reporterDetails.setReporter_gender(reporterGender);
		reporterDetails.setReporter_relation(reporterRelation);
		reporterDetails.setParenting_type(parentingType);
		reporterDetails.setContact_address_type(contactAddress_type);
		reporterDetails.setContact_address_line_1(contactAddress_line_1);
		reporterDetails.setContact_address_line_2(contactAddress_line_2);
		reporterDetails.setPincode(pinCode);
		reporterDetails.setCountry(country);
		reporterDetails.setPrimary_country_code(primaryCountryCode);
		reporterDetails.setPrimary_contact_number(primaryContactNumber);
		reporterDetails.setSecondary_country_code(secondaryCountryCode);
		reporterDetails.setSecondary_contact_number(secondaryContactNumber);
		reporterDetails.setCommunication_language(communicationLanguage);
		//String status = dataGenerator.getRandonText();
		reporterDetails.setStatus("INCOMPLETE");

		childDetails.setFullname(childFullName);
		childDetails.setAge(Integer.parseInt(childAge));
		childDetails.setGender(childGender);
		childDetails.setHeight("5ft");
		childDetails.setWeight("54kg");
		childDetails.setComplexion(dataGenerator.getRandonText());
		childDetails.setClothing(dataGenerator.getRandonText());
		childDetails.setBirth_signs(dataGenerator.getRandonText());
		childDetails.setOther_details(dataGenerator.getRandonText());
		childDetails.setImage_file_key(null);
		childDetails.setNickname(childNickname);

		incidentDetails.setIncident_date(incidentDate);
		incidentDetails.setIncident_brief(dataGenerator.getRandonText());
		incidentDetails.setLocation(incidentLocation);
		incidentDetails.setLandmark_signs(dataGenerator.getRandonText());
		incidentDetails.setNearby_police_station(dataGenerator.getRandonText());
		incidentDetails.setNearby_NGO(dataGenerator.getRandonText());
		incidentDetails.setAllow_connect_police_NGO(dataGenerator.getFlag());
		incidentDetails.setSelf_verification(dataGenerator.getFlag());
		incidentDetails.setCommunity_terms(dataGenerator.getFlag());

		addReport.setReporter_details(reporterDetails);
		addReport.setIncident_details(incidentDetails);
		addReport.setChild_details(childDetails);
		return addReport;
	}

	public Response addReportResponse(int userId, AddReport addReport) {

		Map<String, String> headerMap = getHeaderHavingAuth(generateTokenService.getToken());

		return executePostAPI(APIEndPoints.ADD_REPORT, headerMap, addReport);

	}

}
