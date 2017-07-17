package bayes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import comm.CommonCal;
import comm.Double2String;
import comm.String2Array;
import comm.String2Txt;
import comm.Txt2String;

public class IncreLearning_df {

	private static double N=15600;
	//	public static List<String> changePc(List<String> pcList,int newD,int index) {  
	//		double[] pcArray=String2Array.StrList2DouArray(pcList);
	//		List<String> pcList2=new ArrayList<String>();
	//		for (int i = 0; i < pcArray.length; i++) {
	//			if(i==index){
	//				pcArray[i]=(N/(N+newD))*pcArray[i]+(newD/(N+newD));
	//			}else{
	//				pcArray[i]=(N/(N+newD))*pcArray[i];
	//			}
	//			pcList2.add(String.format("%.4f",pcArray[i]));
	//		}
	//		return pcList2;
	//	}
	/**
	 * 
	 * @param pcList
	 * @param newD
	 * @param num 新增文本总量
	 * @return
	 */
	public static List<String> changePc(List<String> pcList,double[] newD,double num) {  
		double[] pcArray=String2Array.StrList2DouArray(pcList);
		List<String> pcList2=new ArrayList<String>();
		for (int i = 0; i < pcArray.length; i++) {	 
			pcArray[i]=(N/(N+num))*pcArray[i]+(newD[i]/(N+num));
			pcList2.add(String.format("%.4f",pcArray[i]));
		}
		return pcList2;
	}
	//sumTF每个类新增TF和
	public static List<String> changePWC(List<String> pwcList,List<String> newDFList,double[] N,double[] newD) {  
		Map<String,String> pwcMap=CommonCal.String2Map(pwcList);
		Map<String,String> DFMap=CommonCal.String2Map(newDFList);
		List<String> newPwc=new ArrayList<String>();
		int flag=0;
		for (String string : pwcMap.keySet()) {
			//System.out.println("string:"+string);
			String pwc=pwcMap.get(string);
			String[] pwcArray=pwc.split(",");
			double[] pwc_Num=String2Array.StrArray2DouArray(pwcArray);
			if(DFMap.containsKey(string)){flag++;
				String df=DFMap.get(string);
				String[] tfArray=df.split(",");
				int[] df_Num=String2Array.StrArray2IntArray(tfArray);
				for (int i = 0; i < pwc_Num.length; i++) {
					//System.out.println("V:"+V.length+"  pwc_Num:"+pwc_Num.length+" "+string);
					pwc_Num[i]=(N[i]/(N[i]+newD[i]))*pwc_Num[i]+(df_Num[i]/(N[i]+newD[i]));
					//System.out.println(V[i]);
				}
				DFMap.remove(string);
				newPwc.add(string+":"+Double2String.Array2String(pwc_Num, 4));
			}else{
				for (int i = 0; i < pwc_Num.length; i++) {
					pwc_Num[i]=(N[i]/(N[i]+newD[i]))*pwc_Num[i];
				}
				newPwc.add(string+":"+Double2String.Array2String(pwc_Num, 4));
			}
		}
		System.out.println(flag);
//		if(DFMap.size()>0){
//			for (String string : DFMap.keySet()) {
//				String df=DFMap.get(string);
//				String[] dfArray=df.split(",");
//				double[] df_Num=String2Array.StrArray2DouArray(dfArray);
//				double[] pwc_Num=new double[18];
//				for (int i = 0; i < pwc_Num.length; i++) {
//					pwc_Num[i]=(df_Num[i]+1)/(newD[i]);
//					//System.out.println(df_Num[i]+" "+newD[i]+" "+pwc_Num[i]);
//				}
//		
//				newPwc.add(string+":"+Double2String.Array2String(pwc_Num, 4));
//				
//			}
//		}
		return newPwc;
	}
	
//	public static List<String> chaifenTF(List<String> newTFList,List<String> featureList){
//		Map<String,String> pwcMap=CommonCal.String2Map(featureList);
//		Map<String,String> TFMap=CommonCal.String2Map(newTFList);
//		List<String> featureTF=new ArrayList<String>();
//		List<String> unfeatureTF=new ArrayList<String>();
//		for (String string : TFMap.keySet()) {//System.out.println(string);
//			if(pwcMap.containsKey(string)){
//				featureTF.add(string+":"+TFMap.get(string));
//				//TFMap.remove(string);
//			}
//		}
//		String2Txt.writeFileByLines("E:\\ceping\\jieba\\bayes\\incre\\tf_juzhen.txt", featureTF);
//		return featureTF;
//	}

	public static void main(String[] args) {
		//		List<String> pcList=Txt2String.readFileByLines("E:\\ceping\\jieba\\bayes\\pc_test.txt");
		//		//List<String> pcList2=changePc(pcList,4,0);
		//		int[] newD={1,2,1};
		//		List<String> pcList2=changePc(pcList,newD,4);
		//		String2Txt.writeFileByLines("E:\\ceping\\jieba\\bayes\\pc_test.txt", pcList2);
//		List<String> pwcList=Txt2String.readFileByLines("E:\\ceping\\jieba\\bayes\\pwc_test.txt");
//		double[] sumTF={6,4};
//		double[] V={5,6};
//		List<String> newTFList=new ArrayList<String>();
//		newTFList.add("你好:5,2");
//		newTFList.add("大家:1,2");
//	//	List<String> pwcList2=changePWC(pwcList, newTFList, V, sumTF);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba\\bayes\\pwc_test_2.txt", pwcList2);
	}
}
