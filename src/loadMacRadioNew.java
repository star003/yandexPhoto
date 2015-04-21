import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class loadMacRadioNew {
	public static void main(String[] args) {
		String fl = "";
		int allPos = 0;
		int curPos = 0;
		String pt = "";
		String[] x = null;
		String station = "";
		//String[] x = null;
		String k = "\"";
		String adr ="http://www.makradio.ru/arch.php?station=4&arch=set" ; 
		//www.makradio.ru/arch.php?station=4&arch=set
		try {
			URL url = new URL(adr);
			URLConnection conn = url.openConnection();
			InputStreamReader rd = new InputStreamReader(conn.getInputStream(),"UTF-8");
			StringBuilder allpage = new StringBuilder();
			int n = 0;
			char[] buffer = new char[40000];
			while (n >= 0) {
				n = rd.read(buffer, 0, buffer.length);
				if (n > 0) {
					allpage.append(buffer, 0, n).append("\n");
				}
			}
			x = allpage.toString().split("\\s+");
	    }
		catch (Exception e){ 
			
	    }
		
		allPos = x.length;
		System.out.println(allPos);
		for (int i=0;i<x.length;i++) {
			curPos =i;
			System.out.println(x[i]);
			if(x[i].indexOf("/arch_stream/download.php?f=") !=-1){
				//System.out.println(x[i]);
				String[] a1 = x[i].split(k);
				String[] fn0 = a1[1].split("/");
				String fn = station+"-"+fn0[fn0.length-1];
				int h = Integer.valueOf(100*i/x.length);
				fl =a1[1]; 
			//parselbum.saveUrl( pt+fn,a1[1]);
			}
		}
	}
}//public class loadMacRadioNew
