package com.cognizant;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ReadExcel {
    public static String getData(String sheetName,int rowNumber) throws IOException {
        FileInputStream inputStream = new FileInputStream(sheetName);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(rowNumber);
        Cell cell;
        String str = "";
        Iterator<Cell> cellIterator = row.cellIterator();

        while(cellIterator.hasNext()) {
            cell = cellIterator.next();
            DataFormatter formatter = new DataFormatter();
            String text = formatter.formatCellValue(cell);
            str += text + " ";
        }
        return str.strip();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getData("src/main/java/com/cognizant/Book.xlsx",0));
    }
}
