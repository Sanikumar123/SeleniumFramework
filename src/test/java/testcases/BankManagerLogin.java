package testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import basetest.BaseTest;
import utility.DataProviders;
import utility.DriverManager;
import utility.ExcelReader;
import utility.Utility;


public class BankManagerLogin extends BaseTest {
	
	@Test(dataProviderClass=DataProviders.class,dataProvider="getBankManagerSuiteData")
    public void addCustomerTest(Hashtable<String,String> data) throws IOException, InterruptedException

	{
		
		ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\testdata\\BankManagerSuite.xlsx");
		//System.out.println(excel.getData("TestData", 2, "LoginTest"));
	   // System.out.println(excel.getData("TestData", 5, 0));
		//System.out.println(data.get("Username"));
		Utility.checkExecution("master", "addCustomerTest", data.get("RunMode"), excel);
		launchBrowser(data.get("Browser"));
		logInfo("Browser Launched:"+data.get("RunMode"));
		
		DriverManager.getDriver().findElement(By.xpath("//button[@ng-click='manager()']")).click();
		DriverManager.getDriver().findElement(By.xpath("//button[@ng-click='addCust()']")).click();
		DriverManager.getDriver().findElement(By.xpath("//input[@ng-model='fName']")).sendKeys(data.get("Username"));
		DriverManager.getDriver().findElement(By.xpath("//input[@ng-model='lName']")).sendKeys(data.get("Password"));
		logInfo("Username and Password Entered!!!");
		DriverManager.getDriver().findElement(By.xpath("//input[@ng-model='postCd']")).sendKeys(data.get("Postcode"));
		DriverManager.getDriver().findElement(By.xpath("//button[@type='submit' and contains(text(),'Add Customer')]")).click();
		Alert alert = DriverManager.getDriver().switchTo().alert();
		Thread.sleep(3000);
		alert.accept();
	}
	
	@AfterMethod
	public void quit()
	{
		logInfo("Login Test Completed!!!");
		tearDown();
	}

}
