package comm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CommonCal {

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

	public static HashMap<String, int[]> calculate_juzhen(List<String> txtList,HashMap<String, int[]> map,int i) {  
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
	
	public static Map<String,String> String2Map(List<String> list){
		Map<String,String> map=new HashMap<String,String>();
		for (String string : list) {
			int i=string.indexOf(":");
			String word=string.substring(0,i);
			String tf=string.substring(i+1);
			map.put(word, tf);
		}
		return map;
	}
	
	public static int calculate_sum(int[] array){
		int num=0;
		for (int i = 0; i < array.length; i++) {
			num=num+array[i];
		}
		return num;
	}
	
	
}
