Feature: petStore:
  Description: petStore: Performing Crud Operation petStore API's

  @petStoreCrud
  Scenario Outline: PetStore: Validating the PetStore API's
    Given I want to set URL as "<URL>" for test case "<TestName>"
    When I set header content type as "<ContentType>"
    When I hit the API with requestbody "<RequestBody>" and request method is "<RequestMethod>"
    Then I try to verify the status code is "<StatusCode>"

    Examples: 
      | TestName                 | URL                                | ContentType      | RequestBody                           | RequestMethod | StatusCode |
      | Get Available Pets       | /pet/findByStatus?status=available | application/json |                                       | GET           |        200 |
      | Post a New Available Pet | /pet                               | application/json | testdata/post/NewAvailablePet.json    | POST          |        200 |
      | Update Pet to Sold       | /pet                               | application/json | testdata/put/UpdateAnExistingPet.json | PUT           |        200 |
      | Get Available Pets       | /pet/9222968140491044000           | application/json |                                       | DELETE        |        200 |
