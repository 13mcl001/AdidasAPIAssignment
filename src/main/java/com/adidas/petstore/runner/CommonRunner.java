package com.adidas.petstore.runner;

import com.adidas.petstore.commonutils.INVCommonTestBase;

import cucumber.api.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/featureFiles"}, glue = {
				"com.adidas.petstore.stepdefination/" }, monochrome = true, strict = true, dryRun = false, tags = {
						"@petStoreCrud" }, plugin = { "pretty", "html:target/cucumber-reports/cucumber-pretty",
								"json:target/cucumber-reports/CucumberTestReport.json",
								"rerun:target/cucumber-reports/rerun.txt" })

public class CommonRunner extends INVCommonTestBase{

}
