package tfidf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class Funcation_DF {
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
		String a="a a";
		String b="a c d";
		List<String> list=new ArrayList<String>();
		list.add(a);
		list.add(b);
		HashMap<String, Integer> temp=df(list);
		Iterator iterator = temp.entrySet().iterator();
		while(iterator.hasNext()){
			Entry entry = (Entry) iterator.next();  
			System.out.println(entry.getKey()+":"+entry.getValue());      // ªÒ»°key
		
		} 
//		String a="a b a d";
//		String[] termlist=a.split(" ");
//		List list=Arrays.asList(termlist);
//		Set set = new HashSet(list);
//		String [] rid=(String [])set.toArray(new String[0]);
//		for (int i = 0; i < rid.length; i++) {
//			System.out.println(rid[i]);
//		}
		

	}
}
