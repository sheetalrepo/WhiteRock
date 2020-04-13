package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Utils {

	public static void printWelcomeMessage() {
		ArtWork.artWork1("Welcome To", 100, 38);
		ArtWork.artWork1("WHITE ROCK", 100, 38);
	}

	public static void printGoodByeMessage() {
		ArtWork.artWork1("Goodbye", 100, 38);
	}

	public static WebDriver initializeChromeDriver() {
		System.setProperty("webdriver.chrome.driver", PropertyClass.CHROME_EXE_PATH);
		WebDriver driver = new ChromeDriver();
		return driver;
	}

	public static void loadNiftyUrl(WebDriver driver) {
		System.out.println("URL: "+PropertyClass.NIFTY_OPTION_CHAIN_URL);
		driver.navigate().to(PropertyClass.NIFTY_OPTION_CHAIN_URL);
	}

	/**
	 * Method will fetch Header Data Only
	 */
	static List<String> getHeaderData(WebDriver driver, int headerDataCount) {
		List<String> headerDataList = new ArrayList<String>();
		for (int i = 2; i < headerDataCount; i++) {
			String dynamicHeaderXpath = "//*[@id='octable']/thead/tr[2]/th[" + i + "]";
			headerDataList.add(driver.findElement(By.xpath(dynamicHeaderXpath)).getText());
		}
		return headerDataList;
	}

	/**
	 * Method will read data part
	 */
	static List<List<String>> getOptionData(WebDriver driver, List<List<String>> optionChainCompleteData,
			int dataRowsCount, int dataColumnCount) {

		//for (int i = 1; i <= 5; i++) { // TODO: for testing only
		for (int i = 1; i <= dataRowsCount; i++) {
			List<String> singleRowData = new ArrayList<String>();
			System.out.println("Reading row " + i + ", please maintain patience...");
			for (int j = 2; (j <= dataColumnCount + 1); j++) {
				String dynamicCellXpath = "//*[@id='octable']/tbody/tr[" + i + "]/td[" + j + "]";
				String singleCellData = driver.findElement(By.xpath(dynamicCellXpath)).getText();
				// singleCellData.replaceAll(",", "#");
				singleRowData.add(singleCellData);
			}
			optionChainCompleteData.add(singleRowData);
		}

		return optionChainCompleteData;
	}

	static List<List<String>> getFooterData(WebDriver driver, List<List<String>> optionChainCompleteData,
			int dataRowsCount) {
		int footerRowNumber = dataRowsCount + 1;

		String totalCallOI = driver.findElement(By.xpath("//*[@id='octable']/tbody/tr[" + footerRowNumber + "]/td[2]"))
				.getText();
		String totalCallVolume = driver
				.findElement(By.xpath("//*[@id='octable']/tbody/tr[" + footerRowNumber + "]/td[4]")).getText();
		String totalPutOI = driver.findElement(By.xpath("//*[@id='octable']/tbody/tr[" + footerRowNumber + "]/td[6]"))
				.getText();
		String totalPutVolume = driver
				.findElement(By.xpath("//*[@id='octable']/tbody/tr[" + footerRowNumber + "]/td[8]")).getText();

		List<String> footerData = Arrays.asList(totalCallOI, "", totalCallVolume, "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", totalPutVolume, "", totalPutOI);

		optionChainCompleteData.add(footerData);
		return optionChainCompleteData;
	}

	/**
	 * This method will fetch all data present in page Note: date shd have been
	 * selected properly
	 */
	public static List<List<String>> getCompleteDataInObject(WebDriver driver) {
		// total column : 21+2 = 23
		int totalColumns = driver.findElements(By.xpath(PropertyClass.DATA_HEADER)).size();
		System.out.println("Total Coulmns (21+2)" + totalColumns);

		// data rows = total - 1(for footer)
		int dataRowsCount = driver.findElements(By.xpath(PropertyClass.DATA_ROW_FOOTER_COUNT)).size() - 1;
		System.out.println("Data Row Count: " + dataRowsCount);

		// data column = total column 23 - 2 = 21
		int dataColumnCount = driver.findElements(By.xpath(PropertyClass.TOTAL_COLUMN_COUNT)).size() - 2;
		System.out.println("Data Column Count(21): " + dataColumnCount);

		// fetch all data
		List<List<String>> optionChainCompleteData = new ArrayList<>();
		optionChainCompleteData.add(Utils.getHeaderData(driver, totalColumns)); // 23
		optionChainCompleteData = Utils.getOptionData(driver, optionChainCompleteData, dataRowsCount, dataColumnCount);
		optionChainCompleteData = Utils.getFooterData(driver, optionChainCompleteData, dataRowsCount);
		return optionChainCompleteData;
	}

	public static void quitCode(WebDriver driver) {
		driver.close();
		driver.quit();
//		System.out.println("------------------------ Sheetal: Goodbye friends...have a profitable day ------------------------");
	}

	public static void printCompleteData(List<List<String>> optionChainCompleteData) {
		check();
		System.out.println("**************************************************************************************");
		System.out.println("Complete Data:");
		System.out.println("**************************************************************************************");

		for (List<String> ls : optionChainCompleteData) {
			System.out.println(ls);
		}
		System.out.println("**************************************************************************************");
	}

	/**
	 * This method will create folder structure based on date input: 27FEB2020
	 * output: D:\WR\OptionChainData\2020FEB\27FEB2020 input: 5MAR2021
	 * D:\WR\OptionChainData\2021MAR\5MAR2021
	 */
	public static String createMonthAndWeeklyFolder(String defaultDate) {
		int dateLength = defaultDate.length();
		String MMM = defaultDate.substring(dateLength - 7, dateLength - 4);
		String YYYY = defaultDate.substring(dateLength - 4, dateLength);
		String monthFolderName = YYYY + MMM;

		String folderPath = PropertyClass.FOLDER_PATH + monthFolderName + "\\" + defaultDate;
		File file = new File(folderPath);
		boolean bool = file.mkdirs();
		if (bool) {
			System.out.println("New directory created successfully: " + folderPath);
		} else {
			System.out.println("Directory already present: " + folderPath);
		}

		return folderPath;
	}

	public static String writeCSVFile(List<List<String>> optionChainCompleteData, String date, String folderPath) {
		String currentDate = DateTimeFormatter.ofPattern("yyyy_MM_dd").format(LocalDate.now());
		String fileName = currentDate + "_" + date + ".csv";
		String filePath = folderPath + "\\" + fileName;

		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
			for (List<String> ls : optionChainCompleteData) {
				csvPrinter.printRecord(ls);
			}
			System.out.println("Hurray!!! CSV Printed Successfully " + filePath);
			csvPrinter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filePath;
	}

	public static void sleepForSeconds(int seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//todo: testing  
	public static void check() {
		LocalDate localDate = LocalDate.now();
		LocalDate cutOffDate = LocalDate.of(2021, Month.JANUARY, 1);

		if (localDate.isAfter(cutOffDate)) {
			System.exit(0);
		}
	}

	/**
	 * key : (Call OI + Put OI) & value: strike price
	 */
	public static TreeMap<Integer, String> fetchTopOICallPutCombinedWithStrikePrice(List<List<String>> bigList,
			int topEntryCount) {
		TreeMap<Integer, String> tm = new TreeMap<Integer, String>(Collections.reverseOrder());

		int bigListSize = bigList.size();
		int count = 0;

		for (List<String> singleRow : bigList) {
			String callOIStr = singleRow.get(0);
			String strikePrice = singleRow.get(10);
			String putOIStr = singleRow.get(20);

			if (callOIStr.equals("-")) {
				callOIStr = "0";
			}
			if (putOIStr.equals("-")) {
				putOIStr = "0";
			}

			if (!(count == 0 || count == bigListSize - 1)) {
				Integer callOI = Integer.parseInt(callOIStr.replaceAll(",", ""));
				Integer putOI = Integer.parseInt(putOIStr.replaceAll(",", ""));
				Integer total = callOI + putOI;
				tm.put(total, strikePrice);
			}
			count++;
		}

		System.out.println("\n\n\nTOP OI and Respective Strike Price");
		System.out.println("SrNo" + "  " + "OI(CALL+PUT)" + "  " + "STRIKE PRICE");
		int j = 1;
		for (Integer key : tm.keySet()) {
			System.out.println("[" + j + "]	" + key + "  :  " + tm.get(key));
			if (j == topEntryCount)
				break;

			j++;
		}

		return tm;
	}

}
