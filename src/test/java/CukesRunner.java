import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = "com.octopus.stepdef",
        features = "src/test/resources/feature",
        tags = "@run",
        format = { "pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class CukesRunner {

}
