package Com.Map.BaseTest;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.HTMLReporter;
import com.relevantcodes.extentreports.LogStatus;

import Com.Map.Pages.MapDataPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public MapDataPage mapdatapage;
	public String cityone = "San Francisco, California";
	public String citytwo = "Chico, California";
	
	public HTMLReporter htmlreporter;
	public ExtentReports extent;
	public ExtentTest logger;
	String screenShotName="";
	
	
	@BeforeTest
	public void startReport(){
	
	extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/MAPReport.html", true);
	extent
	                .addSystemInfo("Host Name", "MapProject")
	                .addSystemInfo("Environment", "EXL")
	                .addSystemInfo("User Name", "Manoj Mathpal");
	                
	                extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
	}
	@BeforeMethod
	public void setup() throws IOException
	{
		
		initialzation();
		mapdatapage = new MapDataPage();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		Logger log = Logger.getLogger("devpinoyLogger");
		driver.get(prop.getProperty("URL"));
		
		
	}
	
	public TestBase() throws IOException
	{
		
		try
		{
		prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +"/src/test/java/Com/Map/Config/Config.properties");
		prop.load(fis);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void WaitforwebElementVisible(WebElement element, int timeout)
	{
		WebDriverWait waitajax = new WebDriverWait(driver, timeout);
		waitajax.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void initialzation()
	{
		String browsername = prop.getProperty("browser");
		if(browsername.equals("chrome"))
		{

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			
		}
		if(browsername.equals("FF"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			
		}
	}
	
	public static String capture(WebDriver driver,String screenShotName) throws IOException
    {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") +"/test-output/"+screenShotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);        
                     
        return dest;
    }
	
	@AfterMethod
	public void getResult(ITestResult result) throws IOException
	{
		if(result.getStatus() == ITestResult.SUCCESS){
			logger.log(LogStatus.PASS, "Test Case passed is "+result.getName());
			String screenShotPath = capture(driver, result.getName());	
            logger.log(LogStatus.PASS, "Snapshot below: " + logger.addScreenCapture(screenShotPath));
			
			}
		else if(result.getStatus() == ITestResult.SKIP){
			logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
			}
		else if(result.getStatus() == ITestResult.FAILURE){
			
			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
			}
		
		extent.endTest(logger);
		
		
	}
	
	@AfterTest
	public void endReport() throws IOException
	{
		
		File htmlFile = new File(System.getProperty("user.dir") +"/test-output/MAPReport.html");
		Desktop.getDesktop().browse(htmlFile.toURI());
		driver.close();
	  extent.flush();
	  //extent.close();
	    }
		
	
}
