package cq.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈强
 * @version 1.0
 * @date 2016年10月17日 下午2:19:39
 */
public class ReadExcel {
    private static final Logger logger = LoggerFactory.getLogger(ReadExcel.class);
    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;

    //文件名
    private String fileName;

    private Workbook wb;

    //构造方法
    public ReadExcel(File file, boolean isExcel2003) {
        FileInputStream in=null;
        try {
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                in=new FileInputStream(file);
                wb = new HSSFWorkbook(in);
                fileName = file.getName();
            } else {// 当excel是2007时,创建excel2007
                in=new FileInputStream(file);
                wb = new XSSFWorkbook(in);
                fileName = file.getName();
            }
        } catch (IOException e) {
            logger.error("文件位置不正确!" );
            logger.error(e.getLocalizedMessage());
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage());
                }
            }
        }
    }


    //获取总行数
    public int getTotalRows() {
        return totalRows;
    }

    //获取总列数
    public int getTotalCells() {
        return totalCells;
    }

    //获取错误信息
    public String getErrorInfo() {
        return errorMsg;
    }

    //获取总表数
    public int getSheetNums() {
        if (wb != null) {
            return wb.getNumberOfSheets();
        } else {
            return 0;
        }
    }

    /**
     * 根据excel里面的内容读取客户信息
     *
     * @return
     * @throws IOException
     */
    public List<JSONObject> readExcel(int sheetindex, int headindex) throws Exception {
        List<JSONObject> objectList = null;

        objectList = readExcelValue(wb, sheetindex, headindex);// 读取Excel里面客户的信息
        return objectList;
    }

    /**
     * 读取Excel里面客户的信息
     *
     * @param wb
     * @param sheetindex sheet的索引
     * @param headindex  标题行索引，从0开始计数
     * @return 数据从0开始计数，0位表名，1开始为第一列数据
     */
    private List<JSONObject> readExcelValue(Workbook wb, int sheetindex, int headindex) throws Exception{
        // 得到shell
        Sheet sheet = wb.getSheetAt(sheetindex);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if(this.totalRows==0){
            throw  new Exception("EXCEL【"+fileName+"】的表【"+sheet.getSheetName()+"】为空");
        }
        // 得到Excel的列数(前提是有行数)
        if (totalRows > headindex && sheet.getRow(headindex) != null) {
            this.totalCells = sheet.getRow(headindex).getPhysicalNumberOfCells();
        }
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        // 循环Excel行数
        for (int r = headindex + 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("0", sheet.getSheetName());
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        String value = String.valueOf(cell.getNumericCellValue());
                        jsonObject.put(String.valueOf(c + 1), value.substring(0, value.length() - 2 > 0 ? value.length() - 2 : 1));//名称
                    } else {
                        cell.setCellType(CellType.STRING);
                        jsonObject.put(String.valueOf(c + 1), cell.getStringCellValue());
                    }
                }
            }
            // 添加到list
            jsonList.add(jsonObject);
        }
        return jsonList;
    }

    public void close(){
        if(wb!=null){
            try {
                wb.close();
            } catch (IOException e) {
                logger.error("关闭POI异常");
            }
        }
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}