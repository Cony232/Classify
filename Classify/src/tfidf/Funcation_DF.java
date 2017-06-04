package tfidf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import comm.CommonCal;
import comm.String2Txt;
import comm.Txt2String;

public class Funcation_DF {
	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
		"history","military","regimen","society","sports","story","tech","travel","world"};
	public static HashMap<String, Integer> df(List<String> txtList) {  
		HashMap<String, Integer>  hashMap= new HashMap<String, Integer>();
		for (String string : txtList) {
			String[] termlist=string.split(" ");
			List list=Arrays.asList(termlist);
			Set set = new HashSet(list);
			termlist=(String [])set.toArray(new String[0]);
			for (int i = 0; i < termlist.length; i++) {
				if (!hashMap.containsKey(termlist[i])) {
					hashMap.put(termlist[i], new Integer(1));
				} else {
					int k = hashMap.get(termlist[i]).intValue() + 1;
					hashMap.put(termlist[i], new Integer(k));
				}
			}
		}
		return hashMap;	
	}

	public static List<String>  string2tfjuzhen(List<List<String>> txtList){
		Map<String, int[]> map=new HashMap<String, int[]>();
		for (int i = 0; i < txtList.size(); i++) {
			HashMap<String, Integer> temp=df(txtList.get(i));
			List<String> dfList=Funcation_TF.Map2List1(temp);
			map=CommonCal.calculate_juzhen(dfList, map, i); 
		} 
		List<String> endlist=CommonCal.Map2List2(map);
		String2Txt.writeFileByLines("E:\\ceping\\jieba\\bayes_df\\incre\\df_juzhen.txt", endlist);
		return endlist;
	}

	public static void main(String[] args)  {


//		//计算df

//		for (int i = 0; i < 18; i++) {
//			List<String> list=Txt2String.readFileByLines("E:\\ceping\\jieba_end_train\\jieba_train_test\\"+classTitle[i]+".txt");
//			HashMap<String, Integer> temp=df(list);
//			List list1=Funcation_TF.Map2List1(temp);
//			String2Txt.writeFileByLines("E:\\ceping\\jieba_end_train\\df\\"+classTitle[i]+".txt", list1);
//		}
//		
		//计算df矩阵
		List<List<String>> txtList=new ArrayList<List<String>>();
	    Map<String, int[]> map=new HashMap<String, int[]>();
		for (int i = 0; i < 18; i++) {
			List<String> list2=Txt2String.readFileByLines("E:\\ceping\\jieba_end_train\\df\\"+classTitle[i]+".txt");
			txtList.add(list2);
		}
		for (int j = 0; j < txtList.size(); j++) {
		
			map=CommonCal.calculate_juzhen(txtList.get(j), map, j);
		}
		List<String> endlist=CommonCal.Map2List2(map);
		String2Txt.writeFileByLines("E:\\ceping\\jieba_end_train\\df_juzhen\\juzhen.txt", endlist);
	    
		
		//拆分df_juzhen
//	    List<String> df_juzhen=Txt2String.readFileByLines("E:\\ceping\\jieba_df_juzhen\\juzhen.txt");
//	    Map<String,String> map1=CommonCal.String2Map(df_juzhen);
//	    for (int i = 0; i < classTitle.length; i++) {
//		List<String> list2=Txt2String.readFileByLines("E:\\ceping\\jieba_df\\"+classTitle[i]+".txt");
//		Map<String,String> map2=CommonCal.chaifen_juzhen(list2, map1);
//		List list=CommonCal.Map2List1(map2);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba_df_juzhen\\chaifen\\"+classTitle[i]+".txt", list);
//	}
	    
	}
}
