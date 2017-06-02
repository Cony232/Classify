package bayes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import comm.CommonCal;
import comm.Double2String;
import comm.String2Array;
import comm.String2Txt;
import comm.Txt2String;

public class NativeBayes_df {
	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
		"history","military","regimen","society","sports","story","tech","travel","world"};
	private static double N=15600;
	private static double txt[]={10000,10000,4000,10000,4000,10000,10000,10000,10000,10000,10000,4000,10000,10000,4000,10000,10000,10000};
	private static double V=0;
	private static List<String> resultP=new ArrayList<String>();
	//计算p(w|c)
	public static String calculate_pwc(String dfString) {
		double result=0;
		double[] endStr=new double[18];
		int i=dfString.indexOf(":");
		String word=dfString.substring(0,i);
		String df=dfString.substring(i+1);
		String[] dfNum=df.split(",");
		int[] df_num=String2Array.StrArray2IntArray(dfNum);
		for (int j = 0; j < df_num.length; j++) {
			double up=df_num[j]+1;
			double down=txt[j];
			//result=up/down*100;
			result=up/down;
			endStr[j]=result*100;
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
	public static List<String> gedfeature(String str1,Map<String,String> map) {

		String[] term=str1.split(" ");
		List<String> pwcList=new ArrayList<String>();
		for (int i = 0; i < term.length; i++) {
			String str=map.get(term[i]);
			//System.out.println(term[i]+":"+str);
			if(!("").equals(str)&&null!=str){

				//	System.out.println(term[i]);
				pwcList.add(str);
			}
		}
		return pwcList;
	}
	//	//特征表示权重
	//	public static List<String> gedfeatureQZ(String str1,Map<String,String> map) {
	//		String[] term=str1.split(" ");
	//		List<String> dfidfList=new ArrayList<String>();
	//		for (int i = 0; i < term.length; i++) {
	//			String str=map.get(term[i]);
	//			if(!("").equals(str)&&null!=str){
	//				//	System.out.println(term[i]);
	//				dfidfList.add(str);
	//			}
	//		}
	//		return dfidfList;
	//	}

	//计算p(c|x)
	public static int doBayes(List<String> pwcList,List<String> pcList) {
		double max=0;
		int index=-1;
		int mid_i=-1;
		int max_i=-1;
		double[] endP=new double[18];
		List<double[]> list1=new ArrayList<double[]>();
		double[] pcArray=String2Array.StrList2DouArray(pcList);
		if(pwcList.size()==0){
			//index=4;
		}else{
			for (String string : pwcList) {
				String[] pwc=string.split(",");
				double[] pwcArray=String2Array.StrArray2DouArray(pwc);
				list1.add(pwcArray);
			}
			for (int i = 0; i < 18; i++) {
				double result=-1;
				double d=1;
				for (int j = 0; j < list1.size(); j++) {
					d =d*list1.get(j)[i];
					//System.out.println(i+":"+list1.get(j)[i]);
				}
				result=d*pcArray[i];
				//	System.out.println(i+":"+result);
				endP[i]=result;
				if(result>max){
					mid_i=max_i;
					max_i=i;
					max=result;
					index=i;	
				}
				//System.out.println(result);
//				if(max_i==3&&mid_i==0){
//					index=0;
//				}
			}
			resultP.add(Double2String.Array2String(endP, 4));

		}
		//String str=Double2String.Array2String(endP, 4);
	//	System.out.println(index+":"+Double2String.Array2String(endP, 4));
		return index;
	}

	//加权朴素贝叶斯
	public static int doBayes2(List<String> pwcList,List<String> pcList,List<String> dfidfList) {
		double max=0;
		int index=-1;
		double[] endP=new double[18];
		List<double[]> pwcArrayList=new ArrayList<double[]>();
		List<double[]> dfidfArrayList=new ArrayList<double[]>();
		double[] pcArray=String2Array.StrList2DouArray(pcList);
		for (int i = 0; i < pwcList.size(); i++) {
			String str1=pwcList.get(i);
			String[] pwc=str1.split(",");
			double[] pwcArray=String2Array.StrArray2DouArray(pwc);	
			pwcArrayList.add(pwcArray);
			String str2=dfidfList.get(i);
			//	int n=str2.indexOf(":");
			//	String str3=str2.substring(n+1);
			String[] dfidf=str2.split(",");
			double[] dfidfArray=String2Array.StrArray2DouArray(dfidf);
			//			for (int j = 0; j < dfidfArray.length; j++) {
			//				System.out.println(str2.substring(0, n)+":"+dfidfArray[j]);
			//			}
			dfidfArrayList.add(dfidfArray);	
		}
		for (int i = 0; i < 18; i++) {
			double result=0;
			double d=1;
			for (int j = 0; j < pwcList.size(); j++) {
				d =d*pwcArrayList.get(j)[i]*dfidfArrayList.get(j)[i];
				//System.out.println(i+":"+list1.get(j)[i]);
				//System.out.println(d);
			}
			result=d*pcArray[i]*10;
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
		System.out.println(recallList.size());
		for (int i = 0; i < recallList.size(); i++) {
			//	System.out.println(recallList.size());
			int[] temp=recallList.get(i);
			R=R+temp[i]/N[i];
			//System.out.println(R);
		}
		return R/18;
	}

	public static double CalPrecision(List<int[]> recallList){
		double[] Num=new double[18];
		double[] P=new double[18];
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


	public static int doOnly(List<String> onlyList) {
		double max=0;
		
		int index=-1;
		double[] endP=new double[18];
		List<double[]> list1=new ArrayList<double[]>();	 
		for (String string : onlyList) {
			String[] only=string.split(",");
			double[] onlyArray=String2Array.StrArray2DouArray(only);
			list1.add(onlyArray);
		}
		for (int i = 0; i < 18; i++) {
			double result=-1;
			double d=1;
			for (int j = 0; j < list1.size(); j++) {
				d =d*list1.get(j)[i];
				//System.out.println(i+":"+list1.get(j)[i]);
			}
			result=d;
			//	System.out.println(i+":"+result);
			endP[i]=result;
			if(result>max){
			 
				max=result;
				index=i;	
			}
			 
		}
	//	resultP.add(Double2String.Array2String(endP, 4));
		//String str=Double2String.Array2String(endP, 4);
		//System.out.println("index:"+index);
		return index;
	}


	public static void main(String[] args) {


		//2.计算pwc
		List<String> dfList=Txt2String.readFileByLines("E:\\ceping\\jieba\\feature_df\\feature_df_30000.txt");
		List<String> endList=new ArrayList<String>();
		for (String string : dfList) {
			//System.out.println(string);
			String s=calculate_pwc(string);
			endList.add(s);
		}
		String2Txt.writeFileByLines("E:\\ceping\\jieba\\bayes_df\\pwc_30000.txt", endList);


		//计算p(c)
		//						List<String> list=calculate_pc();
		//						String2Txt.writeFileByLines("E:\\ceping\\jieba\\bayes\\pc.txt", list);

				double n=0;
		List<String> list=new ArrayList<String>();
		List<String> errorList=new ArrayList<String>();
		List<String> resultList=new ArrayList<String>();
		List<int[]> recallList=new ArrayList<int[]>();
		List<String> pwcList=Txt2String.readFileByLines("E:\\ceping\\jieba\\bayes_df\\incre\\pwc.txt");
		//List<String> onlyList=Txt2String.readFileByLines("E:\\ceping\\nlpir\\only_icf\\feature_dficf_1060.txt");
		List<String> pcList=Txt2String.readFileByLines("E:\\ceping\\jieba\\bayes_df\\pc.txt");
		//		//List<String> dficfList=Txt2String.readFileByLines("E:\\ceping\\jieba\\icf\\dficf_9831.txt");
		//for (int i = 0; i < 18; i++) {
		//	List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\jieba\\test\\"+classTitle[i]+".txt");
			List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\t_re.txt");
		
			Map<String,String> pwcMap=CommonCal.String2Map(pwcList);
			//Map<String,String> dficfMap   =CommonCal.String2Map(dficfList);
			//Map<String,String> onlyMap=CommonCal.String2Map(onlyList);
			//int[] RArray=new int[18];
			for (int j = 0; j < txtList.size(); j++) {
				String string=txtList.get(j);
			//	System.out.println(string);
				//List<String> term_onlyList=gedfeature(string,onlyMap);
				//				if(term_onlyList.size()>0){
				//					int index=doOnly(term_onlyList);
				//					//int index=doBayes2(term_pcwList, pcList,term_dfidfList);
				//					if(index!=-1){
				//						RArray[index]+=1;
				//						if(index!=i){
				//							errorList.add(string+"  T:"+classTitle[i]+" F:"+classTitle[index]);
				//						}
				//					}
				//				}else{
				List<String> term_pcwList=gedfeature(string,pwcMap);
				//List<String> term_dfidfList=gedfeature(string,dficfMap);
				if(term_pcwList.size()==0){
				  list.add("null");//没有特征的
				}else{
					int index=doBayes(term_pcwList, pcList);
					
					list.add(classTitle[index]);
				//	RArray[index]+=1;
					//System.out.println(index);
					//int index=doBayes2(term_pcwList, pcList,term_dfidfList);
//					if(index!=i){
//						errorList.add(string+"  T:"+classTitle[i]+" F:"+classTitle[index]+"  "+resultP.get(index));
//					} 
				}	 
			}	
			//recallList.add(RArray);	
			//	}

			String s="";
			//System.out.println(RArray.length);
//			for (int k = 0; k < RArray.length; k++) {
//				s=s+RArray[k]+" ";
//			}
//			resultList.add(s);
//		}

//		String2Txt.writeFileByLines("E:\\ceping\\jieba\\bayes_df\\fenlei.txt", resultList);
		String2Txt.writeFileByLines("E:\\ceping\\result.txt", list);
//		String2Txt.writeFileByLines("E:\\ceping\\jieba\\bayes_df\\error.txt", errorList);
//		double r=CalRecall(recallList);
//		double p=CalPrecision(recallList);
//		System.out.println("Recall:"+r);
//		System.out.println("Precision:"+p);
	}

}
