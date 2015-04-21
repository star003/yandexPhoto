import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class opisanieAlb {
	//**********************************************
	//*создает текстовый файл с описанием альбомов
	//**********************************************
	public static void main(String[] args) {
		setOpisanie("ale248","e://11//ale248//");
		
	}//public static void main(String[] args)
	
	static void setOpisanie(String id,String pt) {
		List<alb> aa = new ArrayList<alb>();
		try {
		BufferedWriter out = new BufferedWriter(new FileWriter(pt+id+".txt"));
		aa = readAlb.getDes(id);
		for (int i = 0 ;i<aa.size();i++){
			System.out.println(aa.get(i).adress+"   "+aa.get(i).type);
			out.write(aa.get(i).adress+"   "+aa.get(i).type);
			out.newLine();
		}
            out.close();
	        } catch (IOException e) {}
		
		aa.clear();
	}
}
