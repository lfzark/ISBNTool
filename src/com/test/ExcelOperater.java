package com.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelOperater {

	static WritableWorkbook book;
	WritableSheet sheet;
	Workbook wb = null; // 创建一个workbook对象/

	ExcelOperater(String Path) {

		try {

			wb = Workbook.getWorkbook(new File(Path));
			book = Workbook.createWorkbook(
					new File("E:/BookInfo" + Math.random() + ".xls"), wb);
	 
			sheet = book.getSheet(0);

		} catch (BiffException e) {

			e.printStackTrace();
		} catch (IOException e) {
	 
			e.printStackTrace();
		}


	}

	void Excel() {

		try {
			wb = Workbook.getWorkbook(new File("E:/BookInfo.xls"));
			book = Workbook.createWorkbook(new File("E:/BookInfo.xls"), wb);
			sheet = book.getSheet(0);

		} catch (BiffException e) {
		 
			e.printStackTrace();
		} catch (IOException e) {
		 
			e.printStackTrace();
		}



	}

	public void Insert(ArrayList<ArrayList<String>> all) {

		try {

			int count = all.size();

			for (int i = 0; i < count; i++) {
				ArrayList<String> al = all.get(i);
				for (int j = 0; j < al.size(); j++) {

					String tempData = al.get(j);
				
					if (tempData == null) {
						tempData = "";
					}
					Label label = new Label(j + 1, i + 1, tempData);
				 
					sheet.addCell(label);
				}

			}

			book.write();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void InsertData(ArrayList<String> al, int row) {

		System.out.println(al.size() + "========size=========");
		try {

			int count = al.size();

			for (int i = 0; i < count; i++) {
				String tempData = al.get(i);
			 
				if (tempData == null) {
					tempData = "";
				}
				Label label = new Label(i, row, tempData);
				 
				sheet.addCell(label);
				label = new Label(i, row + 1, tempData);
			
				sheet.addCell(label);
			}

			book.write();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	static public void closeEO() {
		try {
			book.close();
		} catch (WriteException e) {
	 
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}


}