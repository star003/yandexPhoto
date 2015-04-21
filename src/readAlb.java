import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class readAlb {
//****почитаем альбомы и поглядим что в них****
	static List<alb> aa = new ArrayList<alb>();
	static List<String> album = new ArrayList<String>();
	static List<String> des = new ArrayList<String>();
	
	public static void main(String[] args) {
		String id = "frolov-astro";
		aa = getDes(id);
		for (int i = 0 ;i<aa.size();i++){
			System.out.println(aa.get(i).adress+"   "+aa.get(i).type);
		}
	}//public static void main(String[] args)
	
	static List<alb> getDes(String id) {
		//******************************************
		//*вернем список альбомов с именами
		//******************************************
		try {
			String adr = "http://api-fotki.yandex.ru/api/users/"+id+"/albums/"; 
	        URL url 					= new URL(adr);
	        URLConnection conn 			= url.openConnection();
	        InputStreamReader rd 		= new InputStreamReader(conn.getInputStream(),"UTF-8");
        	DocumentBuilderFactory dbf 	= DocumentBuilderFactory.newInstance();
        	DocumentBuilder db 			= dbf.newDocumentBuilder();
        	Document doc 				= db.parse(new InputSource(rd));
        	doc.getDocumentElement().normalize();
        	if (doc.hasChildNodes()) {
        		printNote(doc.getChildNodes());
        	}
        }//try
        	catch (Exception e) {
        	}//catch
		System.out.println(album.size()+" "+des.size());
		for(int i=1;i<album.size();i++){
			alb v 		= new alb();
			v.adress 	= album.get(i);
			v.type   	= des.get(i);
			aa.add(v);
		}
		return aa;
	}//static List<alb> getDes(String id)
	
	private static void printNote(NodeList nodeList) {
		//********************************************
		//**рекурсивный перебор веток XML
		//********************************************
	    for (int count = 0; count < nodeList.getLength(); count++) {
	    	Node tempNode = nodeList.item(count);
	    		if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
	    			if (tempNode.getNodeName().equals("id")) {
	    				album.add(getAlbCode(tempNode.getTextContent()));
	    			}//if id
	    			if (tempNode.getNodeName().equals("title")) {
	    				des.add(tempNode.getTextContent());
	    			}
			if (tempNode.hasChildNodes()) {
				printNote(tempNode.getChildNodes());
			}
		 }
	    }//for
	  }//private static void printNote(NodeList nodeList)
	
	static String getAlbCode(String s){
		String[] x = s.split(":");
		if (x.length==6){
			return x[5];
		}
		else {
			return "-";
		}
	}//static String getAlbCode(String s)
}
