package com.springvuegradle.team6.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@TestPropertySource(properties = {"ADMIN_EMAIL=test@test.com", "ADMIN_PASSWORD=test"})
public class ActivityHistoryRepositoryTest {

    @Autowired private ActivityHistoryRepository activityHistoryRepository;
    @Autowired private ActivityRepository activityRepository;
    @Autowired private ProfileRepository profileRepository;

    @BeforeEach
    void setup() {
        activityHistoryRepository.deleteAll();
    }

    @Test
    void testSingleHistoryFindByActivity() {
        Set<Email> emails = new HashSet<>();
        Email email = new Email("johnydoe99@gmail.com");
        emails.add(email);
        Profile profile = new Profile();
        profile.setFirstname("John");
        profile.setLastname("Doe");
        profile.setEmails(emails);
        profile.setDob("2010-01-01");
        profile.setPassword("Password1");
        profile.setGender("male");
        profile = profileRepository.save(profile);

        Activity activity = new Activity();
        activity.setProfile(profile);
        activity.setActivityName("Run at Hagley Park");
        activity.setContinuous(true);
        activity = activityRepository.save(activity);

        ActivityHistory history = new ActivityHistory();
        history.setActivity(activity);
        history.setTimeDate(new Date());
        history.setMessage("A change to this activity has occurred!!!");
        history = activityHistoryRepository.save(history);

        Profile profile1 = profileRepository.findByEmailsContains(email);
        Activity activity1 = activityRepository.findByProfile_Id(profile1.getId()).get(0);
        Set<ActivityHistory> histories = activityHistoryRepository.findByActivity_id(activity1.getId());
        org.junit.jupiter.api.Assertions.assertEquals(1, histories.size());
    }

    @Test
    void testTwoHistoryUpdatesOneActivityFindByActivity() {
        Set<Email> emails = new HashSet<>();
        Email email = new Email("secondemail@gmail.com");
        emails.add(email);
        Profile profile = new Profile();
        profile.setFirstname("Gon");
        profile.setLastname("Freecss");
        profile.setEmails(emails);
        profile.setDob("2010-01-01");
        profile.setPassword("Password1");
        profile.setGender("male");
        profile = profileRepository.save(profile);

        Activity activity = new Activity();
        activity.setProfile(profile);
        activity.setActivityName("Play rock, paper, scissors");
        activity.setContinuous(true);
        activity = activityRepository.save(activity);

        ActivityHistory history = new ActivityHistory();
        history.setActivity(activity);
        history.setTimeDate(new Date());
        history.setMessage("A change to this activity has occurred!!!");
        history = activityHistoryRepository.save(history);

        ActivityHistory history1 = new ActivityHistory();
        history1.setActivity(activity);
        history1.setTimeDate(new Date());
        history1.setMessage("A second change to this activity has occurred!!!");
        history1 = activityHistoryRepository.save(history1);

        Profile profile1 = profileRepository.findByEmailsContains(email);
        Activity activity1 = activityRepository.findByProfile_Id(profile1.getId()).get(0);
        Set<ActivityHistory> histories = activityHistoryRepository.findByActivity_id(activity1.getId());
        org.junit.jupiter.api.Assertions.assertEquals(2, histories.size());
    }
}
