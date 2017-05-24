package bayes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import comm.CommonCal;
import comm.Double2String;
import comm.String2Array;
import comm.String2Txt;
import comm.Txt2String;

public class NativeBayes {
	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
		"history","military","regimen","society","sports","story","tech","travel","world"};
	private static double N=156000;
	private static double txt[]={10000,10000,4000,10000,4000,10000,10000,10000,10000,10000,10000,4000,10000,10000,4000,10000,10000,10000};
	private static double V=0;
	//计算p(w|c)
	public static String calculate_pwc(String tfString,int[] word_num) {
		double result=0;
		double[] endStr=new double[18];
		int i=tfString.indexOf(":");
		String word=tfString.substring(0,i);
		String tf=tfString.substring(i+1);
		String[] tfNum=tf.split(",");
		int[] tf_num=String2Array.StrArray2IntArray(tfNum);
		for (int j = 0; j < tf_num.length; j++) {
			double up=tf_num[j]+1;
			double down=word_num[j]+V;
			result=up/down;
			endStr[j]=result;
		}
		String s=Double2String.Array2String(endStr,5);
		return word+":"+s;
	}


	public static List<String> calculate_pc() {
		double result=0;
		List<String> list=new ArrayList<String>();
		//double[] endStr=new double[18];
		for (int i = 0; i < txt.length; i++) {
			result=txt[i]/N;
			String str=String.format("%.4f",result);
			list.add(str);
		}

		//String s=Double2String.Array2String(endStr,5);
		return list;
	}



	//特征表示
	public static List<String> getFeature(String str1,Map<String,String> map) {
		String[] term=str1.split(" ");
		List<String> pwcList=new ArrayList<String>();
		for (int i = 0; i < term.length; i++) {
			String str=map.get(term[i]);
			if(!("").equals(str)&&null!=str){
				//	System.out.println(term[i]);
				pwcList.add(str);
			}
		}
		return pwcList;
	}
