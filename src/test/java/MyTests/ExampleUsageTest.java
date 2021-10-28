package MyTests;

import com.mailslurp.clients.ApiClient;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ExampleUsageTest {

    private static WebDriver driver = null;

    private static final String YOUR_API_KEY = "def6e020434ac89649d54481f0cc6f78";

    // set a timeout as fetching emails might take time
    private static final Long TIMEOUT_MILLIS = 30000L;
    //private static final String WEBDRIVER_PATH = "/path/to/your/webdriver";

    private static ApiClient mailslurpClient;
    //private static WebDriver driver;

    @BeforeClass
    public static void beforeAll() {


        // setup mailslurp
        mailslurpClient = com.mailslurp.clients.Configuration.getDefaultApiClient();
        mailslurpClient.setApiKey(YOUR_API_KEY);
        mailslurpClient.setConnectTimeout(TIMEOUT_MILLIS.intValue());

        // setup webdriver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

    }

    private static final String PLAYGROUND_URL = "https://playground.mailslurp.com";

    @Test
    public void test1_canLoadAuthenticationPlayground() {

        driver.get(PLAYGROUND_URL);
        assertEquals(driver.getTitle(), "React App");
    }


}


