package com;

public class PropertyClass {

	
	//public static String NIFTY_OPTION_CHAIN_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?segmentLink=17&instrument=OPTIDX&symbol=NIFTY";
	public static String NIFTY_OPTION_CHAIN_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?symbolCode=-10006&symbol=NIFTY&symbol=NIFTY&instrument=-&date=-&segmentLink=17&symbolCount=2&segmentLink=17";
	public static String NIFTY_OPTION_CHAIN_MONTHY_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?segmentLink=17&instrument=OPTIDX&symbol=NIFTY&date=";
	//public static String CHROME_EXE_PATH = ".\\src\\main\\resources\\chromedriver.exe";	
	public static String CHROME_EXE_PATH = "C:\\WR\\chromedriver.exe";	
	public static String FOLDER_PATH = "C:\\WR\\OptionChainData\\";
	
	public static String DATA_HEADER = "//*[@id='octable']/thead/tr[2]/th";  //23
	public static String DATA_ROW_FOOTER_COUNT = "//*[@id='octable']/tbody/tr";  // data row + footer (no header)
	public static String TOTAL_COLUMN_COUNT = "//*[@id='octable']/tbody/tr[1]/td"; // 23
	
	public static String DEFAULT_DATE_SELECTED = "//*[@id='date']/option[2]";
	public static int TOP_OI_DATA_COUNT = 5;
}
