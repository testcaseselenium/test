package com.fremap.policy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	//Metodo general para que nos lea todos los datos del Excel
public void readExcel( String filepath, String sheetName) throws IOException {
		
		File file = new File(filepath); //Creamos nuestro archivo
		
		FileInputStream inputStream = new FileInputStream(file);
		
		XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream); //Creamos donde guardamos el libro excel
		
		XSSFSheet newSheet = newWorkbook.getSheet(sheetName); //Hoja del Excel y el nombre de la hoja
		
		//Cuantas filas de datos tiene el excel
		int rowCount = newSheet.getLastRowNum() + newSheet.getFirstRowNum();
		
		for (int i = 0; i <= rowCount; i++) { //Para que lea filas
			XSSFRow row = newSheet.getRow(i);
			
			for (int j = 0; j < row.getLastCellNum(); j++) {
				System.out.println(row.getCell(j).getStringCellValue() + "||");
			}
		}
	}
	

//para leer una celda específica
	public String getCellValue(String filepath, String sheetName, int rowNumber, int cellNumber) throws IOException {
		
		File file = new File(filepath); // Creamos nuestro archivo
		
		FileInputStream inputStream = new FileInputStream(file);
		
		XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream); //Creamos donde guardamos el libro excel
		
		XSSFSheet newSheet = newWorkbook.getSheet(sheetName);
		
		XSSFRow row = newSheet.getRow(rowNumber);
		
		XSSFCell column = row.getCell(cellNumber);
		
		DataFormatter formatter = new DataFormatter();
	      
		String formattedValue = formatter.formatCellValue(column);
	       
	    return formattedValue;

		
		
	}
}
	


