package chi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import comm.CommonCal;
import comm.String2Txt;
import comm.Txt2String;

public class Funcation_TF { 
	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
		"history","military","regimen","society","sports","story","tech","travel","world"};
	
	public static HashMap<String, Integer> tf(List<String> txtList) {  
		HashMap<String, Integer>  hashMap= new HashMap<String, Integer>();
		for (String string : txtList) {
			String[] termlist=string.split(" ");
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


	public static List<String> Map2List1(HashMap<String, Integer> map){
		Iterator<Entry<String, Integer>> iterator = map.entrySet().iterator();
		List<String> list=new ArrayList<String>();
		Integer n=0;
		while(iterator.hasNext()){
			while(iterator.hasNext()){
				Entry<String, Integer> entry = (Entry<String, Integer>) iterator.next(); 
				String str=entry.getKey()+":"+entry.getValue();
				n=n+entry.getValue();
				list.add(str);      
			} 		
		}
		list.add(""+n);
		return list;
	}
	
	
	public static void main(String[] args) {
		List<List<String>> txtList=new ArrayList<List<String>>();
	    HashMap<String, int[]> map=new HashMap<String, int[]>();
		//计算每个类的tf和每个类的总词频(num.txt)
//		List<String> numList=new ArrayList<String>();
//		for (int i = 0; i < classTitle.length; i++) {
//			List<String> list=Txt2String.readFileByLines("E:\\ceping\\data_jieba_remove\\"+classTitle[i]+".txt");
//			HashMap<String, Integer> temp=tf(list);
//			List<String> list1=Map2List1(temp);
//			String str=list1.get(list1.size()-1);
//			numList.add(str);
//			list1.remove(list1.size()-1);
//			String2Txt.writeFileByLines("E:\\ceping\\jieba_tf\\"+classTitle[i]+".txt", list1);
//		}
//		String2Txt.writeFileByLines("E:\\ceping\\jieba_c_num\\num.txt", numList);
		
		//计算tf矩阵(tf_all.txt)
		for (int i = 0; i < classTitle.length; i++) {
			List<String> list2=Txt2String.readFileByLines("E:\\ceping\\jieba_tf\\"+classTitle[i]+".txt");
			txtList.add(list2);
		}
		for (int j = 0; j < txtList.size(); j++) {
			map=CommonCal.calculate_juzhen(txtList.get(j), map, j+1);
		}
		List<String> endlist=CommonCal.Map2List2(map);
		String2Txt.writeFileByLines("E:\\ceping\\jieba_tf_juzhen\\juzhen.txt", endlist);
	}
}
