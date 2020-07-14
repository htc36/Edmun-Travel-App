package com.springvuegradle.team6.steps;

import com.springvuegradle.team6.models.Activity;
import com.springvuegradle.team6.models.ActivityRepository;
import com.springvuegradle.team6.models.Tag;
import com.springvuegradle.team6.models.TagRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource(properties = {"ADMIN_EMAIL=test@test.com", "ADMIN_PASSWORD=test"})
@DirtiesContext
public class ActivityHashtagFeatureSteps {
  private String jsonString;
  private MockHttpSession session;
  private ResultActions mvcResponse;
  private String profileId;
  private String activityId;
  private List<String> autocompleteResult;

  @Autowired private ActivityRepository activityRepository;

  @Autowired private MockMvc mvc;

  @Given("I registered a user with email {string} and password {string}")
  public void i_registered_a_user_with_email_and_password(String email, String password)
      throws Exception {
    jsonString =
        "{\r\n  \"lastname\": \"Test\",\r\n  \"firstname\": \"Cucumber\",\r\n  \"middlename\": \"Z\",\r\n  \"nickname\": \"BigTest\",\r\n  \"primary_email\": \""
            + email
            + "\",\r\n  \"password\": \""
            + password
            + "\",\r\n  \"bio\": \"Test is plain\",\r\n  \"date_of_birth\": \"2000-11-11\",\r\n  \"gender\": \"nonbinary\"\r\n}";
    String createProfileUrl = "/profiles";
    profileId =
        mvc.perform(
                MockMvcRequestBuilders.post(createProfileUrl)
                    .content(jsonString)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
  }

  @Given("I log in as a user with email {string} and password {string}")
  public void i_log_in_as_a_user_with_email_and_password(String email, String password)
      throws Exception {
    session = new MockHttpSession();
    jsonString =
        "{\n"
            + "\t\"email\": \""
            + email
            + "\",\n"
            + "\t\"password\": \""
            + password
            + "\"\n"
            + "}";
    String loginUrl = "/login";
    mvcResponse =
        mvc.perform(
            MockMvcRequestBuilders.post(loginUrl)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .session(session));
  }

  @Given("there are no activities in the database")
  public void there_are_no_activities_in_the_database() {
    List<Activity> activities = activityRepository.findAll();
    Assert.assertTrue(activities.size() == 0);
  }

  @Given("I create an activity {string} with no hashtags")
  public void i_create_an_activity_with_no_hashtags(String activityName) throws Exception {
    jsonString =
        "{\n"
            + "  \"activity_name\": \""
            + activityName
            + "\",\n"
            + "  \"description\": \"tramping iz fun\",\n"
            + "  \"activity_type\":[ \n"
            + "    \"Hike\",\n"
            + "    \"Bike\"\n"
            + "  ],\n"
            + "  \"continuous\": true"
            + "}";
    String createActivityUrl = "/profiles/" + profileId + "/activities";
    mvcResponse =
        mvc.perform(
            MockMvcRequestBuilders.post(createActivityUrl)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .session(session));
    activityId = mvcResponse.andReturn().getResponse().getContentAsString();
  }

  @When("I edit an activity {string} and add hashtags")
  public void i_edit_an_activity_and_add_hashtags(
      String activityName, io.cucumber.datatable.DataTable dataTable) throws Exception {
    List<String> hashTags = new ArrayList<>();
    for (Map<String, String> hashTagMapping : dataTable.asMaps()) {
      String hashTag = '"' + hashTagMapping.get("Hashtag") + '"';
      hashTags.add(hashTag);
    }
    jsonString =
        "{\n"
            + "  \"activity_name\": \""
            + activityName
            + "\",\n"
            + "  \"description\": \"tramping iz fun\",\n"
            + "  \"activity_type\":[ \n"
            + "    \"Hike\",\n"
            + "    \"Bike\"\n"
            + "  ],\n"
            + "  \"continuous\": true,\n"
            + "  \"hashtags\": "
            + hashTags
            + "\n"
            + "}";
    String editActivityUrl = "/profiles/" + profileId + "/activities/" + activityId;
    mvcResponse =
        mvc.perform(
            MockMvcRequestBuilders.put(editActivityUrl)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .session(session));
  }

  @When("I create an activity {string} with hashtags")
  public void i_create_an_activity_with_hashtags(
      String activityName, io.cucumber.datatable.DataTable dataTable) throws Exception {
    List<String> hashTags = new ArrayList<>();
    for (Map<String, String> hashTagMapping : dataTable.asMaps()) {
      String hashTag = '"' + hashTagMapping.get("Hashtag") + '"';
      hashTags.add(hashTag);
    }
    jsonString =
        "{\n"
            + "  \"activity_name\": \""
            + activityName
            + "\",\n"
            + "  \"description\": \"tramping iz fun\",\n"
            + "  \"activity_type\":[ \n"
            + "    \"Hike\",\n"
            + "    \"Bike\"\n"
            + "  ],\n"
            + "  \"continuous\": true,\n"
            + "  \"hashtags\": "
            + hashTags
            + "\n"
            + "}";
    String createActivityUrl = "/profiles/" + profileId + "/activities";
    mvcResponse =
        mvc.perform(
            MockMvcRequestBuilders.post(createActivityUrl)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .session(session));
    activityId = mvcResponse.andReturn().getResponse().getContentAsString();
  }

  @Then("I have an activity {string} with hashtags")
  public void i_have_an_activity_with_hashtags(
      String activityName, io.cucumber.datatable.DataTable dataTable) throws Exception {
    Assert.assertTrue(activityRepository.findByActivityName(activityName) != null);

    String getActivityUrl = "/activities/" + activityId;
    String responseString =
        mvc.perform(
                MockMvcRequestBuilders.get(getActivityUrl)
                    .content(jsonString)
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(session))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    for (Map<String, String> hashTagMapping : dataTable.asMaps()) {
      String hashTag = hashTagMapping.get("Hashtag").toLowerCase().substring(1);
      Assert.assertTrue(responseString.contains(hashTag));
    }
  }

  @Then("I do not have an activity {string} and receive {string} status code")
  public void i_do_not_have_an_activity_and_receive_status_code(
      String activityName, String statusCode) throws Exception {
    Assert.assertTrue(activityRepository.findByActivityName(activityName) == null);
    mvcResponse.andExpect(status().is(Integer.parseInt(statusCode)));
  }

  @When("I search for hashtag {string}")
  public void i_search_for_hashtag(String string) throws Exception {
    String response =
        mvc.perform(
                MockMvcRequestBuilders.get("/hashtag/autocomplete?hashtag=" + string, profileId)
                    .session(session))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    System.out.println(response);
    JSONObject obj = new JSONObject(response);
    JSONArray arr = obj.getJSONArray("results");
    List<String> list = new ArrayList<String>();
    for (int i = 0; i < arr.length(); i++) {
      list.add(arr.getString(i));
    }
    System.out.println(list);
    autocompleteResult = list;
  }

  @Then("I get hashtag search results containing")
  public void i_get_hashtag_search_results_containing(io.cucumber.datatable.DataTable dataTable) {
    List<String> hashTags = new ArrayList<>();
    for (Map<String, String> hashTagMapping : dataTable.asMaps()) {
      String hashTag = hashTagMapping.get("Hashtag").toLowerCase();
      hashTags.add(hashTag);
    }
    System.out.println(autocompleteResult);
    System.out.println(hashTags);
    Assert.assertTrue(autocompleteResult.containsAll(hashTags));
  }

  @Then("I get hashtag search results in order")
  public void i_get_hashtag_search_results_in_order(io.cucumber.datatable.DataTable dataTable) {
    List<String> hashTags = new ArrayList<>();
    for (Map<String, String> hashTagMapping : dataTable.asMaps()) {
      String hashTag = hashTagMapping.get("Hashtag").toLowerCase();
      hashTags.add(hashTag);
    }
    Assert.assertTrue(autocompleteResult.equals(hashTags));
  }

  @Then("I will receive {string} status code")
  public void i_will_receive_status_code(String string) throws Exception {
    mvcResponse.andExpect(status().isBadRequest());
  }

  @Then("the activity {string} has no hashtags")
  public void the_activity_has_no_hashtags(String string) {
    Set<Tag> hashtags = activityRepository.getActivityTags(Integer.parseInt(activityId));
    Set<Tag> expected = new HashSet<>();
    expected.add(null);
    Assert.assertTrue(hashtags.isEmpty() || hashtags.equals(expected));
  }
}