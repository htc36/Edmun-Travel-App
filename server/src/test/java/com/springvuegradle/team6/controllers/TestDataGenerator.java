package com.springvuegradle.team6.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springvuegradle.team6.models.entities.Activity;
import com.springvuegradle.team6.models.entities.ActivityQualificationMetric;
import com.springvuegradle.team6.models.entities.ActivityRole;
import com.springvuegradle.team6.models.entities.ActivityRoleType;
import com.springvuegradle.team6.models.entities.Profile;
import com.springvuegradle.team6.models.entities.Unit;
import com.springvuegradle.team6.models.repositories.ActivityQualificationMetricRepository;
import com.springvuegradle.team6.models.entities.VisibilityType;
import com.springvuegradle.team6.models.repositories.ActivityRepository;
import com.springvuegradle.team6.models.repositories.ActivityRoleRepository;
import com.springvuegradle.team6.models.repositories.ProfileRepository;
import com.springvuegradle.team6.requests.CreateProfileRequest;
import com.springvuegradle.team6.requests.LoginRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Generates and automatically fills mockMVC with test data to aid in writing tests */
public class TestDataGenerator {
  public static int createJohnDoeUser(MockMvc mvc, ObjectMapper mapper, MockHttpSession session)
      throws Exception {
    String create_profile_url = "/profiles/";
    CreateProfileRequest valid_request = new CreateProfileRequest();
    valid_request.firstname = "John";
    valid_request.middlename = "S";
    valid_request.lastname = "Doe";
    valid_request.nickname = "BigJ";
    valid_request.bio = "Just another plain jane";
    valid_request.email = "johndoe@uclive.ac.nz";
    valid_request.password = "SuperSecurePassword123";
    valid_request.dob = "2000-11-11";
    valid_request.gender = "male";
    valid_request.fitness = 0;

    String body =
        mvc.perform(
                post(create_profile_url)
                    .content(mapper.writeValueAsString(valid_request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(session))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
    return Integer.parseInt(body);
  }

  public static int loginJohnDoeUser(MockMvc mvc, ObjectMapper mapper, MockHttpSession session)
      throws Exception {
    String login_url = "/login";
    LoginRequest login_request = new LoginRequest();
    login_request.email = "johndoe@uclive.ac.nz";
    login_request.password = "SuperSecurePassword123";

    mvc.perform(
            post(login_url)
                .content(mapper.writeValueAsString(login_request))
                .contentType(MediaType.APPLICATION_JSON)
                .session(session))
        .andExpect(status().isOk());

    String body =
        mvc.perform(get("/profiles/id").session(session))
            .andReturn()
            .getResponse()
            .getContentAsString();

    return Integer.parseInt(body);
  }

  public static ActivityRole createActivityMemberType(
      MockMvc mvc,
      ObjectMapper mapper,
      MockHttpSession session,
      ActivityRoleType activityRoleType,
      Activity activity,
      Profile profile)
      throws Exception {
    ActivityRole activityRole = new ActivityRole();
    activityRole.setProfile(profile);
    activityRole.setActivity(activity);
    activityRole.setActivityRoleType(activityRoleType);
    return activityRole;
  }

  public static void addActivityRole(
      Profile profile,
      Activity activity,
      ActivityRoleType roleType,
      ActivityRoleRepository activityRoleRepository) {
    ActivityRole role = new ActivityRole();
    role.setProfile(profile);
    role.setActivityRoleType(roleType);
    role.setActivity(activity);
    activityRoleRepository.save(role);
  }

  public static Profile createExtraProfile(ProfileRepository profileRepository) {
    Profile otherProfile = new Profile();
    otherProfile.setFirstname("Poly");
    otherProfile.setLastname("Pocket");
    otherProfile.setDob("2010-10-10");
    otherProfile.setPassword("Password1");
    otherProfile.setGender("female");
    profileRepository.save(otherProfile);
    return otherProfile;
  }

  public static ActivityQualificationMetric createDummyActivityMetric(
      Activity activity, Unit unit, ActivityQualificationMetricRepository metricRepo) {
    ActivityQualificationMetric activityMetrics = new ActivityQualificationMetric();
    activityMetrics.setActivity(activity);
    activityMetrics.setUnit(unit);
    activityMetrics.setDescription("Dummy");
    activityMetrics.setTitle("test");
    metricRepo.save(activityMetrics);
    return activityMetrics;
  }

  public static Activity createExtraActivity(
      Profile profile, ActivityRepository repo, VisibilityType vis) {
    Activity activity = new Activity();
    activity.setProfile(profile);
    activity.setVisibilityType(vis);
    repo.save(activity);
    return activity;
  }
}
