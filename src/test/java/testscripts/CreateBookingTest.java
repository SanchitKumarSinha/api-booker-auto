package testscripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.request.createbooking.Bookingdates;
import pojo.request.createbooking.CreateBookingRequest;

// Given- All Inputs ()
// When- submit api (headerType, endpoint)
//Then- validate the response

public class CreateBookingTest {
	String token;
	int bookingId;
	CreateBookingRequest payload;

	@BeforeMethod
	public void generateToken() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Response res = RestAssured.given()
				.log().all()
				.headers("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.when().post("/auth")
				// .then()
				// .assertThat().statusCode(200)
				// .log().all()
				// .extract().response()
				;

		Assert.assertEquals(res.statusCode(), 200);
		token = res.jsonPath().getString("token");
		System.out.println(token);

	}

	@Test(enabled = false)
	public void createBookingTest() {
		Response res = RestAssured.given().header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.body("{\r\n" + "    \"firstname\" : \"Jim\",\r\n" + "    \"lastname\" : \"Brown\",\r\n"
						+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n" + "        \"checkin\" : \"2018-01-01\",\r\n"
						+ "        \"checkout\" : \"2019-01-01\"\r\n" + "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n" + "}")
				.when()
				.post("/booking");

		// System.out.println(res.getStatusCode());
		// System.out.println(res.getStatusLine());
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);

	}

	@Test
	public void createBookingTestWithPOJO() {

		Bookingdates bookingdates = new Bookingdates();
		bookingdates.setCheckin("2023-05-02");
		bookingdates.setCheckout("2023-05-05");

		payload = new CreateBookingRequest();
		payload.setFirstname("Sam");
		payload.setLastname("S");
		payload.setTotalprice(123);
		payload.setDepositpaid(true);
		payload.setAdditionalneeds("breakfast");
		payload.setBookingdates(bookingdates);

		Response res = RestAssured.given()
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.body(payload)
				.log().all()
				.when()
				.post("/booking");

		// System.out.println(res.getStatusCode());
		// System.out.println(res.getStatusLine());
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		// Assert.assertTrue(Integer.valueOf(res.jsonPath().getInt("bookingid"))
		// instanceof Integer);
		bookingId = res.jsonPath().getInt("bookingid");
		Assert.assertTrue(bookingId > 0);

		//System.out.println(res.jsonPath().getString("booking.firstname"));
	}

	@Test(priority = 1, enabled = false)
	public void getAllBookingTest() {
		Response res = RestAssured.given().header("Accept", "application/json").log().all().when().get("/booking");

		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		List<Integer> listOfBookingIds = res.jsonPath().getList("bookingid");
		System.out.println(listOfBookingIds.size());
		Assert.assertTrue(listOfBookingIds.contains(bookingId));
		validateResponse(res, payload, "booking.");
	}

	@Test(priority = 2, enabled = false)
	public void getBookingIdTest() {
		Response res = RestAssured.given()
				.header("Accept", "application/json")
				.log().all()
				.when()
				.get("/booking/" + bookingId);

		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		validateResponse(res, payload, "");
	}

	@Test(priority = 2, enabled = false)
	public void getBookingIdDeserializedTest() {
		Response res = RestAssured.given()
				.header("Accept", "application/json")
				.log().all()
				.when()
				.get("/booking/" + bookingId);

		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());

		// To Deserialize
		CreateBookingRequest responseBody = res.as(CreateBookingRequest.class);

		System.out.println(responseBody);

		// Assert.assertEquals(payload.firstname, responseBody.firstname);
		Assert.assertTrue(responseBody.equals(payload));
	}
	
	@Test(priority = 3)
	public void updateBookingIdTest() {
		payload.setLastname("Sam2");
		Response res = RestAssured.given()
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.header("Cookie", "token="+token)
				.pathParam("bookingId", bookingId)
				.log().all()
				.body(payload)
				.when()
				.put("/booking/{bookingId}");

		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());

		// To Deserialize
		CreateBookingRequest responseBody = res.as(CreateBookingRequest.class);

		Assert.assertTrue(responseBody.equals(payload));
	}

	@Test(enabled = false)
	public void createBookingTestInPlanMode() {

		String payload = "{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}";
		RequestSpecification reqSpec = RestAssured.given();
		reqSpec.baseUri("https://restful-booker.herokuapp.com");
		reqSpec.header("Content-Type", "application/json");
		reqSpec.body(payload);
		Response res = reqSpec.post("/auth");

		// System.out.println(res.statusCode());
		Assert.assertEquals(res.statusCode(), 200);
		// System.out.println(res.asPrettyString());
	}

	private void validateResponse(Response res, CreateBookingRequest payload, String object) {

		Assert.assertEquals(res.jsonPath().getString(object + "firstname"), payload.getFirstname());
		Assert.assertEquals(res.jsonPath().getString(object + "lastname"), payload.getLastname());
		Assert.assertEquals(res.jsonPath().getInt(object + "totalprice"), payload.getTotalprice());
		Assert.assertEquals(res.jsonPath().getBoolean(object + "depositpaid"), payload.isDepositpaid());
		Assert.assertEquals(res.jsonPath().getString(object + "bookingdates.checkin"),
				payload.getBookingdates().getCheckin());
		Assert.assertEquals(res.jsonPath().getString(object + "bookingdates.checkout"),
				payload.getBookingdates().getCheckout());
		Assert.assertEquals(res.jsonPath().getString(object + "additionalneeds"), payload.getAdditionalneeds());
	}

}
