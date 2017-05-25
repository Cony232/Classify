package tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import comm.CommonCal;
import comm.Double2String;
import comm.String2Array;
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
				String str=entry.getKey()+";"+entry.getValue();// nlpir
			//	String str=entry.getKey()+":"+entry.getValue();// jieba
				n=n+entry.getValue();
				list.add(str);      
			} 		
		}
		//list.add(""+n);
		return list;
	}
	
	//tf频率
	public static List<String> tf2(List<String> txtList,List<String> numList) {  
		List<String> tfList=new ArrayList<String>();
		for (String string : txtList) {
			double[] tf_double=new double[18];
			int i=string.indexOf(":");
			String word=string.substring(0,i);
			String tf=string.substring(i+1);
			String[] tfNum=tf.split(",");
			int[] tf_Num=String2Array.StrArray2IntArray(tfNum);
			for (int j = 0; j < tf_Num.length; j++) {
				tf_double[j]=tf_Num[j]/(double)Integer.parseInt(numList.get(j));
				//System.out.println(tf_double[j]);
			}
			String s=Double2String.Array2String(tf_double, 4);
			tfList.add(word+":"+s);
		}
		return tfList;	
	}  

	
	public static List<String> featureTF(List<String> list){
		List<String> wordList=new ArrayList<String>();
		List<String> featureTF_Num=new ArrayList<String>();
		List<int[]> tfList=new ArrayList<int[]>();
		for (String string : list) {
			int i=string.indexOf(":");
			String word=string.substring(0,i);
			wordList.add(word);
			String tf=string.substring(i+1);
			String[] tfArray=tf.split(",");
			int[] intArray=String2Array.StrArray2IntArray(tfArray);
			tfList.add(intArray);
		}
		//String term="";
		int[] num=new int[18];
		for (int j = 0; j < tfList.size(); j++) {
			for (int i = 0; i < 18; i++) {
				num[i]=num[i]+tfList.get(j)[i];
			}
		}
		//	featureTF_Num.add(""+num);
		for (int i = 0; i < num.length; i++) {
			featureTF_Num.add(""+num[i]);
		}
		return featureTF_Num;
	}
	
	public static void main(String[] args) {
		//1.计算C中所有特征词的词频和
//		List<String> tfList=Txt2String.readFileByLines("E:\\ceping\\jieba_feature_tf\\feature_tf_30000.txt");
//		List<String> wordNum=featureTF(tfList);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba_c_num\\num_30000.txt", wordNum);
		
		List<List<String>> txtList=new ArrayList<List<String>>();
	    HashMap<String, int[]> map=new HashMap<String, int[]>();
		//计算每个类的tf
		List<String> numList=new ArrayList<String>();
		for (int i = 0; i < classTitle.length; i++) {
			List<String> list=Txt2String.readFileByLines("E:\\ceping\\nlpir\\data_remove\\"+classTitle[i]+".txt");
			HashMap<String, Integer> temp=tf(list);
			List<String> list1=Map2List1(temp);
			//String str=list1.get(list1.size()-1);
			//numList.add(str);
			list1.remove(list1.size()-1);
			String2Txt.writeFileByLines("E:\\ceping\\nlpir\\jieba_tf\\"+classTitle[i]+".txt", list1);
		}
//		//String2Txt.writeFileByLines("E:\\ceping\\jieba_c_num\\num.txt", numList);
//		
//		//计算tf矩阵(tf_all.txt)
//		for (int i = 0; i < classTitle.length; i++) {
//			List<String> list2=Txt2String.readFileByLines("E:\\ceping\\jieba_tf\\"+classTitle[i]+".txt");
//			txtList.add(list2);
//		}
//		for (int j = 0; j < txtList.size(); j++) {
//			map=CommonCal.calculate_juzhen(txtList.get(j), map, j+1);
//		}
//		List<String> endlist=CommonCal.Map2List2(map);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba_tf_juzhen\\juzhen.txt", endlist);
		
		//计算tf2
//		List<String> list=Txt2String.readFileByLines("E:\\ceping\\jieba_feature_tf\\feature_tf_9831.txt");
//		List<String> numList=Txt2String.readFileByLines("E:\\ceping\\jieba_c_num\\num.txt");
//		List tf2List=tf2(list,numList);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba_feature_tf\\juzhen_tf2_4959_2.txt", tf2List);
	}
	
	
}
