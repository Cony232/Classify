package chi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import comm.String2Txt;
import comm.Txt2String;

public class Funcation_DF {
	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
		"history","military","regimen","society","sports","story","teach","travel","world"};
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

	public static HashMap<String, int[]> calculate_B(List<String> txtList,HashMap<String, int[]> map,int i) {  
		for (String string : txtList) {
			String[] termlist=string.split(":");
			int[] num=new int[18];
			if (!map.containsKey(termlist[0])) {
				num[i-1]=Integer.parseInt(termlist[1]);
				map.put(termlist[0],num);
			} else {
				int[] num2= map.get(termlist[0]);
				num2[i-1]=Integer.parseInt(termlist[1])+num2[i-1];
				map.put(termlist[0], num2);
			}
		}
		return map;	
	}

	public static List<String> Map2List1(HashMap map){
		Iterator iterator = map.entrySet().iterator();
		List<String> list=new ArrayList<String>();
		while(iterator.hasNext()){
			while(iterator.hasNext()){
				Entry entry = (Entry) iterator.next(); 
				String str=entry.getKey()+":"+entry.getValue();
				list.add(str);      
			} 		
		}
		return list;
	}

	public static List<String> Map2List2(HashMap map){
		Iterator iterator = map.entrySet().iterator();
		List<String> list=new ArrayList<String>();
		while(iterator.hasNext()){
			while(iterator.hasNext()){
				Entry entry = (Entry) iterator.next(); 
				int[] num=(int[]) entry.getValue();
				String value=Arrays.toString(num);
				value=value.replaceAll(" ","");
				String str=entry.getKey()+":"+value.substring(1, value.length()-1);
				list.add(str);     
			} 		
		}
		return list;
	}

	public static void main(String[] args)  {
		//		String a="a a";
		//		String b="a c d";
		//		List<String> list=new ArrayList<String>();
		//		list.add(a);
		//		list.add(b);
		//		HashMap<String, Integer> temp=df(list);
		//		List<String> l=Map2List(temp);
		//		for (String string : l) {
		//			System.out.println(string);
		//		}
		//		Iterator iterator = temp.entrySet().iterator();
		//		while(iterator.hasNext()){
		//			Entry entry = (Entry) iterator.next();  
		//			System.out.println(entry.getKey()+":"+entry.getValue());      // ��ȡkey
		//		
		//		} 
		//		String a="a:1";
		//		String b="a:2";
		//		String c="b:2";
		//		String d="c:2";
		//		List<String> list1=new ArrayList<String>();
		//		list1.add(a);
		//		list1.add(b);
		//		list1.add(c);
		//		HashMap<String, int[]> map=new HashMap<String, int[]>();
		//		//HashMap<String, Integer[]> map=new HashMap<String, Integer[]>();
		//		map=calculate_B(list1,map,1);
		//		List<String> l=Map2List2(map);
		//		for (String string : l) {
		//			System.out.println(string);
		//		}
		//		Iterator iterator = map.entrySet().iterator();
		//		while(iterator.hasNext()){
		//			Entry entry = (Entry) iterator.next();  
		//			int[] num=(int[]) entry.getValue();
		//			//String str=String.valueOf(entry.getValue());
		//			System.out.println(entry.getKey()+":"+Arrays.toString(num));      // ��ȡkey
		//		
		//		}
		//		System.out.println("-----------------------");
		//		List<String> list2=new ArrayList<String>();
		//		list2.add(a);
		//		list2.add(d);
		//		map=calculate_B(list2,map,2);
		//		Iterator iterator2 = map.entrySet().iterator();
		//		while(iterator2.hasNext()){
		//			Entry entry = (Entry) iterator2.next();  
		//			int[] num=(int[]) entry.getValue();
		//			//String str=String.valueOf(entry.getValue());
		//			System.out.println(entry.getKey()+":"+Arrays.toString(num));      // ��ȡkey
		//		
		//		}


		//		List<String> babyList=Txt2String.readFileByLines("E:\\Classify\\data_jieba_remove\\sports.txt");
		//		HashMap<String, Integer> temp=df(babyList);
		//		List list1=Map2List1(temp);
		//		String2Txt.writeFileByLines("E:\\Classify\\jieba_df\\sports.txt", list1);
		List<List<String>> txtList=new ArrayList<List<String>>();
		HashMap<String, int[]> map=new HashMap<String, int[]>();
		for (int i = 0; i < classTitle.length; i++) {
			List<String> list=Txt2String.readFileByLines("E:\\ceping\\data_jieba_remove\\"+classTitle[i]+".txt");
			HashMap<String, Integer> temp=df(list);
			List list1=Map2List1(temp);
			String2Txt.writeFileByLines("E:\\ceping\\jieba_df\\"+classTitle[i]+".txt", list1);
		}
		for (int i = 0; i < classTitle.length; i++) {
			List<String> list2=Txt2String.readFileByLines("E:\\ceping\\jieba_df\\"+classTitle[i]+".txt");
			txtList.add(list2);
		}
		for (int j = 0; j < txtList.size(); j++) {
			map=calculate_B(txtList.get(j), map, j+1);
		}
		List<String> endlist=Map2List2(map);
		String2Txt.writeFileByLines("E:\\ceping\\jieba_B\\B.txt", endlist);
	}
}
