package fenci;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comm.CommonCal;
import comm.String2Txt;
import comm.Txt2String;

public class FindOnly {
	
	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
		"history","military","regimen","society","sports","story","tech","travel","world"};
	
	
	public static Map<String,Integer> findOnly(List<String> endList){
		Map<String,Integer> map=new HashMap<String,Integer>();
		for (String string : endList) {
			String[] wordArray=string.split("   ");
			//System.out.println(wordArray[1]);
			 for (int i = 0; i < wordArray.length; i++) {
				 if(wordArray[i].contains("/nr")||wordArray[i].contains("/nz")||wordArray[i].contains("/nt")||wordArray[i].contains("/ns")){
					  int k=wordArray[i].indexOf("/");
					  String s=wordArray[i].substring(0,k);
					 if(map.containsKey(s)){
						 int m=map.get(s)+1;
						 map.put(s,m);
					  }else{
						  map.put(s,1);
					  }
				 }
			}
		}
		return map;
	}
	
	public static  List<String>  removeOneWord(List<String> endList){
		 List<String> list=new ArrayList<String>();
		for (String string : endList) {
			 int k=string.indexOf(":");
			 String s=string.substring(0,k);
			 if(s.length()>1){
				  list.add(string);
			 }
		}
		return list;
	}
	
	public static  List<String>  featureRemoveOnly(List<String> featureList,List<String> onlyList){
		Map<String,String> featureMap=CommonCal.String2Map(featureList);
		Map<String,String> onlyMap=CommonCal.String2Map(onlyList);
		for (String string : onlyMap.keySet()) {
			if(featureMap.containsKey(string)){
				featureMap.remove(string);
			}
		}
		List list=CommonCal.Map2List1(featureMap);
		return list;
	}
	
	public static void main(String[] args) {
//		for (int i = 0; i < classTitle.length; i++) {
//			List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\nlpir\\data_biaozhu\\"+classTitle[i]+".txt");
//			Map map=findOnly(txtList);
//			List<String> endList=CommonCal.Map2List1(map);
//			String2Txt.writeFileByLines("E:\\ceping\\nlpir\\data_biaozhu_only\\"+classTitle[i]+".txt", endList);
//			
//		}
		
//		List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\nlpir\\only_icf\\tficf_sort.txt");
//		List<String> list=removeOneWord(txtList);
//		String2Txt.writeFileByLines("E:\\ceping\\nlpir\\only_icf\\tficf_sort_re.txt", list);
		
		
		List<String> tfList=Txt2String.readFileByLines("E:\\ceping\\nlpir\\feature_tf\\feature_tf_24643.txt");
		List<String> onlyList=Txt2String.readFileByLines("E:\\ceping\\nlpir\\only_icf\\feature_tficf_1060.txt");
		
		List<String> list=featureRemoveOnly(tfList, onlyList);
		String2Txt.writeFileByLines("E:\\ceping\\nlpir\\feature_tf\\feature_24643_reonly.txt", list);
		
	}
}
