package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParserUtil {
	private static String ENCODE = "GBK";

	private static void message(String szMsg) {
		try {
			System.out.println(new String(szMsg.getBytes(ENCODE), System
					.getProperty("file.encoding")));
		} catch (Exception e) {
		}
	}

	public static String openFile(String szFileName) {
		try {
			BufferedReader bis = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(szFileName)), ENCODE));
			String szContent = "";
			String szTemp;

			while ((szTemp = bis.readLine()) != null) {
				szContent += szTemp + "\n";
			}
			bis.close();
			return szContent;
		} catch (Exception e) {
			return "";
		}
	}

	public static String getBookName(Parser parse) {
		String BookName = "";
		AndFilter filter = new AndFilter(new TagNameFilter("span"),
				new HasAttributeFilter("property", "v:itemreviewed"));
		NodeList nodes;
		try {
			nodes = parse.parse(filter);
			for (NodeIterator i = nodes.elements(); i.hasMoreNodes();) {
				Node node = i.nextNode();
				BookName = node.toPlainTextString();

			}
		} catch (ParserException e) {
			e.printStackTrace();

		}

		return BookName;

	}

	static public ArrayList<String> getData(Parser parser)
			throws ParserException {

		AndFilter filter = new AndFilter(new TagNameFilter("div"),
				new HasAttributeFilter("id", "info"));
		NodeList nodes = parser.parse(filter);
		int count = 1;
		ArrayList<String> al = new ArrayList<String>();

		for (NodeIterator i = nodes.elements(); i.hasMoreNodes();) {
			Node node = i.nextNode();
			count = 1;
			for (NodeIterator j = node.getChildren().elements(); j
					.hasMoreNodes();) {
				count++;
				Node nodes1 = j.nextNode();
				if (count == 3 || count == 6 || count == 7 || count == 10
						|| count == 11 || count == 14 || count == 15
						|| count == 18 || count == 21 || count == 22
						|| count == 25 || count == 26 || count == 29
						|| count == 30 || count == 33 || count == 34
						|| count == 37 || count == 39 || count == 42
						|| count == 43) {

					al.add(nodes1.toPlainTextString());
				}
			}

		}
		return al;
	}

	static public Parser getParser(String isbn) throws ParserException,
			MalformedURLException, IOException {

		return new Parser((HttpURLConnection) (new URL(
				"http://book.douban.com/isbn/" + isbn)).openConnection());
	}

	public static void main(String[] args) {
		System.out.println("args:"+args[0]);

		ArrayList<ArrayList<String>> all = new ArrayList<ArrayList<String>>();

		Scanner in = new Scanner(System.in);

		ExcelOperater eo = new ExcelOperater(args[0]);
		String isbn = "";

		while (!isbn.equals("end")) {
			try {
				// 7111165055
				System.out.println("Input In ISBN:");
				isbn = in.next();
				if (isbn.equals("end")) {
					System.out.println(all.size() + "size===========");
					eo.Insert(all);
					break;
				}

				ArrayList<String> al = getData(getParser(isbn));
				al.add(0, getBookName(getParser(isbn)));
				all.add(al);

			} catch (Exception e) {
				System.out.println("Exception:" + e);
			}

		}
		ExcelOperater.closeEO();
	}
}