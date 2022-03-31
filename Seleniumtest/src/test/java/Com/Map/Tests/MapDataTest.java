package Com.Map.Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;

import Com.Map.BaseTest.TestBase;

public class MapDataTest extends TestBase{
	


	public MapDataTest() throws IOException 
	{
		super();
		
	}
	@Test
	public void MapPage() throws InterruptedException
	{
		SoftAssert softAssert = new SoftAssert();
		logger = extent.startTest("map test start");
		mapdatapage.ValidateMapData(cityone);
		String data = driver.getCurrentUrl();
		System.out.println(data);
		softAssert.assertEquals(data.substring(58, 64), "37.757");
		mapdatapage.ValidateURL(citytwo);
		mapdatapage.ValidateDistance();
		softAssert.assertAll();
	}

}
