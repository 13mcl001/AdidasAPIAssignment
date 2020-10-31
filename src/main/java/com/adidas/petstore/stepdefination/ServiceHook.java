package com.adidas.petstore.stepdefination;

import java.util.logging.Logger;

import org.testng.Assert;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.restassured.response.Response;

public class ServiceHook {
	private final static Logger logger = Logger.getLogger(StepDefinitions.class.getName());
	public static String testName;
	public static String CONTENT_TYPE;
	public static String FILE_PATH;
	public static String REQUESTBODY;
	public static String RESPONSEBODY;
	public static Response response;
	public static Scenario scenario;
	

	@Before
	public void  initializeTest(Scenario scenario) {
		this.scenario = scenario;
	}
	
	@After
	public void verifyStatusCode() {
		
		if (StepDefinitions.EXPECTED_STATUS_CODE.equals(String.valueOf(StepDefinitions.ACTUAL_STATUS_CODE))) {
			Assert.assertEquals(StepDefinitions.ACTUAL_STATUS_CODE, StepDefinitions.EXPECTED_STATUS_CODE);
			logger.info("Status Code is :: " + StepDefinitions.ACTUAL_STATUS_CODE);
		} else {
			Assert.assertEquals(StepDefinitions.ACTUAL_STATUS_CODE, StepDefinitions.EXPECTED_STATUS_CODE);
			logger.info("Status Code is not as expected :: " + StepDefinitions.ACTUAL_STATUS_CODE);
		}
	}
}
