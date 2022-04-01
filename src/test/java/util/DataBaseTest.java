package util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DataBaseTest {
	public static Statement st = null;
	public static Connection con = null;
	public ExtentReports extent;
	public ExtentTest logger;
	String serverName = null;
	String portNo = null;
	String dbName = null;
	String driverName = null;
	String userName = null;
	String password = null;

	@BeforeTest
	public void startReport() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/extentReport/databaseTestExtentReport.html",
				true);
		extent.addSystemInfo("Host Name", "Local Host").addSystemInfo("Environment", "Test Environment")
				.addSystemInfo("User Name", "Choudhury Iqbal");
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());

		}
		extent.endTest(logger);
	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}

	@BeforeClass
	public void setup() throws Exception {
		serverName = FileReaderManager.getInstance().getConfigReader().getConfigKey("Server");
		portNo = FileReaderManager.getInstance().getConfigReader().getConfigKey("Port");
		dbName = FileReaderManager.getInstance().getConfigReader().getConfigKey("DatabaseName");
		userName = FileReaderManager.getInstance().getConfigReader().getConfigKey("Username");
		password = FileReaderManager.getInstance().getConfigReader().getConfigKey("Password");
		dbName = FileReaderManager.getInstance().getConfigReader().getConfigKey("DatabaseName");
		driverName = FileReaderManager.getInstance().getConfigReader().getConfigKey("Driver");
		Class.forName(driverName).newInstance();
		String dbURL = serverName + ":" + portNo + "/" + dbName;
		Connection con = DriverManager.getConnection(dbURL, userName, password);
		st = con.createStatement();
	}

	@AfterClass
	public void tearDown() throws SQLException {
		if (con != null) {
			con.close();
		}
	}
}
