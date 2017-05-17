package fenci;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comm.String2Txt;
import comm.Txt2String;



public class RemoveStopWord {

	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
			"history","military","regimen","society","sports","story","tech","travel","world"};
	
	public static boolean isNumeric(String str){ 
		Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*"); 
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false; 
		} 
		return true; 
	}
	
	public static List<String> removeWord(List<String> endList) {
		List<String> endList2=new ArrayList<String>();//分词和去停用词过后的
		System.out.println("开始读取停用词表");
		BufferedReader StopWordFileBr;
		try {
			StopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File("file/stop.txt")), "utf-8"));
			System.out.println("结束读取停用词表");
			Set<String> stopWordSet = new HashSet<String>();
			stopWordSet = new HashSet<String>();
			String stopWord = null;
			for (; (stopWord = StopWordFileBr.readLine()) != null;) {
				stopWordSet.add(stopWord);
			}
			for(int i=0;i<endList.size();i++){
				String[] termlist=endList.get(i).split(" ");
				// 除去停用词
				// System.out.println(Arrays.asList(termlist));  
				for (int j = 0; j < termlist.length; j++) {
					//System.out.println(j+":"+termlist[j]);
					if (stopWordSet.contains(termlist[j])||isNumeric(termlist[j])) {
						termlist[j]="";
						//System.out.println(j+":"+termlist[j]);
					}	
				}	
				StringBuffer sb = new StringBuffer();
				for(int n = 0; n < termlist.length; n++){
					sb. append(termlist[n]);
					if(!termlist[n].equals("")){
						sb. append(" ");
					}	
				}
				String s = sb.toString();
				endList2.add(s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(s);

		//for (String string : endList) {
		//	System.out.println(string);
		//}
		//for (String string : endList2) {
		//	System.out.println(string);
		//}
		System.out.println("去停用词结束");
		return endList2;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		for (int i = 0; i < classTitle.length; i++) {
//			List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\data_jieba\\"+classTitle[i]+".txt");
//			List<String> endList=removeWord(txtList);
//			String2Txt.writeFileByLines("E:\\ceping\\data_jieba_remove\\"+classTitle[i]+".txt", endList);
//			
//		}
		List<String> txtList=Txt2String.readFileByLines("E:\\work\\Classify\\test\\game.txt");
		List<String> endList=removeWord(txtList);
		String2Txt.writeFileByLines("E:\\work\\Classify\\test\\game.txt", endList);
		
	}

}
