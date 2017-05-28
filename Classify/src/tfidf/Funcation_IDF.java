package tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comm.CommonCal;
import comm.Double2String;
import comm.String2Array;
import comm.String2Txt;
import comm.Txt2String;

public class Funcation_IDF {
	private static double N=156000;
		
	public static List<String> idf(List<String> txtList) {  
		List<String> idfList=new ArrayList<String>();
		for (String string : txtList) {
			int i=string.indexOf(":");
			String word=string.substring(0,i);
			String df=string.substring(i+1);
			String[] dfNum=df.split(",");
			int[] df_num=String2Array.StrArray2IntArray(dfNum);
			double	idf_num=Math.log((N+1)/((double)CommonCal.calculate_sum(df_num)));
			idfList.add(word+":"+idf_num);
		}
		return idfList;
	}
	
	
	public static List<String> getFeatureIDF(List<String> endList,List<String> idfList) { 
		List<String> list=new ArrayList<String>();
		for (int i = 0; i <endList.size(); i++) {
			String string=endList.get(i);
			int j=string.indexOf(":");
			String word=string.substring(0, j);
			for (int k = 0; k < idfList.size(); k++) {
				String s=idfList.get(k);
				int m=s.indexOf(":");
				String term=s.substring(0, m);
				if(word.equals(term)){
					list.add(idfList.get(k));
					idfList.remove(k);
					break;
				}
			}
		}
		return list;
	}
	public static void main(String[] args) {
		//计算idf
//		List<String> dfList=Txt2String.readFileByLines("E:\\ceping\\jieba_df_juzhen\\juzhen.txt");
//		List<String> idfList=idf(dfList);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba_idf\\idf_all.txt", idfList);
		
		//计算特征词的idf
		List<String> endList=Txt2String.readFileByLines("E:\\ceping\\feature\\end_feature.txt");
		List<String> idfList=Txt2String.readFileByLines("E:\\ceping\\jieba_idf\\idf_all.txt");
		List<String> featureIDFList=getFeatureIDF(endList,idfList);
		String2Txt.writeFileByLines("E:\\ceping\\jieba_idf\\feature_idf.txt", featureIDFList);
	}
}
