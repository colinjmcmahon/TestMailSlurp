package MyTests;

import com.mailslurp.apis.InboxControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.clients.ApiException;
import com.mailslurp.models.Inbox;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Test
    public void hello(){
        System.out.println("hello");
        driver.findElement(By.cssSelector("[data-test=sign-in-create-account-link]")).click();
    }

    private static Inbox inbox;

    @Test
    public void test3_canCreateEmailAddressAndSignUp() throws ApiException {
        // create a real, randomized email address with MailSlurp to represent a user
        InboxControllerApi inboxControllerApi = new InboxControllerApi(mailslurpClient);
        inbox = inboxControllerApi.createInbox(null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        // check the inbox was created
        assertNotNull(inbox.getId());
        assertTrue(inbox.getEmailAddress().contains("@mailslurp.com"));

        // fill the playground app's sign-up form with the MailSlurp
        // email address and a random password
        driver.findElement(By.name("email")).sendKeys(inbox.getEmailAddress());
        driver.findElement(By.name("password")).sendKeys("Qwerty_12");

        // submit the form to trigger the playground's email confirmation process
        // we will need to receive the confirmation email and extract a code
        driver.findElement(By.cssSelector("[data-test=sign-up-create-account-button]")).click();
    }






}


