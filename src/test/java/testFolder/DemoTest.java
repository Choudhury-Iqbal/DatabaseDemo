package testFolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import util.DataBaseTest;

public class DemoTest extends DataBaseTest {
	@Test(dataProvider = "countCountryDataProvider")
	public void countCountryTest(String query, String expectedResults,String testname)  {
		logger=extent.startTest(testname);
		try {
			ResultSet res = st.executeQuery(query);
			res.next();
			String actualCount = res.getString(1);
			Assert.assertEquals(actualCount, expectedResults);
			logger.log(LogStatus.PASS, "Total Number of countries verified");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(LogStatus.FAIL,"Total number of Countries not matched");
			e.printStackTrace();
		}
	}

	@Test(dataProvider="capitalDataProvider")
	public void findCountryCapitalTest(String query, String expectedResults,String testname) {
		logger=extent.startTest(testname);

		try {
			ResultSet res = st
					.executeQuery(query);
			res.next();
			String actualCapital = res.getString("country_capital");
			Assert.assertEquals(actualCapital, expectedResults);
			logger.log(LogStatus.PASS, "Capital of the country (England) is verified");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(LogStatus.FAIL, "Capital of Country not matched.");
			e.printStackTrace();
		}

	}

	@DataProvider(name = "countCountryDataProvider")
	public Object[][] loadCountryCountTestData() {
		String path = System.getProperty("user.dir");
		util.ExcelDataConfig config = new util.ExcelDataConfig(path + "/testdata/sapTestData.xlsx");
		int rows = config.getRowCount(0);
		Object[][] data = new Object[1][3];
		data[0][2]=config.getData(0,1,0);
		data[0][0] = config.getData(0, 1, 1);
		data[0][1] = config.getData(0, 1, 2);
		return data;
	}
	
	
	
	@DataProvider(name = "capitalDataProvider")
	public Object[][] loadFindCountryCapitalData() {
		String path = System.getProperty("user.dir");
		util.ExcelDataConfig config = new util.ExcelDataConfig(path + "/testdata/sapTestData.xlsx");
		int rows = config.getRowCount(0);
		Object[][] data = new Object[1][3];
		data[0][2]=config.getData(0, 2, 0);
		data[0][0] = config.getData(0, 2, 1);
		data[0][1] = config.getData(0, 2, 2);
		return data;
	}
}
