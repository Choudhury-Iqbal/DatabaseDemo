package util;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataConfig {
	XSSFWorkbook wb;
	XSSFSheet ws;

	public ExcelDataConfig(String excelPath) {
		try {
			File src = new File(excelPath);
			FileInputStream fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public String getData(int sheetNumber, int row, int column) {
		String data;
		ws = wb.getSheetAt(sheetNumber);
		data = ws.getRow(row).getCell(column).getStringCellValue();
		return data;
	}

	public int getRowCount(int sheetIndex) {
		int row = wb.getSheetAt(sheetIndex).getLastRowNum();
		row = row + 1;
		return row;
	}

	public int getColCount(int sheetIndex) {
		Row r = wb.getSheetAt(0).getRow(1);
		int maxCell = r.getLastCellNum();
		return maxCell;
	}

	/// trying to read different set of data reader
	public String getCellData(int sheetIndex, int rowNum, int colNum) {
		XSSFRow row = null;
		XSSFCell cell = null;
		try {
			ws = wb.getSheetAt(sheetIndex);
			row = ws.getRow(rowNum);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";
			if (cell.getCellType() == CellType.STRING) {
				//System.out.println("Cell Value " + cell.getStringCellValue());
				return cell.getStringCellValue();
			} else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {

				String cellText = String.valueOf((int)cell.getNumericCellValue());
				//System.out.println("Cell Value " + cellText);
				return cellText;
			} else if (cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist in xlsx";
		}
	}
  
	public static void main(String[] args) {
		String path = System.getProperty("user.dir");
		System.out.println("Printing Path" + path + "/resources/testdata.xlsx");
		ExcelDataConfig config = new ExcelDataConfig(path + "/template/template.xlsx");
		int rowCount = config.getRowCount(0);
		int colCount = config.getColCount(0);
		Object data[][] = new Object[colCount-1][rowCount - 1];
		System.out.println(colCount);

		for (int i = 1; i < colCount; i++) {
			for (int j = 1; j < rowCount; j++)
				data[i - 1][j - 1] = config.getCellData(0, j, i);
			

		}
		
		

	}

}
