package cq.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExcelUtils {
    /**
     * 创建excel并填入数据
     * @author LiQuanhui
     * @date 2017年11月24日 下午5:25:13
     * @param tableName 表名
     * @param head 数据头
     * @param body 主体数据
     * @return HSSFWorkbook
     */
    public static HSSFWorkbook expExcel(String tableName, JSONArray head, JSONArray body) {
        List<String> arr =new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(tableName);

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        setBorderStyle(cellStyle, BorderStyle.THIN);
        cellStyle.setFont(setFontStyle(workbook, "黑体", (short) 14));
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < head.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(head.getString(i));
            cell.setCellStyle(cellStyle);
            arr.add(head.getString(i));
        }
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        setBorderStyle(cellStyle2, BorderStyle.THIN);
        cellStyle2.setFont(setFontStyle(workbook, "宋体", (short) 12));
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0, isize = body.size(); i < isize; i++) {
            row = sheet.createRow(i + 1);
            JSONObject stuInfo = body.getJSONObject(i);
            for (int j = 0, jsize = stuInfo.size(); j < jsize; j++) {
                cell = row.createCell(j);
                cell.setCellValue(stuInfo.getString(arr.get(j)));
                cell.setCellStyle(cellStyle2);
            }
        }
        for (int i = 0, isize = head.size(); i < isize; i++) {
            sheet.autoSizeColumn(i);
        }
        return workbook;
    }

    /**
     * 在已有的Excel表上追加数据
     * @param file 文件名
     * @param head
     * @param body
     * @return
     */
    public static HSSFWorkbook expExcel(File file, JSONArray head, JSONArray body) throws IOException {
        List<String> arr =new ArrayList<>();
        FileInputStream fileInputStream=new FileInputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row =null;
        HSSFCell cell = null;

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        setBorderStyle(cellStyle, BorderStyle.THIN);
        cellStyle.setFont(setFontStyle(workbook, "黑体", (short) 14));
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < head.size(); i++) {
            arr.add(head.getString(i));
        }
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        setBorderStyle(cellStyle2, BorderStyle.THIN);
        cellStyle2.setFont(setFontStyle(workbook, "宋体", (short) 12));
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);

        int lastRowNum=sheet.getLastRowNum();
        for (int i = 0, isize = body.size(); i < isize; i++) {
            row = sheet.createRow((short)(lastRowNum+1+i));
            JSONObject stuInfo = body.getJSONObject(i);
            for (int j = 0, jsize = stuInfo.size(); j < jsize; j++) {
                cell = row.createCell(j);
                cell.setCellValue(stuInfo.getString(arr.get(j)));
                cell.setCellStyle(cellStyle2);
            }
        }
        for (int i = 0, isize = head.size(); i < isize; i++) {
            sheet.autoSizeColumn(i);
        }
        return workbook;
    }

    /**
     * 文件输出
     * @author LiQuanhui
     * @date 2017年11月24日 下午5:26:23
     * @param workbook 填充好的workbook
     * @param path 存放的位置
     */
    public static void outFile(HSSFWorkbook workbook,String path) {
        OutputStream os=null;
        try {
            os = new FileOutputStream(new File(path));
            workbook.write(os);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置字体样式
     * @author LiQuanhui
     * @date 2017年11月24日 下午3:27:03
     * @param workbook 工作簿
     * @param name 字体类型
     * @param height 字体大小
     * @return HSSFFont
     */
    private static HSSFFont setFontStyle(HSSFWorkbook workbook, String name, short height) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(height);
        font.setFontName(name);
        return font;
    }

    /**
     * 设置单元格样式
     * @author LiQuanhui
     * @date 2017年11月24日 下午3:26:24
     * @param cellStyle 单元格样式
     * @param border border样式
     */
    private static void setBorderStyle(HSSFCellStyle cellStyle, BorderStyle border) {
        cellStyle.setBorderBottom(border); // 下边框
        cellStyle.setBorderLeft(border);// 左边框
        cellStyle.setBorderTop(border);// 上边框
        cellStyle.setBorderRight(border);// 右边框
    }
}