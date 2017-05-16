package chi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import comm.Calculte_TFDF;
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



	public static void main(String[] args)  {

		List<List<String>> txtList=new ArrayList<List<String>>();
	    HashMap<String, int[]> map=new HashMap<String, int[]>();
//		//计算df

//		for (int i = 0; i < classTitle.length; i++) {
//			List<String> list=Txt2String.readFileByLines("E:\\ceping\\data_jieba_remove\\"+classTitle[i]+".txt");
//			HashMap<String, Integer> temp=df(list);
//			List list1=Calculte_TFDF.Map2List1(temp);
//			String2Txt.writeFileByLines("E:\\ceping\\jieba_df\\"+classTitle[i]+".txt", list1);
//		}
		
		//计算df矩阵
		for (int i = 0; i < classTitle.length; i++) {
			List<String> list2=Txt2String.readFileByLines("E:\\ceping\\jieba_df\\"+classTitle[i]+".txt");
			txtList.add(list2);
		}
		for (int j = 0; j < txtList.size(); j++) {
			map=Calculte_TFDF.calculate_juzhen(txtList.get(j), map, j+1);
		}
		List<String> endlist=Calculte_TFDF.Map2List2(map);
		String2Txt.writeFileByLines("E:\\ceping\\jieba_df_juzhen\\juzhen.txt", endlist);
	}
}