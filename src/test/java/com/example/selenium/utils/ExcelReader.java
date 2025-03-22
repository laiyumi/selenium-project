package com.example.selenium.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {
    public static List<String[]> readData(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = sheet.iterator();

            iterator.next(); // Skip header row

            while (iterator.hasNext()) {
                Row row = iterator.next();
                int columnCount = row.getLastCellNum();
                String[] rowData = new String[columnCount];
                boolean isEmptyRow = true;

                for (int i = 0; i < columnCount; i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        isEmptyRow = false;

                        switch (cell.getCellType()) {
                            case STRING:
                                rowData[i] = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    rowData[i] = cell.getDateCellValue().toString();
                                } else {
                                    rowData[i] = String.valueOf(cell.getNumericCellValue());
                                }
                                break;
                            case BOOLEAN:
                                rowData[i] = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                rowData[i] = cell.getCellFormula();
                                break;
                            default:
                                rowData[i] = "";
                        }
                    } else {
                        rowData[i] = "";
                    }

                }

                if (!isEmptyRow) {
                    data.add(rowData);
                }            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
