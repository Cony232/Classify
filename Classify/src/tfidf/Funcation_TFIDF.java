package tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comm.Double2String;
import comm.String2Array;
import comm.String2Txt;
import comm.Txt2String;

public class Funcation_TFIDF {
	public static List<String> tfidf(List<String> tfList,List<String> idfList) {
		List<String> tfidfList=new ArrayList<String>();
		for (int j = 0; j < tfList.size(); j++) {
			double[] tfidf_double=new double[18];
			String string=tfList.get(j);
			int i=string.indexOf(":");
			String word=string.substring(0,i);
			String tf=string.substring(i+1);
			String[] tfNum=tf.split(",");
			double[] tf_num=String2Array.StrArray2DouArray(tfNum);
			for (int k = 0; k < tf_num.length; k++) {
				double idf=Double.valueOf(idfList.get(j).substring(i+1));
				System.out.println(idf);
				tfidf_double[k]=tf_num[k]*idf;
				
			}
			String s=Double2String.Array2String(tfidf_double, 4);
			tfidfList.add(word+":"+s);
		}
		return tfidfList;
	}
	
	public static void main(String[] args) {
		List<String> tfList=Txt2String.readFileByLines("E:\\ceping\\jieba_feature_tf\\juzhen_tf2_4959_2.txt");
		List<String> idfList=Txt2String.readFileByLines("E:\\ceping\\jieba_idf\\feature_idf_4959.txt");
		List tf2List=tfidf(tfList, idfList);
		String2Txt.writeFileByLines("E:\\ceping\\jieba_tfidf\\feature_4959_2.txt", tf2List);
	}
}
