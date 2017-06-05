package fenci;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comm.CommonCal;
import comm.String2Txt;
import comm.Txt2String;

public class StopWord {
	public static List<String> Sort(List<String> maxList){
		Map<String,Double> maxMap=new HashMap<String,Double>();
		List<String> endList=new ArrayList<String>();
		for (String string : maxList) {
			int i=string.indexOf("	");
			String word=string.substring(0, i);
			String value=string.substring(i+1);
			double num=Double.valueOf(value).doubleValue();
			maxMap.put(word, num);
		}
		List<Map.Entry<String, Double>> infoIds = new ArrayList<Map.Entry<String, Double>>(maxMap.entrySet());
		//排序
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				return o2.getKey().length()<=o1.getKey().length()?1:-1;
			}
		});
		for(Map.Entry<String,Double> mapping:infoIds){   
			endList.add(mapping.getKey());   
		}   
		return endList;
	}
	
	public static void main(String[] args) {
		List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\vocab.txt");
		List<String> endList=Sort(txtList);
		String2Txt.writeFileByLines("E:\\ceping\\vocab4.txt", endList);
	}
}
