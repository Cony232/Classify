package comm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Txt2String {
	public static List<String> readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		List<String> txtList=new ArrayList<String>();
		try {
			// InputStreamReader read = new InputStreamReader(new FileInputStream(file),"gbk"); 
			//以行为单位读取文件内容，一次读一行
		    reader = new BufferedReader(new FileReader(file));
			//reader = new BufferedReader(read);
			String tempString = null;
			//一次读一行，读入null时文件结束
			while ((tempString = reader.readLine()) != null) {
				txtList.add(tempString);
			}
		
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		System.out.println("结束读取");
		return txtList;
	}
	
//	public static void main(String[] args){
//		List<String> txtList=readFileByLines("E:/classify/test.txt");
//		for (String string : txtList) {
//			System.out.println(string);
//		}
//	}
}


