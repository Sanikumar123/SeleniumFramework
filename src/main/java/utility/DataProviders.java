package utility;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import utility.Constants;
import utility.Utility;
import utility.ExcelReader;

public class DataProviders {
	
	@DataProvider(parallel=true)
	public static Object[][] getBankManagerSuiteData(Method m) throws IOException
	{
		String testCase=m.getName();
		//System.out.println(testCase);
		ExcelReader excel = new ExcelReader(Constants.BANKMANAGER_SUITE_PATH);
		return Utility.getData(testCase, excel);
	}
	

}
