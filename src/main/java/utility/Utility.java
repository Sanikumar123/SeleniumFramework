package utility;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;

public class Utility {
	
	public static void checkExecution(String suiteName, String testName, String dataRunMode, ExcelReader excel) throws IOException
	{
		if(!isSuiteRunnable(suiteName))
		{
			throw new SkipException("Skipping the test"+testName+"as the runmode of testsuite "+suiteName+"is set as NO!");
		}
		if(!isTestRunnable(testName))
		{
			throw new SkipException("Skipping the test"+testName+"as the runmode of testcase "+testName+"is set as NO!");
		}
		if(dataRunMode.equalsIgnoreCase(Constants.RUNMODE_N))
		{
			throw new SkipException("Skipping the test"+testName+"as the runmode of testdata is set as NO!");
		}
		
	}
	
	
	public static boolean isSuiteRunnable(String suiteName) throws IOException
	{
		ExcelReader excel = new ExcelReader(Constants.MASTER_SUITE_PATH);
		int RowCount= excel.getRowCount(Constants.MASTER_SUITE_SHEET_NAME);
		//System.out.println("Suite row count "+RowCount);
		
		for(int i=1;i<RowCount;i++)
		{
			String testSuiteName = excel.getData(Constants.MASTER_SUITE_SHEET_NAME, i, Constants.MASTER_SUITE_COL_NAME);
			if(testSuiteName.equalsIgnoreCase(suiteName))
			{
				String testRunMode = excel.getData(Constants.MASTER_SUITE_SHEET_NAME, i, Constants.RUNMODE_COL_NAME);
				if(testRunMode.equals(Constants.RUNMODE_Y))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
	}
	
	public static boolean isTestRunnable(String testname) throws IOException
	{
		ExcelReader excel = new ExcelReader(Constants.BANKMANAGER_SUITE_PATH);
		int rowCount = excel.getRowCount(Constants.TEST_SHEET_NAME);
		
		for(int i=1;i<rowCount;i++)
		{
			String testCaseName = excel.getData(Constants.TEST_SHEET_NAME, i, Constants.TEST_COL_NAME);
			
			if(testCaseName.equalsIgnoreCase(testname))
			{
				String runModeStatus = excel.getData(Constants.TEST_SHEET_NAME, i, Constants.RUNMODE_COL_NAME);
				if(runModeStatus.equalsIgnoreCase(Constants.RUNMODE_Y))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
		
		
	}
	
	
@DataProvider
public static Object[][] getData(String testName, ExcelReader excel)
{
	int totalRowCount = excel.getRowCount(Constants.SUITE_DATA_SHEET);
	//System.out.println("total test data row count:"+totalRowCount);
	
	String test_Name = testName;
	//System.out.println(test_Name);
	int testCaseRowNum = 0;
	for(testCaseRowNum=0;testCaseRowNum<totalRowCount;testCaseRowNum++)
	{
		String testcaseName = excel.getData(Constants.SUITE_DATA_SHEET, testCaseRowNum, 0);
		//System.out.println("test "+ testcaseName);
		if(testcaseName.equalsIgnoreCase(test_Name))
		{
			break;
		}
	}
	//System.out.println("test case row:"+ testCaseRowNum);
	
	int dataStartRow = testCaseRowNum+2;
	int totaldatarow =0;
	
	while(!excel.getData(Constants.SUITE_DATA_SHEET, dataStartRow+totaldatarow, 0).equals(""))
	{
		totaldatarow++;
	}
	//System.out.println(totaldatarow);
	
	int datacol = testCaseRowNum+1;
	int totaldatacell=0;
	
	while(!excel.getData(Constants.SUITE_DATA_SHEET, datacol, totaldatacell).equals(""))
	{
		totaldatacell++;
	}
	
//System.out.println(totaldatacell);
	
	Object[][] data = new Object[totaldatarow][1];
	
	int i=0;
	for(int row =dataStartRow;row<dataStartRow+totaldatarow; row++)
	{
		Hashtable<String,String> table = new Hashtable<String,String>();
		
		for(int col = 0;col<totaldatacell;col++)
		{
			String testColRow = excel.getData(Constants.SUITE_DATA_SHEET, datacol, col);
			String testDataRow = excel.getData(Constants.SUITE_DATA_SHEET, row, col);
			table.put(testColRow, testDataRow);
		}
		
		data[i][0]=table;
		i++;
	}
	return data;
}

}
