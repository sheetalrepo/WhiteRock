package com;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Sheetal_Singh Dated: 30th Jan 2020
 */
public class MainClass {

	public static void main(String[] args) {
		// WebDriver driver = Utils.initMethod();
		Utils.printWelcomeMessage();
		WebDriver driver = Utils.initializeChromeDriver();
		Utils.loadNiftyUrl(driver);

		// Flow 1: Fetch Current Date Data
		String defaultDate = driver.findElement(By.xpath(PropertyClass.DEFAULT_DATE_SELECTED)).getText();
		System.out.println("**************************************************************************************");
		System.out.println("Fetching Data For : " + defaultDate);

		List<List<String>> optionChainCompleteData = Utils.getCompleteDataInObject(driver);
		Utils.printCompleteData(optionChainCompleteData);

		// Top OI for PUT/CALL
		Utils.fetchTopOICallPutCombinedWithStrikePrice(optionChainCompleteData, PropertyClass.TOP_OI_DATA_COUNT);

		String folderPath = Utils.createMonthAndWeeklyFolder(defaultDate);
		System.out.println("Folder Path in Run1: " + folderPath);
		String csvfilePath = Utils.writeCSVFile(optionChainCompleteData, defaultDate, folderPath);
		System.out.println("CSV File Path in Run1: " + csvfilePath);

		String monthEndDate = fetchMonthEndDateFromDropdown(driver, defaultDate); // TODO
		Utils.quitCode(driver);
		if (defaultDate.equals(monthEndDate)) {
			System.out
					.println("$$$ PLS NOTE:  Last week of the month going on; so single report will be generated $$$");
		} else {
			fetchMonthyReport(folderPath, monthEndDate);
		}

		Utils.printGoodByeMessage();
		System.exit(0);
	}

	public static String fetchMonthEndDateFromDropdown(WebDriver driver, String defaultDate) {
		System.out.println("Default date:" + defaultDate);

		Select dateDropdown = new Select(driver.findElement(By.id("date")));
		List<WebElement> allOptions = dateDropdown.getOptions();

		List<String> dateList = new ArrayList<String>();
		for (WebElement element : allOptions) {
			dateList.add(element.getText());
		}

		System.out.println("All Dates in Dropdown: " + dateList);
		int dateLength = defaultDate.length();
		String MMM = defaultDate.substring(dateLength - 7, dateLength - 4);

		List<String> filteredList = dateList.stream().filter(x -> x.contains(MMM)).collect(Collectors.toList());
		System.out.println("Filtered List For Current Month: " + filteredList);

		String monthEndDate = filteredList.get(filteredList.size() - 1);
		System.out.println("Month End Date: " + monthEndDate);
		return monthEndDate;
	}

	private static void fetchMonthyReport(String folderPath, String monthEndDate) {
		System.out.println("\n\n\n\n");
		System.out.println("*********************** MONTHY REPORT DATA ************************");

		WebDriver driver = Utils.initializeChromeDriver(); // New Driver
		String monthlyUrl = PropertyClass.NIFTY_OPTION_CHAIN_MONTHY_URL + monthEndDate;
		driver.get(monthlyUrl);
		System.out.println("Opening:" + monthlyUrl);

		List<List<String>> optionChainCompleteData2 = Utils.getCompleteDataInObject(driver);
		Utils.printCompleteData(optionChainCompleteData2);

		// Top OI for PUT/CALL
		Utils.fetchTopOICallPutCombinedWithStrikePrice(optionChainCompleteData2, PropertyClass.TOP_OI_DATA_COUNT);

		System.out.println("Folder Path in Run2: " + folderPath);
		String csvfilePath2 = Utils.writeCSVFile(optionChainCompleteData2, monthEndDate, folderPath);
		System.out.println("CSV File Path in Run2: " + csvfilePath2);

		// Quit
		Utils.quitCode(driver);
	}

}