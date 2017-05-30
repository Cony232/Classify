package bayes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tfidf.Funcation_TF;

import comm.CommonCal;
import comm.Double2String;
import comm.String2Array;
import comm.String2Txt;
import comm.Txt2String;

public class TestIncreBayes {
	public static String[] classTitle={"baby","car","discovery","entertainment","essay","fashion","finance","food","game",
		"history","military","regimen","society","sports","story","tech","travel","world"};



	public static void main(String[] args) {
		List<int[]> resultList=new ArrayList<int[]>();
		List<String> fList=new ArrayList<String>();
		for (int i = 0; i < 18; i++) {
			resultList.add(new int[18]);
		}
		List<List<String>> txtList=new ArrayList<List<String>>();
		for (int i = 0; i < 18; i++) {
			List<String> onetxtList=Txt2String.readFileByLines("E:\\ceping\\nlpir\\test\\"+classTitle[i]+".txt");
			txtList.add(onetxtList);
		}
		List<List<String>> nullFeature=new ArrayList<List<String>>();
		for (int i = 0; i < 10; i++) {
			List<List<String>> trueList=new ArrayList<List<String>>();
			List<String> pwcList=Txt2String.readFileByLines("E:\\ceping\\nlpir\\bayes\\incre\\pwc.txt");
			List<String> pcList=Txt2String.readFileByLines("E:\\ceping\\nlpir\\bayes\\incre\\pc.txt");
			Map<String,String> pwcMap=CommonCal.String2Map(pwcList);
			for (int j = 0; j < txtList.size(); j++) {
			//	int[] RArray=new int[18];
				List<String> tList=new ArrayList<String>();
				List<String> nList=new ArrayList<String>();
			
				for (int k = i*200; k < (i+1)*200; k++) {
					//System.out.println(k);
					String string=txtList.get(j).get(k);
					//System.out.println(string);
					List<String> term_pcwList=NativeBayes.getFeature(string,pwcMap);
					//List<String> term_tfidfList=getFeature(string,tficfMap);
					if(term_pcwList.size()==0){
						nList.add(string);
					}else{
						int index=NativeBayes.doBayes(term_pcwList, pcList);
						//int index=doBayes2(term_pcwList, pcList,term_tfidfList);
						if(index!=-1){
							resultList.get(j)[index]+=1;
							if(index!=j){
								fList.add(string+"  T:"+classTitle[j]+" F:"+classTitle[index]+" ");
							}else{
								tList.add(string);
							}
						}
					}	
				}
				trueList.add(tList);
			}

			
			List<String> pwList=Txt2String.readFileByLines("E:\\ceping\\nlpir\\bayes\\incre\\pwc.txt");
			
			//更新pwc
			List<String> newTFList=Funcation_TF.string2tfjuzhen(trueList);	
			List<String> newTFList2=IncreLearning.chaifenTF(newTFList, pwList);
			List<String> sumTF=Funcation_TF.featureTF(newTFList2);
			double[] sumTFArray=String2Array.StrList2DouArray(sumTF);
			List<String> V=Txt2String.readFileByLines("E:\\ceping\\nlpir\\bayes\\incre\\V.txt");
			double[] VArray=String2Array.StrList2DouArray(V);
			List<String> pwcList2=IncreLearning.changePWC(pwcList, newTFList2, VArray, sumTFArray);
			List<String> newVList= new ArrayList<String>();
			for (int j = 0; j < VArray.length; j++) {
				VArray[j]=VArray[j]+sumTFArray[j];
				newVList.add(""+VArray[j]);
			}
			String2Txt.writeFileByLines("E:\\ceping\\nlpir\\bayes\\incre\\V.txt", newVList);
			String2Txt.writeFileByLines("E:\\ceping\\nlpir\\bayes\\incre\\pwc.txt", pwcList2);
			
			//更新pc
			int[] newD=new int[18];
			int num=0;
			for (int j = 0; j < trueList.size(); j++) {
				//System.out.println(trueList.get(j).size());
				newD[j]=trueList.get(j).size();
				num=num+newD[j];
			}
			List<String> pcList2=IncreLearning.changePc(pcList,newD,num);
			String2Txt.writeFileByLines("E:\\ceping\\nlpir\\bayes\\incre\\pc.txt", pcList2);
		}
		double r=NativeBayes.CalRecall(resultList);
		double p=NativeBayes.CalPrecision(resultList);
		List<String> l=new ArrayList<String>();
		 for (int i = 0; i < resultList.size(); i++) {
				String s="";
			 for (int j = 0; j < resultList.get(i).length; j++) {
				 s=s+resultList.get(i)[j]+" ";
			}
			 l.add(s);
		}
		String2Txt.writeFileByLines("E:\\ceping\\nlpir\\bayes\\error.txt", fList);
		String2Txt.writeFileByLines("E:\\ceping\\nlpir\\bayes\\fenlei.txt", l);
		System.out.println("Recall:"+r);
		System.out.println("Precision:"+p);
	}
}
