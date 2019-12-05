package com.fremap.policy;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import junit.framework.Assert;























import org.junit.AfterClass;
//import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
//import org.openqa.selenium.NoAlertPresentException;
//import org.openqa.selenium.UnexpectedAlertBehaviour;
//import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


public class CancellationPolicyCenter {

	
	private static WebDriver driver;
	
	private static WriteExcelFile writeFile;
	private static ReadExcel readFile;

	//Locators Cancellation
	private By source = By.id("StartCancellation:StartCancellationScreen:CancelPolicyDV:Source-inputEl");
	private By reason = By.id("StartCancellation:StartCancellationScreen:CancelPolicyDV:Reason-inputEl");							
//private By cancellationEffectiveDate = By.id("StartCancellation:StartCancellationScreen:CancelPolicyDV:CancelDate_date-inputEl");
	
	@BeforeClass
	public static void setUp() {
			System.setProperty(
					"webdriver.chrome.driver","src\\test\\resources\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
			writeFile = new WriteExcelFile();
		    readFile = new ReadExcel();
	
		}



	@Test
	public void cancellationPolicy() throws InterruptedException, IOException {
	
		driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS) ;
		//driver.get("https://10.231.193.245/pc/PolicyCenter.do");
		driver.get("https://10.229.211.136/pc/PolicyCenter.do");
		driver.manage().window().maximize();
		
		String filepath = "C:\\Users\\MCCARO4\\workspace\\Cancellation.xlsx";
		 JavascriptExecutor js = (JavascriptExecutor) driver;

		// Loggin
		WebElement username = driver.findElement(By.id("Login:LoginScreen:LoginDV:username-inputEl"));
		username.sendKeys("su");

		WebElement password = driver.findElement(By.id("Login:LoginScreen:LoginDV:password-inputEl"));
		password.sendKeys("gw");

		WebElement login = driver.findElement(By.id("Login:LoginScreen:LoginDV:submit-btnInnerEl"));
		login.click();
		
		Actions build = new Actions(driver);
        // Policy arrow
        build.moveToElement(driver.findElement(By.id("TabBar:PolicyTab-btnWrap"))).moveByOffset(30, 0).click().build().perform();
        //Textbox SearchPolicy
        build.moveToElement(driver.findElement(By.id("TabBar:PolicyTab:PolicyTab_PolicyRetrievalItem-inputEl"))).moveByOffset(30, 0).click().sendKeys("VRT000000004419651").build().perform();
        
        
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        // Lens button
        WebElement searchButton = driver.findElement(By.id("TabBar:PolicyTab:PolicyTab_PolicyRetrievalItem_Button"));
        searchButton.click();
        
        Thread.sleep(2000);

        try    {
            WebElement objFrau = driver.findElement(By.id("PolicyFile_Summary:Policy_SummaryScreen:WarningsPanelSet_tb:NotMarkFraudulentJob-btnInnerEl"));
            //PolicyFile_Summary:Policy_SummaryScreen:WarningsPanelSet_tb:MarkFraudulentJob-btnInnerEl
            if (objFrau.isEnabled()) {
    			objFrau.click();
            }
            }catch (Exception e)      {
             System.out.println("fraudulent button did not displayed");} 
        
        Thread.sleep(2000);

		//Click on Actions
		WebElement actionsDrop = driver.findElement(By.id("PolicyFile:PolicyFileMenuActions-btnEl"));
		actionsDrop.click();

		//Select on Cancel Policy 
		WebElement cancelPolicy =driver.findElement(By.id("PolicyFile:PolicyFileMenuActions:PolicyFileMenuActions_NewWorkOrder:PolicyFileMenuActions_CancelPolicy-textEl"));
		cancelPolicy.click();
		
		 Thread.sleep(1000); 
		 String sourceText = readFile.getCellValue(filepath, "Sheet5", 1, 1);
		 driver.findElement(source).click();
		 Thread.sleep(1000);
		 driver.findElement(source).sendKeys(sourceText);
		 driver.findElement(source).sendKeys(Keys.ENTER);
		 
		 Thread.sleep(1000); 
		 
	     String reasonText = readFile.getCellValue(filepath, "Sheet5", 2, 1);
	     driver.findElement(reason).click();
		 Thread.sleep(1000);
	     driver.findElement(reason).sendKeys(reasonText);
	     driver.findElement(reason).sendKeys(Keys.ENTER);


		 System.out.println("Page result text: " +  source + reason);
		 
	     Thread.sleep(3000);

		 //START MAIN FLOW   
		//START CANCELLETION BUTTON
		 WebElement startcancellationBtn = driver.findElement(By.id("StartCancellation:StartCancellationScreen:NewCancellation-btnInnerEl"));
		 js.executeScript("arguments[0].scrollIntoView();", startcancellationBtn);
		 startcancellationBtn.click();
		 
		 Thread.sleep(3000);
		
		//CLICK ON QUOTE BUTTON
		 WebElement quoteBtn = driver.findElement(By.id("CancellationWizard:CancellationWizard_EntryScreen:JobWizardToolbarButtonSet:QuoteOrReview-btnInnerEl"));
		 quoteBtn.click();
		 
		 Thread.sleep(5000);
		
		//Go Back to RISK ANALISIS
		  WebElement cancellationBTNBCK  = driver.findElement(By.id("CancellationWizard:Prev-btnInnerEl"));
		  cancellationBTNBCK.click();
		  
		 Thread.sleep(3000);
		 
		 // you are in risk analisis page
		 
		 boolean correct = false;
	        List<WebElement> checkboxes = driver.findElements(By.tagName("img"));
	        for (WebElement eachInput : checkboxes) {
	            if (!eachInput.isSelected() && "x-grid-checkcolumn ".equals(eachInput.getAttribute("class"))) {
	                eachInput.click();
	                correct = true;
	            } else {
	                correct = false;
	            }
	            }
		

         
         Thread.sleep(2000);
		 
		 //btn Aprove
		 WebElement approveBtn = driver.findElement(By.id("CancellationWizard:Job_RiskAnalysisScreen:RiskAnalysisCV:RiskEvaluationPanelSet:Approve-btnInnerEl"));
		 approveBtn.click();
		 Thread.sleep(3000);

		 //btn OK
		 WebElement okBtn = driver.findElement(By.id("RiskApprovalDetailsPopup:Update-btnInnerEl"));
		 okBtn.click();
		 
		 Thread.sleep(2000);
		 
		 //confirmation page ---reason confirmation
		 
		 WebElement nextButton7 = driver.findElement(By.id("CancellationWizard:Next-btnInnerEl"));
		 js.executeScript("arguments[0].scrollIntoView();",  nextButton7);
	     nextButton7.click();
	     
	     Thread.sleep(2000);
		 
		 
		 try    {
			 WebElement confirmationReason = driver.findElement(By.id("CancellationWizard:CancellationWizard_QuoteScreen:Quote_SummaryDV:doc-inputEl"));
		        
		        if (confirmationReason.isDisplayed()) {
		        	 System.out.println(confirmationReason); 
		        }
		        }catch (Exception e)      {
		         System.out.println("Pack 44 did not displayed");} 
		 
		 
		 Thread.sleep(3000);
		 Actions builds = new Actions(driver);
		 builds.moveToElement(driver.findElement(By.id("CancellationWizard:CancellationWizard_QuoteScreen:JobWizardToolbarButtonSet:BindOptions-btnInnerEl"))).moveByOffset(30,0).click().build().perform();
	     builds.moveToElement(driver.findElement(By.id("CancellationWizard:CancellationWizard_QuoteScreen:JobWizardToolbarButtonSet:BindOptions:CancelNow-textEl"))).moveByOffset(30,0).click().build().perform();
		
		 
	     Thread.sleep(2000);
	     
	     WebElement okAlert = driver.findElement(By.id("button-1005-btnInnerEl"));
         okAlert.click();
         
         Thread.sleep(2000);
         //Final Message
         WebElement finalMessage = driver.findElement(By.id("JobComplete:JobCompleteScreen:JobCompleteDV:ViewPolicy-inputEl"));
	     System.out.println(finalMessage); 
	     
	   


}

	private void trackError(Exception e) {
		// TODO Auto-generated method stub
		
	}



	@AfterClass
	public static void tearDown() {

		// driver.close();
		// driver.quit();
	}

}