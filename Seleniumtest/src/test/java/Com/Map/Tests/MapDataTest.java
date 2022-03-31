package Com.Map.Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

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
		logger = extent.startTest("map test start");
		mapdatapage.ValidateMapData(cityone);
		String data = driver.getCurrentUrl();
		System.out.println(data);
		//Assert.assertEquals(data.substring(58, 81), "37.7578149,-122.5078124");
		mapdatapage.ValidateURL(citytwo);
		mapdatapage.ValidateDistance();
		logger.log(LogStatus.PASS, "All validation passed");
	}

}
