package comm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class String2Txt{
	public static void writeFileByLines(String fileName,List<String> list) {
		File f=new File(fileName);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for(int i=0;i<list.size();i++){
				bw.write(list.get(i));
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("结束写入");
	}
	
}



