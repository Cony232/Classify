package icf;

import java.util.ArrayList;
import java.util.List;

import chi.Funcation_Chi;

import comm.Double2String;
import comm.String2Array;
import comm.String2Txt;
import comm.Txt2String;

public class Funcation_ICF {
	private static double C=18;//类别数

	public static List<String> icf(List<String> txtList) {  
		List<String> icfList=new ArrayList<String>();
		for (String string : txtList) {
			int cf=0;
			int i=string.indexOf(":");
			String word=string.substring(0,i);
			String df=string.substring(i+1);
			String[] dfNum=df.split(",");
			for (int j = 0; j < dfNum.length; j++) {

				if(!"0".equals(dfNum[j])){
					cf=cf+1;System.out.println(cf);
				}
			}

			double	icf_num=Math.log((C+1)/((double)cf));
			icfList.add(word+":"+icf_num);
		}
		return icfList;
	}

	public static List<String> getFeatureICF(List<String> endList,List<String> icfList) { 
		List<String> list=new ArrayList<String>();
		for (int i = 0; i <endList.size(); i++) {
			String string=endList.get(i);
			int j=string.indexOf(":");
			String word=string.substring(0, j);
			for (int k = 0; k < icfList.size(); k++) {
				String s=icfList.get(k);
				int m=s.indexOf(":");
				String term=s.substring(0, m);
				if(word.equals(term)){
					list.add(icfList.get(k));
					icfList.remove(k);
					break;
				}
			}
		}
		return list;
	}

	public static List<String> tficf(List<String> tfList,List<String> icfList) {
		List<String> tficfList=new ArrayList<String>();
		for (int j = 0; j < tfList.size(); j++) {
			double[] tfidf_double=new double[18];
			String string=tfList.get(j);
			int i=string.indexOf(":");
			String word=string.substring(0,i);
			String tf=string.substring(i+1);
			String[] tfNum=tf.split(",");
			double[] tf_num=String2Array.StrArray2DouArray(tfNum);
			for (int k = 0; k < tf_num.length; k++) {
				//System.out.println(icfList.get(j));
				double icf=Double.valueOf(icfList.get(j).substring(i+2));
				//System.out.println(icf);
				tfidf_double[k]=tf_num[k]*icf;

			}
			String s=Double2String.Array2String(tfidf_double, 4);
			tficfList.add(word+":"+s);
		}
		return tficfList;
	}

	public static List<String> getMax(List<String> tficfList) {
		List<String> tficfList2=new ArrayList<String>();
		for (int j = 0; j < tficfList.size(); j++) {
			String string=tficfList.get(j);
			int i=string.indexOf(":");
			String word=string.substring(0,i);
			String tficf=string.substring(i+1);
			String[] tficfNum=tficf.split(",");
			double[] tficf_num=String2Array.StrArray2DouArray(tficfNum);
			double max=0;
			for (int k = 0; k < tficf_num.length; k++) {
				if(tficf_num[k]>max){
					max=tficf_num[k];
				}
			}
			tficfList2.add(word+":"+max);
		}
		return tficfList2;
	}

	public static void main(String[] args) {
//				List<String> list=Txt2String.readFileByLines("E:\\ceping\\jieba\\df_juzhen\\juzhen.txt");
//				List icfList=icf(list);
//				String2Txt.writeFileByLines("E:\\ceping\\jieba\\icf\\icf.txt", icfList);


//				List<String> endList=Txt2String.readFileByLines("E:\\ceping\\jieba\\feature\\end_feature.txt");
//				List<String> icfList=Txt2String.readFileByLines("E:\\ceping\\jieba\\icf\\icf.txt");
//				List<String> featureICFList=getFeatureICF(endList,icfList);
//				String2Txt.writeFileByLines("E:\\ceping\\jieba\\icf\\feature_icf.txt", featureICFList);
		//		
		//		
//		List<String> tfList=Txt2String.readFileByLines("E:\\ceping\\jieba\\tf_juzhen\\juzhen.txt");
//		List<String> icfList=Txt2String.readFileByLines("E:\\ceping\\jieba\\icf\\icf.txt");
//		List tficfList=tficf(tfList, icfList);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba\\icf\\tficf.txt", tficfList);
		
//		List<String> tficfList=Txt2String.readFileByLines("E:\\ceping\\jieba\\icf\\tficf.txt");
//		List<String> tficfList2=getMax(tficfList);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba\\icf\\tficf_max.txt", tficfList2);
		
		List<String> tficfList=Txt2String.readFileByLines("E:\\ceping\\jieba\\icf\\tficf_max.txt");
		List<String> maxlist=Funcation_Chi.SortFeature(tficfList);
		String2Txt.writeFileByLines("E:\\ceping\\jieba\\icf\\sort_tficf.txt", maxlist);
	}
}
