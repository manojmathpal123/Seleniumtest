package Com.Map.Pages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import Com.Map.BaseTest.TestBase;


public class MapDataPage extends TestBase {
	
	@FindBy(id="searchboxinput")
	WebElement Searchinputbox;
	
	@FindBy(id="searchbox-searchbutton")
	WebElement Searchboxbutton;
	
	@FindBy(xpath="//img[@alt=\"Directions\"]")
	WebElement Direction;
	
	@FindBy(xpath="//img[contains(@src,\"directions_car\")]")
	WebElement Directioncar;
	
	@FindBy(xpath="//*[@id=\"sb_ifc51\"]/input")
	WebElement Cityname;
	
	@FindBy(xpath="//*[@id=\"directions-searchbox-0\"]/button[1]")
	WebElement Citynamedirection;
	
	

	public MapDataPage() throws IOException 
	{
		PageFactory.initElements(driver, this);
	}
	
	public void ValidateMapData(String searchinput) throws InterruptedException
	{
		Searchinputbox.sendKeys("San Francisco, California");
		WaitforwebElementVisible(Searchboxbutton, 20);
		Searchboxbutton.click();
		WaitforwebElementVisible(Direction, 40);
		Direction.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	public void ValidateURL(String cityname)
	{
		WaitforwebElementVisible(Directioncar, 20);
		Directioncar.click();
		WaitforwebElementVisible(Cityname, 20);
		Cityname.sendKeys("Chico, California");
		Citynamedirection.click();
		
	}
	
	public void ValidateDistance()
	{
	    int route =driver.findElements(By.xpath("//img[contains(@id,\"section-directions-trip-travel-mode\")]")).size();
		if(route>1)
		{
	    List<WebElement> childs1 =driver.findElements(By.xpath("//div[@id=\"pane\"]//div[@class=\"siAUzd-neVct\"]"));
		try {
	        File file = new File("routes.txt");
	        FileWriter fw = new FileWriter(file.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        for(WebElement childs : childs1)
			{
	                bw.write(childs.getText());
	        }
	            bw.close();
	        }
	     catch (IOException e) {
	        e.printStackTrace();
	        }
		    }
	}
		
		
		
		
	
	

}
