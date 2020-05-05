package com.springvuegradle.team6;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"}, // How to format test report, "pretty" is good for human eyes
        glue = {"com.springvuegradle.team6.steps"}, // Where to look for your tests' steps
        features = "src/test/resources/edmun/features/", // Where to look for your features
        strict = true // Causes cucumber to fail if any step definitions are still undefined
)
public class CucumberRunnerTest {
}
