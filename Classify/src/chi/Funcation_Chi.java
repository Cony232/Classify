package chi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import comm.CommonCal;
import comm.Double2String;
import comm.String2Array;
import comm.String2Txt;
import comm.Txt2String;

public class Funcation_Chi {
	private static double N=156000;
	private static double txt[]={10000,10000,4000,10000,4000,10000,10000,10000,10000,10000,10000,4000,10000,10000,4000,10000,10000,10000};
	private static double A=0;
	private static double B=0;
	private static double C=0;
	private static double D=0;

	public static List<String> calculate_chi(List<String> txtList) {  
		List<String> featureList=new ArrayList<String>();
		List<String> maxList=new ArrayList<String>();
		int flag=0;//记录最大chi的类别
		for (String str : txtList) {
			double max=0;
			double[] feature=new double[18];
			int i=str.indexOf(":");
			String word=str.substring(0, i);
			String numString=str.substring(i+1);
			String[] termlist=numString.split(",");
			int[] num=String2Array.StrArray2IntArray(termlist);
			int sum=CommonCal.calculate_sum(num);
			for (int j = 0; j < num.length; j++) {
				A=num[j];
				B=sum-num[j];
				C=txt[j]-A;
				D=N-A-B-C;
				//System.out.println(word+":---------"+A+";"+B+";"+C+";"+D);
				feature[j]=chi(A,B,C,D);	
				if(feature[j]>max){
					max=feature[j];
					flag=j+1;
				}
			}
			String maxFeature=Double.toString(max);
			//maxList.add(word+":"+maxFeature+";"+flag);
			maxList.add(word+":"+maxFeature);
			String s=Double2String.Array2String(feature,4);
			featureList.add(word+":"+s);
		}
		//获取每个单词的最大chi
		String2Txt.writeFileByLines("E:\\ceping\\feature\\max_feature.txt", maxList);
		//String max=maxList.toString()
		return featureList;
	}



	public static  double chi(double A,double B,double C,double D){
		double up=N*Math.pow((A*D)-(B*C),2);
		//System.out.println(up);
		double down=(A+C)*(B+D)*(A+B)*(C+D);
		//System.out.println(down);
		return up/down;	
	}

	


	//	//获取chi最大值
	//	public static String getMax(double[] arr){
	//		double max=0;
	//		int flag=0;
	//		for(int i=1;i<arr.length;i++){
	//			if(arr[i]>max){
	//				max=arr[i];
	//				flag=i+1;
	//			}
	//		}
	//		return ""+max+";"+flag;
	//	}

	//chi排序
	public static List<String> SortFeature(List<String> maxList){
		Map<String,Double> maxMap=new HashMap<String,Double>();
		List<String> endList=new ArrayList<String>();
		for (String string : maxList) {
			int i=string.indexOf(":");
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
				return o2.getValue()>=o1.getValue()?1:-1;
			}
		});
		for(Map.Entry<String,Double> mapping:infoIds){   
			endList.add(mapping.getKey()+":"+mapping.getValue());   
		}   
		return endList;
	}


	public static List<String> getFeatureTF(List<String> endList,List<String> tfList) { 
		List<String> list=new ArrayList<String>();
		for (int i = 0; i <30000; i++) {
			String string=endList.get(i);
			int j=string.indexOf(":");
			String word=string.substring(0, j);
			for (int k = 0; k < tfList.size(); k++) {
				String s=tfList.get(k);
				int m=s.indexOf(":");
				String term=s.substring(0, m);
				if(word.equals(term)){
					list.add(tfList.get(k));
					tfList.remove(k);
					break;
				}
			}
		}
		return list;
	}


	public static void main(String[] args) {

		//生成chi矩阵(feature.txt)和每个词的最大chi(max_feature.txt)
		List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\jieba_df_juzhen\\juzhen.txt");
		List<String> list=calculate_chi(txtList);	
//		String2Txt.writeFileByLines("E:\\ceping\\feature\\feature.txt", list);

		//每个词最大chi按从大到小排序(end_feature.txt)
//		List<String> maxList=Txt2String.readFileByLines("E:\\ceping\\feature\\max_feature.txt");
//		List<String> list=SortFeature(maxList);
//		String2Txt.writeFileByLines("E:\\ceping\\feature\\end_feature.txt", list);//最大chi排序

		//按chi从大到小排序词的tf矩阵(jieba_tf_feature.txt)
//		List<String> endList=Txt2String.readFileByLines("E:\\ceping\\feature\\end_feature.txt");
//		//	System.out.println(endList.get(0));
//		List<String> tfList=Txt2String.readFileByLines("E:\\ceping\\jieba_tf_juzhen\\juzhen.txt");	
//		List<String> list=getFeatureTF(endList, tfList);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba_feature_tf\\feature_tf_30000.txt", list);


	}
}
