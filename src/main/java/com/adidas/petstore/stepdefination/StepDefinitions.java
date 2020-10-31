package com.adidas.petstore.stepdefination;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Logger;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.adidas.petstore.commonutils.ConfigFileReader;
import com.adidas.petstore.constants.Constant;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefinitions {
	private final static Logger logger = Logger.getLogger(StepDefinitions.class.getName());
	public static String API_URL;
	public static String TEST_NAME;
	public static String CONTENT_TYPE;
	public static String ACTUAL_STATUS_CODE;
	public static String FILE_PATH;
	public static String REQUESTBODY;
	public static String RESPONSEBODY;
	public static Response RESPONSE;
	public static String EXPECTED_STATUS_CODE;
	ConfigFileReader con = new ConfigFileReader();
	
	@Given("^I want to set URL as \"([^\"]*)\" for test case \"([^\"]*)\"$")
	public void setAPIEndpointURL(String URL, String testCaseName) {
		API_URL = String.format("%s%s", con.getAPIHostName(), URL);
		TEST_NAME = testCaseName;
		logger.info("Cucumber Hostname URL is :: " + API_URL);
		logger.info("Cucumber Test case name is :: " + TEST_NAME);
	}

	@When("^I set header content type as \"([^\"]*)\"$")
	public void setHeader(String contentType) {
		if (contentType != null && !contentType.isEmpty()) {
			CONTENT_TYPE = contentType;
			logger.info("Content type is :: " + CONTENT_TYPE);
		} else {
			logger.info("Content type cannot be null or empty!");
		}
	}

	@And("^I hit the API with requestbody \"([^\"]*)\" and request method is \"([^\"]*)\"$")
	public void submitRequest(String requestBodyPath, String requestType) throws Throwable {
		RestAssured.baseURI = API_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", CONTENT_TYPE).header("Authorization", "token " + Constant.FINAL_TOKEN_VALUE);
		;
		if (requestBodyPath != null && !requestBodyPath.isEmpty() && requestType.equalsIgnoreCase("POST")
				|| requestType.equalsIgnoreCase("PUT")) {
			JSONParser jsonParser = new JSONParser();
			FILE_PATH = System.getProperty("user.dir") + "//src//test//resources//" + requestBodyPath;
			logger.info("Path of requestbody file is :: " + FILE_PATH);
			try (FileReader reader = new FileReader(FILE_PATH)) {
				Object obj = jsonParser.parse(reader);
				REQUESTBODY = obj.toString();
				logger.info("Request Body is :: " + REQUESTBODY);
			} catch (FileNotFoundException | ParseException exc) {
				exc.printStackTrace();
			}
			if (REQUESTBODY.length() > 0) {
				request.body(REQUESTBODY);
				RESPONSE = request.post();
			} else {
				logger.info(" Request Body cannot be null or empty!");
			}
		} else if (requestType.equalsIgnoreCase("GET")) {
			RESPONSE = request.get();
		}
		ACTUAL_STATUS_CODE = String.valueOf(RESPONSE.getStatusCode());
		RESPONSEBODY = RESPONSE.getBody().asString();
		logger.info("Actual Response is" + RESPONSEBODY);
	}

	@Then("^I try to verify the status code is \"([^\"]*)\"$")
	public void verifyStatusCode(String statusCode) {
		EXPECTED_STATUS_CODE = statusCode;
		if (statusCode.equals(String.valueOf(ACTUAL_STATUS_CODE))) {
			Assert.assertEquals(ACTUAL_STATUS_CODE, EXPECTED_STATUS_CODE);
			logger.info("Status Code is :: " + ACTUAL_STATUS_CODE);
		} else {
			Assert.assertEquals(ACTUAL_STATUS_CODE, EXPECTED_STATUS_CODE);
			logger.info("Status Code is not as expected :: " + ACTUAL_STATUS_CODE);
		}
	}

}
