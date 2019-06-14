package basetest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import extentlisteners.ExtentListeners;
import utility.DriverFactory;
import utility.DriverManager;

public class BaseTest {
	
	private static FileInputStream fin;
	private static Properties prop= new Properties();;
	private static WebDriver driver;
	public static boolean grid=false;
	
	@BeforeSuite
	public void setUp()
	{
		DriverFactory.setGridPath("http://localhost:4444/wd/hub");
		//Set config file path
		DriverFactory.setConfigPropertyFilePath(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
		
		//Set Driver exe path
		if(System.getProperty("os.name").equalsIgnoreCase("mac"))
		{
		}
		else
		{
			DriverFactory.setChromeDriverExePath(System.getProperty("user.dir")+"\\src\\test\\resources\\Executables\\chromedriver.exe");
		}
	
	//Load config file 
		try {
			fin = new FileInputStream(DriverFactory.getConfigPropertyFilePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			prop.load(fin);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void logInfo(String message)
	{
		ExtentListeners.testReport.get().info(message);
	}
	
	public static void launchBrowser(String browserName)
	{
		
		if(System.getenv("Execution Type")!=null && System.getenv("Execution Type").equals("Grid"))
		{
			grid=true;
		}
		DriverFactory.setRemote(grid);
		
		if(DriverFactory.isRemote())
		{
			DesiredCapabilities cap = null;
			
			if(browserName.equals("chrome"))
			{
				cap=DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.ANY);
			}
			else if(browserName.equals("firefox"))
			{
				cap=DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.ANY);
			}
			
			try {
				driver=new RemoteWebDriver(new URL(DriverFactory.getGridPath()),cap);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
		
		if(browserName.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
			driver = new ChromeDriver();
		}
		
		}
		DriverManager.setDriver(driver);
		DriverManager.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().get(prop.getProperty("testsiteurl"));
		


	}
	//@AfterSuite
	public static void tearDown()
	{
		DriverManager.getDriver().quit();
	}

}

