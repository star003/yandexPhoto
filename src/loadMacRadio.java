import java.awt.TextField;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class loadMacRadio {
  //http://media.makradio.ru/arch_stream/arch.php?station=4.
	static JTextField x1;
	static JProgressBar pb1;
	static String fl = "";
	static int allPos = 0;
	static int curPos = 0;
	
	public static void loadMac2013(String pt,String station)throws Exception {
		String[] x = null;
		String k = "\"";
		String adr ="http://media.makradio.ru/arch_stream/arch.php?station="+station ; 
		try
	    {
        URL url = new URL(adr);
        URLConnection conn = url.openConnection();
        InputStreamReader rd = new InputStreamReader(conn.getInputStream(),"UTF-8");
        StringBuilder allpage = new StringBuilder();
        int n = 0;
        char[] buffer = new char[40000];
        while (n >= 0)
        {
            n = rd.read(buffer, 0, buffer.length);
            if (n > 0)
            {
                allpage.append(buffer, 0, n).append("\n");
            }
        }
        x = allpage.toString().split("\\s+");
	    }
		catch (Exception e){ 
			
	    }
		allPos = x.length;
		for (int i=0;i<x.length;i++) {
			curPos =i;
			if(x[i].indexOf("http://media.makradio.ru") !=-1){
			String[] a1 = x[i].split(k);
			String[] fn0 = a1[1].split("/");
			String fn = station+"-"+fn0[fn0.length-1];
			int h = Integer.valueOf(100*i/x.length);
			pb1.setValue(h);
			x1.setText(a1[1]);
			fl =a1[1]; 
			parselbum.saveUrl( pt+fn,a1[1]);
			}
		}
	}//public static void loadMac2013(String pt,String station)throws Exception
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void loadMac(String pt,String station)throws Exception {
		String[] x = null;
		String k = "\"";
		String adr ="http://media.makradio.ru/arch_stream/arch.php?station="+station ; 
		//www.makradio.ru/arch.php?station=4&arch=set
		try
	    {
        URL url = new URL(adr);
        URLConnection conn = url.openConnection();
        InputStreamReader rd = new InputStreamReader(conn.getInputStream(),"UTF-8");
        StringBuilder allpage = new StringBuilder();
        int n = 0;
        char[] buffer = new char[40000];
        while (n >= 0)
        {
            n = rd.read(buffer, 0, buffer.length);
            if (n > 0)
            {
                allpage.append(buffer, 0, n).append("\n");
            }
        }
        x = allpage.toString().split("\\s+");
	    }
		catch (Exception e){ 
			
	    }
		allPos = x.length;
		for (int i=0;i<x.length;i++) {
			curPos =i;
			if(x[i].indexOf("/arch_stream/download.php?f=") !=-1){
			String[] a1 = x[i].split(k);
			String[] fn0 = a1[1].split("/");
			String fn = station+"-"+fn0[fn0.length-1];
			int h = Integer.valueOf(100*i/x.length);
			pb1.setValue(h);
			x1.setText(a1[1]);
			fl =a1[1]; 
			parselbum.saveUrl( pt+fn,a1[1]);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getDir() throws IOException{
		JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choosertitle");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        	String r = chooser.getSelectedFile().getAbsolutePath();
        	
			return r;
          } else {
            return null;
          }
	}//public String getDir()
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static String getPos(){
		return String.valueOf(100*curPos/allPos);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		//loadMac("e://2//"); 
	}
	
}
