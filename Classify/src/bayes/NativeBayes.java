package bayes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import comm.CommonCal;
import comm.Double2String;
import comm.String2Array;
import comm.Txt2String;

public class NativeBayes {
	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
		"history","military","regimen","society","sports","story","tech","travel","world"};
	private static double N=156000;
	private static double txt[]={10000,10000,4000,10000,4000,10000,10000,10000,10000,10000,10000,4000,10000,10000,4000,10000,10000,10000};
	private static double V=0;
	//计算p(w|c)
	public static String calculate_pwc(String tfString,int[] wordNum) {
		double result=0;
		double[] endStr=new double[18];
		int i=tfString.indexOf(":");
		String word=tfString.substring(0,i);
		String tf=tfString.substring(i+1);
		String[] tfNum=tf.split(",");
		int[] tf_num=String2Array.StrArray2IntArray(tfNum);
		for (int j = 0; j < tf_num.length; j++) {
			double up=tf_num[j]+1;
			double down=wordNum[j]+V;
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
				pwcList.add(str);
			}
		}
		return pwcList;
	}

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
			}
			result=d*pcArray[i]*100;
			endP[i]=result;
			if(result>max){
				max=result;
				index=i;
			}
		}
		String str=Double2String.Array2String(endP, 4);
		System.out.println(str);
		return index;
	}


	public static void main(String[] args) {

//		List<String> wordNum=Txt2String.readFileByLines("E:\\work\\Classify\\jieba_c_num\\num.txt");
//		int[] word_num=String2Array.StrList2IntArray(wordNum);
//		V=CommonCal.calculate_sum(word_num);
//		List<String> tfList=Txt2String.readFileByLines("E:\\work\\Classify\\jieba_feature_tf\\feature_tf_4959.txt");
//		List<String> endList=new ArrayList<String>();
//		for (String string : tfList) {
//			System.out.println(string);
//			String s=calculate_pwc(string,word_num);
//			endList.add(s);
//		}
//		String2Txt.writeFileByLines("E:\\work\\Classify\\bayes\\pwc_4959.txt", endList);

		//	String tfString="宝宝:2417,6,1,40,1,7,5,54,28,1,2,14,9,7,21,10,8,16";
		//		
		//		System.out.println(s);

		//计算p(c)
		//		List<String> list=calculate_pc();
		//		String2Txt.writeFileByLines("E:\\work\\Classify\\bayes\\pc.txt", list);
	
	String str="文友 忠告 暖 人心 人到中年 不交五友 ";
	List<String> pwcList=Txt2String.readFileByLines("E:\\work\\Classify\\bayes\\pwc_4959.txt");
	List<String> pcList=Txt2String.readFileByLines("E:\\work\\Classify\\bayes\\pc.txt");
	Map<String,String> map=CommonCal.String2Map(pwcList);
	List<String> term_pcwList=getFeature(str,map);
	int index=doBayes(term_pcwList, pcList);
	System.out.println(classTitle[index]);
	//System.out.println(pwcList.get(0));
	}
}
