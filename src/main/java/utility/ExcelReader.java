package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelReader {
	
	public FileInputStream fin=null;
	public static XSSFWorkbook wb=null;
	public static XSSFSheet sheet=null;
	public static XSSFRow row=null;
	public static XSSFCell cell=null;
	
	
	
	public ExcelReader(String path) throws IOException
	{
		fin = new FileInputStream(path);
		wb = new XSSFWorkbook(fin);
		//fin.close();
		
	}
	
	public static int getRowCount(String sheetName)
	{
		int index=wb.getSheetIndex(sheetName);
		sheet = wb.getSheetAt(index);
		int rowCount = sheet.getLastRowNum()+1;
		
		return rowCount;
	}


	public String getData(String sheetName, int rowNum, int cellNum)
	{
		String cellValue=null;
		int index = wb.getSheetIndex(sheetName);
		if(index == -1)
			return "";
		
		sheet = wb.getSheetAt(index);
		row = sheet.getRow(rowNum);
		if(row == null)
			return "";
		
		
		cell = row.getCell(cellNum);
		if(cell==null)
			return "";
		
		if(cell.getCellType()==CellType.STRING)
		{
		  cellValue = cell.getStringCellValue();
		}
		
		return cellValue;	
		
	}
	
	
	public String getData(String sheetName, int rowNum, String cellName)
	{
		String cellValue=null;
		int colnum=-1;
		int index = wb.getSheetIndex(sheetName);
		//System.out.println(index);
		if(index == -1)
			return "";
		
		sheet = wb.getSheetAt(index);
		row = sheet.getRow(0);
		if(row == null)
			return "";
		//System.out.println(row.getLastCellNum());
		for(int i=0;i<row.getLastCellNum();i++)
		{
			
			//System.out.println(row.getCell(i).getStringCellValue());
			if(row.getCell(i).getStringCellValue().equals(cellName))
			{
				colnum=i;
				//flag=0;
			}
			
		}
		//System.out.println(colnum);
		if(colnum==-1)
		{
			return "";
		}
		
		row = sheet.getRow(rowNum);
		cell = row.getCell(colnum);
		if(cell==null)
			return "";
		
		if(cell.getCellType()==CellType.STRING)
		{
		  cellValue = cell.getStringCellValue();
		}
		
		
		//System.out.println(cellValue);
		return cellValue;	
		
	}

}





