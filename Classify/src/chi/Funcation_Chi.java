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
	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
		"history","military","regimen","society","sports","story","tech","travel","world"};
//	private static double N=156000;
//	private static double txt[]={10000,10000,4000,10000,4000,10000,10000,10000,10000,10000,10000,4000,10000,10000,4000,10000,10000,10000};
//	private static double N=192000;
//	private static double txt[]={12000,12000,6000,12000,6000,12000,12000,12000,12000,12000,12000,6000,12000,12000,6000,12000,12000,12000};
	private static double N=1650;
	private static double txt[]={300,650,700};
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
			double[] feature=new double[3];
			//int i=str.indexOf(":");
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
		String2Txt.writeFileByLines("E:\\work\\animal\\feature\\max_feature.txt", maxList);
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
		for (int i = 0; i <endList.size(); i++) {
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


	public static List<String> calculate_chi_chaifen(List<String> txtList,int index) {  
		List<String> maxList=new ArrayList<String>();
		for (String str : txtList) {
			double chi=0;
			int i=str.indexOf(":");
			String word=str.substring(0, i);
			String numString=str.substring(i+1);
			String[] termlist=numString.split(",");
			int[] num=String2Array.StrArray2IntArray(termlist);
			int sum=CommonCal.calculate_sum(num);			
			A=num[index];
			B=sum-num[index];
			C=txt[index]-A;
			D=N-A-B-C;
			chi=chi(A,B,C,D);
			maxList.add(word+":"+chi);
		}		
		//String max=maxList.toString()
		return maxList;
	}


	public static void main(String[] args) {

	//	1.2.生成chi矩阵(feature.txt)和每个词的最大chi(max_feature.txt)
//				List<String> txtList=Txt2String.readFileByLines("E:\\work\\animal\\df_juzhen\\juzhen.txt");
//				List<String> list=calculate_chi(txtList);	
//				String2Txt.writeFileByLines("E:\\work\\animal\\feature\\feature.txt", list);

		//3.每个词最大chi按从大到小排序(end_feature.txt)
//				List<String> maxList=Txt2String.readFileByLines("E:\\work\\animal\\feature\\max_feature.txt");
//				List<String> list2=SortFeature(maxList);
//				String2Txt.writeFileByLines("E:\\work\\animal\\feature\\end_feature.txt", list2);//最大chi排序

		//4.按chi从大到小排序词的tf矩阵(jieba_tf_feature.txt)
		List<String> endList=Txt2String.readFileByLines("E:\\work\\animal\\feature\\end_feature.txt");
		//	System.out.println(endList.get(0));
		List<String> tfList=Txt2String.readFileByLines("E:\\work\\animal\\df_juzhen\\juzhen.txt");	
		List<String> list=getFeatureTF(endList, tfList);
		String2Txt.writeFileByLines("E:\\work\\animal\\feature_df\\feature_df.txt", list);

		//拆分计算chi并排序
		//		for (int i = 0; i < classTitle.length; i++) {
		//			List df_chaifen=Txt2String.readFileByLines("E:\\ceping\\\\nlpir\\df_juzhen\\"+classTitle[i]+".txt");
		//			List<String> maxList=calculate_chi_chaifen(df_chaifen,i);	
		//			String2Txt.writeFileByLines("E:\\ceping\\feature\\chaifen\\"+classTitle[i]+".txt", maxList);
		//			//List<String> maxlist=SortFeature(maxList);
		//		}

		//拆分后没类的
		//		for (int i = 0; i < classTitle.length; i++) {
		//			List<String> maxList=Txt2String.readFileByLines("E:\\ceping\\feature\\chaifen\\"+classTitle[i]+".txt");
		//			List<String> list=SortFeature(maxList);
		//			String2Txt.writeFileByLines("E:\\ceping\\feature\\chaifen\\end_feature\\"+classTitle[i]+".txt", list);//最大chi排序
		//		}

		//拆分的特征整合end_feature_chaifen
//				List<String>  zhenghe=new ArrayList<String> ();
//				for (int i = 0; i < classTitle.length; i++) {
//					List<String> maxList=Txt2String.readFileByLines("E:\\ceping\\feature\\chaifen\\end_feature\\"+classTitle[i]+".txt");
//					for (int j = 0; j < 200; j++) {
//						zhenghe.add(maxList.get(j));
//					}			
//				}
//				String2Txt.writeFileByLines("E:\\ceping\\feature\\end_feature_chaifen.txt", zhenghe);//最大chi排序
	}
}
