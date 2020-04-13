package com;

import java.io.File;

public class Play {

	
	public static String createMonthyFolder(String defaultDate) {
		int dateLength = defaultDate.length();
		String MMMYYYY = defaultDate.substring(dateLength - 7, dateLength);
		String MMM = defaultDate.substring(dateLength - 7, dateLength-4);
		String YYYY = defaultDate.substring(dateLength - 4, dateLength);
		String monthFolderName = YYYY+MMM;
		
		System.out.println("--");
		System.out.println(MMM);
		System.out.println(YYYY);
		System.out.println(monthFolderName);
		
		String folderPath = PropertyClass.FOLDER_PATH + MMMYYYY+"\\"+defaultDate;
		//D:\WR\OptionChainData\FEB2020\13FEB2020
		
		File file = new File(folderPath);
		boolean bool = file.mkdirs();
		if (bool) {
			System.out.println("New directory created successfully: " + folderPath);
		} else {
			System.out.println("Directory already present: " + folderPath);
		}

		return folderPath;
	}
	
	public static void main(String[] args) {
		
		createMonthyFolder("MAR2021");
		
	}

}
