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

	public static double CalPre(List<Integer> cList, int index){
		double A=0.0;
		double B=0.0;
		double P=0.0;
		for (int i = 0; i < cList.size(); i++) {
			if(cList.get(i)==index){
				A+=1;
			}else{
				B+=1;
			}
		}
		//System.out.println(A);
		P=A/(A+B);
		return P;
	}

	public static List<String> featureTF(List<String> list){
		int num=0;
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
		for (int i = 0; i < 18; i++) {
			//String term="";
			for (int j = 0; j < wordList.size(); j++) {
				num=num+tfList.get(j)[i];
			}
			featureTF_Num.add(""+num);
		}
		return featureTF_Num;
	}


	public static void main(String[] args) {
		//1.计算C中所有特征词的词频和
//				List<String> tfList=Txt2String.readFileByLines("E:\\ceping\\jieba_feature_tf\\feature_tf_chaifen.txt");
//				List<String> wordNum=featureTF(tfList);
//				String2Txt.writeFileByLines("E:\\ceping\\jieba_c_num\\num_chaifen.txt", wordNum);

		//2.计算pwc
//				List<String> wordNum=Txt2String.readFileByLines("E:\\ceping\\jieba_c_num\\num_chaifen.txt");
//				int[] word_num=String2Array.StrList2IntArray(wordNum);
//				V=3600;
//				List<String> tfList=Txt2String.readFileByLines("E:\\ceping\\jieba_feature_tf\\feature_tf_chaifen.txt");
//				List<String> endList=new ArrayList<String>();
//				for (String string : tfList) {
//					//System.out.println(string);
//					String s=calculate_pwc(string,word_num);
//					endList.add(s);
//				}
//				String2Txt.writeFileByLines("E:\\ceping\\bayes\\pwc_chaifen.txt", endList);


		//计算p(c)
//		List<String> list=calculate_pc();
//		String2Txt.writeFileByLines("E:\\ceping\\bayes\\pc.txt", list);

		double n=0;
		for (int i = 0; i < classTitle.length; i++) {
			List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\test\\"+classTitle[i]+".txt");
			//List<String> txtList=new ArrayList<String>();
			List<String> pwcList=Txt2String.readFileByLines("E:\\ceping\\bayes\\pwc_4959.txt");
			List<String> pcList=Txt2String.readFileByLines("E:\\ceping\\bayes\\pc.txt");
			Map<String,String> map=CommonCal.String2Map(pwcList);
			List<Integer> cList=new ArrayList<Integer>();
			//txtList.add("产 斤 双眼皮 公主 生产 时 医生 一句 话 吓坏 ");
			for (String string : txtList) {
				List<String> term_pcwList=getFeature(string,map);
				// System.out.println(term_pcwList.get(0));
				Integer index=doBayes(term_pcwList, pcList);
				// System.out.println(index);
				cList.add(index);
			}
			double p=CalPre(cList,i);
			n+=p;
			System.out.println(classTitle[i]+":"+p);
		}
		System.out.println(n/18);
	}
}
