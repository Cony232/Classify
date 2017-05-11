package tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Funcation_TF { 
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


	public static void main(String[] args) {
		String a="我们 中国";
		String b="我们 中国 我们";
		List<String> list=new ArrayList<String>();
		list.add(a);
		list.add(b);
		HashMap<String, Integer> temp=tf(list);
		Iterator iterator = temp.entrySet().iterator();
		while(iterator.hasNext()){
			Entry entry = (Entry) iterator.next();  
			System.out.println(entry.getKey()+":"+entry.getValue());      // 获取key
		} 

	}
}