//	//特征表示权重
//	public static List<String> getFeatureQZ(String str1,Map<String,String> map) {
//		String[] term=str1.split(" ");
//		List<String> tfidfList=new ArrayList<String>();
//		for (int i = 0; i < term.length; i++) {
//			String str=map.get(term[i]);
//			if(!("").equals(str)&&null!=str){
//				//	System.out.println(term[i]);
//				tfidfList.add(str);
//			}
//		}
//		return tfidfList;
//	}
	
	//计算p(c|x)
	public static int doBayes(List<String> pwcList,List<String> pcList) {
		double max=0;
		int index=0;
		double[] endP=new double[18];
		List<double[]> list1=new ArrayList<double[]>();
		double[] pcArray=String2Array.StrList2DouArray(pcList);
		for (String string : pwcList) {
			String[] pwc=string.split(",");
			double[] pwcArray=String2Array.StrArray2DouArray(pwc);
			list1.add(pwcArray);
		}
		for (int i = 0; i < 18; i++) {
			double result=0;
			double d=1;
			for (int j = 0; j < list1.size(); j++) {
				d =d*list1.get(j)[i];
				//System.out.println(i+":"+list1.get(j)[i]);
			}
			result=d*pcArray[i]*100;
			endP[i]=result;
			if(result>max){
				max=result;
				index=i;	
			}
		
		}
		//String str=Double2String.Array2String(endP, 4);
		//System.out.println(index);
		return index;
	}

	//加权朴素贝叶斯
	public static int doBayes2(List<String> pwcList,List<String> pcList,List<String> tfidfList) {
		double max=0;
		int index=0;
		double[] endP=new double[18];
		List<double[]> pwcArrayList=new ArrayList<double[]>();
		List<double[]> tfidfArrayList=new ArrayList<double[]>();
		double[] pcArray=String2Array.StrList2DouArray(pcList);
		for (int i = 0; i < pwcList.size(); i++) {
			String str1=pwcList.get(i);
			String[] pwc=str1.split(",");
			double[] pwcArray=String2Array.StrArray2DouArray(pwc);	
			pwcArrayList.add(pwcArray);
			String str2=tfidfList.get(i);
		//	int n=str2.indexOf(":");
		//	String str3=str2.substring(n+1);
			String[] tfidf=str2.split(",");
			double[] tfidfArray=String2Array.StrArray2DouArray(tfidf);
//			for (int j = 0; j < tfidfArray.length; j++) {
//				System.out.println(str2.substring(0, n)+":"+tfidfArray[j]);
//			}
			tfidfArrayList.add(tfidfArray);	
		}
		for (int i = 0; i < 18; i++) {
			double result=0;
			double d=1;
			for (int j = 0; j < pwcList.size(); j++) {
				d =d*pwcArrayList.get(j)[i]*tfidfArrayList.get(j)[i];
				//System.out.println(i+":"+list1.get(j)[i]);
				//System.out.println(d);
			}
			result=d*pcArray[i]*100;
			endP[i]=result;
			if(result>max){
				max=result;
				index=i;	
			}
		
		}
		//String str=Double2String.Array2String(endP, 4);
		//System.out.println(index);
		return index;
	}
	
	public static double CalRecall(List<int[]> recallList){
		double R=0.0;
		double[] N={2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000};
		for (int i = 0; i < recallList.size(); i++) {
			int[] temp=recallList.get(i);
			R=R+temp[i]/N[i];
			//System.out.println(R);
		}
		return R/18;
	}

	public static double CalPrecision(List<int[]> recallList){
		double[] Num=new double[18];
		double[] P=new double[18];
		double[] N={2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000};
		double result=0.0;
		for (int i = 0; i < 18; i++) {
			for (int j = 0; j < recallList.size(); j++) {
				Num[i]+=recallList.get(j)[i];			
				
			}
		//	System.out.println(Num[i]);
			P[i]=recallList.get(i)[i]/Num[i];
		}
		for (int i = 0; i < P.length; i++) {
			result+=P[i];
		}
		return result/18;
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
//				List<String> tfList=Txt2String.readFileByLines("E:\\ceping\\jieba_tf_juzhen\\juzhen.txt");
//				List<String> wordNum=featureTF(tfList);
//				String2Txt.writeFileByLines("E:\\ceping\\jieba_c_num\\num.txt", wordNum);

		//2.计算pwc
		//		List<String> wordNum=Txt2String.readFileByLines("E:\\ceping\\jieba_c_num\\num_4959.txt");
		//		int[] word_num=String2Array.StrList2IntArray(wordNum);
		//		V=4959;
		//		List<String> tfList=Txt2String.readFileByLines("E:\\ceping\\jieba_feature_tf\\feature_tf_4959.txt");
		//		List<String> endList=new ArrayList<String>();
		//		for (String string : tfList) {
		//			//System.out.println(string);
		//			String s=calculate_pwc(string,word_num);
		//			endList.add(s);
		//		}
		//		String2Txt.writeFileByLines("E:\\ceping\\bayes\\pwc_4959.txt", endList);


		//计算p(c)
		//		List<String> list=calculate_pc();
		//		String2Txt.writeFileByLines("E:\\ceping\\bayes\\pc.txt", list);

//		double n=0;
		List<int[]> recallList=new ArrayList<int[]>();
		List<String> pwcList=Txt2String.readFileByLines("E:\\ceping\\bayes\\pwc_4959.txt");
		List<String> pcList=Txt2String.readFileByLines("E:\\ceping\\bayes\\pc.txt");
		List<String> tfidfList=Txt2String.readFileByLines("E:\\ceping\\jieba_tfidf\\feature_4959_2.txt");
		for (int i = 0; i < 18; i++) {
			List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\test\\"+classTitle[i]+".txt");
			Map<String,String> pwcMap=CommonCal.String2Map(pwcList);
			Map<String,String> tfidfMap=CommonCal.String2Map(tfidfList);
			int[] RArray=new int[18];
			for (String string : txtList) {
				List<String> term_pcwList=getFeature(string,pwcMap);
				List<String> term_tfidfList=getFeature(string,tfidfMap);
				int index=doBayes2(term_pcwList, pcList,term_tfidfList);
				RArray[index]+=1;
			}
			recallList.add(RArray);
		}	
		double r=CalRecall(recallList);
		double p=CalPrecision(recallList);
		System.out.println("Recall:"+r);
		System.out.println("Precision:"+p);
	}

}
