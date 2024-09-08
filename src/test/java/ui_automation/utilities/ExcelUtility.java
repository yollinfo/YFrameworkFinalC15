package ui_automation.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {
    private static Logger logger = LogManager.getLogger(ExcelUtility.class);

    private static XSSFSheet workSheet;
    private static XSSFWorkbook workBook;
    private static XSSFCell cell;
    private static XSSFRow row;


    /**
     * Based on the path to excell file, and specified sheetName
     * The method will read excell workbook, and will read the specified sheet
     * Then it'll save all information in global static variables
     * @param path
     * @param sheetName
     * @throws Exception
     */
    public static void readExcelFile(String path, String sheetName) throws Exception {
        try {
            // read file as input stream
            FileInputStream fileInputStream = new FileInputStream(path);
            // convert input stream to workbook
            workBook = new XSSFWorkbook(fileInputStream);
            // read the workSheet
            workSheet = workBook.getSheet(sheetName);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw (e);
        }
    }

    /**
     * Once readExcelFile() has been called,
     * getCellData() can return the cellValue based on row and col number
     * @param rowNum row number
     * @param colNum cloumn number
     * @return
     * @throws Exception
     */
    public static Object getCellData(int rowNum, int colNum){
        try{
            cell = workSheet.getRow(rowNum).getCell(colNum);
            return cell.getStringCellValue();
        }catch (Exception IllegalStateException){
            try{
                cell = workSheet.getRow(rowNum).getCell(colNum);
                return cell.getNumericCellValue();
            }
            catch (Exception e){
                cell = workSheet.getRow(rowNum).getCell(colNum);
                return cell.getDateCellValue();
            }
        }
    }

    /**
     *
     * @param path will be excell file path
     * @param value value that will get saved into cell
     * @param rowNum the row number
     * @param colNum the column number
     * @throws Exception
     */
    public static void setCellData(String path, String value,  int rowNum, int colNum) throws Exception {
        try{
            row  = workSheet.getRow(rowNum);
            cell = row.getCell(colNum);
            // if cell is null, create a cell -> write the value
            if (cell == null) {
                cell = row.createCell(colNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }

            // create a outputStream
            FileOutputStream fileOut = new FileOutputStream(path);
            // 1st we need to write the workbook in the path
            workBook.write(fileOut);
            // 2nd flush out -> save
            fileOut.flush();
            // close the fileOutStream
            fileOut.close();
        }catch(Exception e){
            logger.error(e.getMessage());
            throw (e);

        }

    }


    public static void createExcelAndWrite(String fileName, String value){
        workBook = new XSSFWorkbook();
        workSheet = workBook.createSheet("FIRST SHEET");
        row = workSheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue(value);
        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
        {
            workBook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}