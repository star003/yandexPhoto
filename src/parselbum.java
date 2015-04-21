import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class parselbum {
	static JLabel l11;
	static JLabel l12;
	static JLabel l13;
	static String userId="";
	static String path="";
	
	static List<String> getAlb(String id){
	//***************************************************
	//**вернет список ссылок на альбомы пользователя
	//**ID
	//***************************************************
		List<String> v = new ArrayList<String>();
		try {
			String adr = "http://api-fotki.yandex.ru/api/users/"+id+"/albums/"; 
	        URL url = new URL(adr);
	        URLConnection conn 			= url.openConnection();
	        InputStreamReader rd 		= new InputStreamReader(conn.getInputStream(),"UTF-8");
        	DocumentBuilderFactory dbf 	= DocumentBuilderFactory.newInstance();
        	DocumentBuilder db 			= dbf.newDocumentBuilder();
        	Document doc 				= db.parse(new InputSource(rd));
        	doc.getDocumentElement().normalize();
        	NodeList nodeList 			= doc.getElementsByTagName("id");
        	for (int z=0;z<nodeList.getLength();z++) {
            	Node node = nodeList.item(z);
            	if (getAlbCode(node.getTextContent()) !="-"){
            		v.add(getAlbCode(node.getTextContent()));
            	}
        	}//for z
        }//try
        	catch (Exception e) {
        	}//catch
		return v;
	}//List<alb> getAlb(String id)
	
	static List<String> getAlbUrl(String id){
		//***************************************************
		//**вернет список ссылок на альбомы по URL
		//***************************************************
		l11.setText("начало сканирования...");
			List<String> v = new ArrayList<String>();
			try {
				String adr = "http://api-fotki.yandex.ru/api/users/"+id+"/albums/"; 
		        URL url = new URL(adr);
		        URLConnection conn = url.openConnection();
		        InputStreamReader rd = new InputStreamReader(conn.getInputStream(),"UTF-8");
	        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        	DocumentBuilder db = dbf.newDocumentBuilder();
	        	Document doc = db.parse(new InputSource(rd));
	        	doc.getDocumentElement().normalize();
	        	NodeList nodeList = doc.getElementsByTagName("id");
	        	for (int z=0;z<nodeList.getLength();z++) {
	        		l12.setText(String.valueOf(100*z/nodeList.getLength())+"%");
	            	Node node = nodeList.item(z);
	            	if (getAlbCode(node.getTextContent()) !="-"){
	            		//***закинем адрес а не код альбома
	            		String[] user =node.getTextContent().split("/");
	            		String newAdres = "http://api-fotki.yandex.ru/api/users/"+id+"/album/"+getAlbCode(node.getTextContent())+"/photos/";
	            		v.add(newAdres);
	            		String aa = getDopPhoto(newAdres);
	            		if (aa!=null) {
            				System.out.println("dop:"+aa);
            				v.add(aa);
            				System.out.println("dop:"+aa);
    	            		while (aa!=null){
    	            			aa = getDopPhoto(aa);
    	            			if (aa!=null) {
    	            				System.out.println("dop:"+aa);
    	            				v.add(aa);
    	            			}
    	            		}//while
            			}
	            	}
	        	}//for z
	        }//try
	        	catch (Exception e) {
	        	}//catch
			//l12.setText("начало сканирования...");
			return v;
		}//static List<String> getAlbUrl(String adr)
	
	static List<String> getPhoto(String id,String adr1){
		//**************************************************
		//**вернет адреса фото в альбоме номе adr1 юзера id 
		//**
		//**************************************************
		List<String> v = new ArrayList<String>();
		//http://api-fotki.yandex.ru/api/users/x00502/album/300693/photos/
		String adr = "http://api-fotki.yandex.ru/api/users/"+id+"/album/"+adr1+"/photos/"; 
		//System.out.println(adr);
		try {
		URL url 				= new URL(adr);
        URLConnection conn 		= url.openConnection();
        InputStreamReader rd 	= new InputStreamReader(conn.getInputStream(),"UTF-8");
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db 		= dbf.newDocumentBuilder();
    	Document doc 			= db.parse(new InputSource(rd));
    	doc.getDocumentElement().normalize();
    	NodeList nodeList 		= doc.getElementsByTagName("content");
    	for (int z=0;z<nodeList.getLength();z++) {
        		Node node = nodeList.item(z);
        		NodeList x1 = node.getChildNodes();
     				NamedNodeMap x = node.getAttributes();
     				alb v1 = new alb();
       				for(int i=0;i<x.getLength();i++) {
       				if(x.item(i).getNodeName().equals("src")){
       					v1.adress =x.item(i).getNodeValue();
       				}
       				}//for i
       					v.add(v1.adress);
        	}//for z
    }//try
    	catch (Exception e) {
    	}//catch
		
	return v;
	}//static List<String> getPhoto(String adr)
	
	static List<String> getPhotoUrl(String adr){
		//**************************************************
		//**вернет адреса фото в альбоме по заданому адресу 
		//**************************************************
		List<String> v = new ArrayList<String>();
		//http://api-fotki.yandex.ru/api/users/x00502/album/300693/photos/
		//String adr = "http://api-fotki.yandex.ru/api/users/"+id+"/album/"+adr1+"/photos/"; 
		//System.out.println(adr);
		try {
		URL url 				= new URL(adr);
        URLConnection conn 		= url.openConnection();
        InputStreamReader rd 	= new InputStreamReader(conn.getInputStream(),"UTF-8");
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db 		= dbf.newDocumentBuilder();
    	Document doc 			= db.parse(new InputSource(rd));
    	doc.getDocumentElement().normalize();
    	NodeList nodeList 		= doc.getElementsByTagName("content");
    	for (int z=0;z<nodeList.getLength();z++) {
        		Node node = nodeList.item(z);
        		NodeList x1 = node.getChildNodes();
     				NamedNodeMap x = node.getAttributes();
     				alb v1 = new alb();
       				for(int i=0;i<x.getLength();i++) {
       				if(x.item(i).getNodeName().equals("src")){
       					v1.adress =x.item(i).getNodeValue();
       				}
       				}//for i
       					v.add(v1.adress);
        	}//for z
    }//try
    	catch (Exception e) {
    	}//catch
		
	return v;
	}//static List<String> getPhotoUrl(String adr)
	
	static String getDopPhoto(String adr){
		//*****************************************************************
		//**если в альбоме более 99 фото , то получим доп адрес на скачку
		//*****************************************************************
		List<String> v = new ArrayList<String>();
		//http://api-fotki.yandex.ru/api/users/x00502/album/300693/photos/
		//String adr = "http://api-fotki.yandex.ru/api/users/"+id+"/album/"+adr1+"/photos/"; 
		//System.out.println(adr);
		try {
		URL url 				= new URL(adr);
        URLConnection conn 		= url.openConnection();
        InputStreamReader rd 	= new InputStreamReader(conn.getInputStream(),"UTF-8");
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db 		= dbf.newDocumentBuilder();
    	Document doc 			= db.parse(new InputSource(rd));
    	doc.getDocumentElement().normalize();
    	NodeList nodeList 		= doc.getElementsByTagName("link");
    	for (int z=0;z<nodeList.getLength();z++) {
        		Node node = nodeList.item(z);
        		NodeList x1 = node.getChildNodes();
     				NamedNodeMap x = node.getAttributes();
     				alb v1 = new alb();
       				for(int i=0;i<x.getLength();i++) {
       				if(x.item(i).getNodeName().equals("href")){
       					if (x.item(i).getNodeValue().indexOf("updated")!=-1) {
       					return x.item(i).getNodeValue();
       					}
       					if (x.item(i).getNodeValue().indexOf("next")!=-1) {
           					return x.item(i).getNodeValue();
           				}
       				}
       				}//for i
        	}//for z
    }//try
    	catch (Exception e) {
    	}//catch
		
	return null;
	}//static String getDopPhoto(String adr)
	
	public static void main(String[] args) {
		String adr = "http://api-fotki.yandex.ru/api/users/byaka5917/album/35409/photos/";
		//String adr = "http://api-fotki.yandex.ru/api/users/byaka5917/album/35409/photos/updated;2010-03-08T18:19:21Z,162435,29224799/?limit=100";
		//System.out.println(adr);
		//List<String> photos = new ArrayList<String>();
		//photos = getPhotoUrl(adr);
		//System.out.println(photos.get(photos.size()));
		//for (int i = 0 ;i<photos.size();i++){
		//	System.out.println(getPhotoName(photos.get(i)));
		//}
		String aa = getDopPhoto(adr);
		System.out.println(aa);
		while (aa!=null){
			aa = getDopPhoto(aa);
			if (aa!=null) {
				System.out.println(aa);
			}
		}//while
		
	}//public static void main(String[] args)
	
	static String getAlbCode(String s){
		String[] x = s.split(":");
		if (x.length==6){
			return x[5];
		}
		else {
			return "-";
		}
	}//static String getAlbCode(String s)
	
	static String getAlbCodeFromUrl(String s){
		//******************************************
		//*вернет код альбома из строки адреса
		//******************************************
		String[] x = s.split("/");
		if (x.length>4){
			return x[5];
		}
		else {
			return "-";
		}
	}//String getAlbCodeFromUrl(String s)
	
	static String getPhotoName(String s){
		//******************************************
		//*вернет имя файла из переданной ссылки
		//*ссылка:http://img-fotki.yandex.ru/get/6434/195791804.2/0_927d1_70b340cf_orig
		//*вернет:0_927d1_70b340cf_orig
		//******************************************
		String[] x = s.split("/");
			return x[x.length-1];
	}//String getAlbCodeFromUrl(String s)
	
	public static void saveUrl(String filename, String urlString) throws MalformedURLException, IOException
    {
    	BufferedInputStream in = null;
    	FileOutputStream fout = null;
    	try
    	{
    		in = new BufferedInputStream(new URL(urlString).openStream());
    		fout = new FileOutputStream(filename);

    		byte data[] = new byte[1024];
    		int count;
    		while ((count = in.read(data, 0, 1024)) != -1)
    		{
    			fout.write(data, 0, count);
    		}
    	}
    	finally
    	{
    		if (in != null)
    			in.close();
    		if (fout != null)
    			fout.close();
    	}
    } //saveUrl(String filename, String urlString)
	
	static void createDir(String adr){
		File theDir = new File(adr);

		  // if the directory does not exist, create it
		  if (!theDir.exists())
		  {
		    System.out.println("creating directory: " + adr);
		    boolean result = theDir.mkdir();  
		    if(result){    
		       System.out.println("DIR created");  
		     }//if
		    else {
		    	System.out.println("DIR NOT created"); 
		    }

		  }
	}//static void createDir(String adr)
	
	public static void goParse() {
		String id = userId;
		l13.setText("user ID: "+id);
		opisanieAlb.setOpisanie(id,path+id+"//");
		//String ur = "http://api-fotki.yandex.ru/api/users/"+id+"/albums/";
		List<alb> aa  = new ArrayList<alb>();
		List<alb> aa1 = new ArrayList<alb>();
		List<String> albums = new ArrayList<String>();
		List<String> photos = new ArrayList<String>();
		aa1				= readAlb.getDes(id);
		albums 			= getAlbUrl(id);
		String crDir 	= "-";
		createDir(path+id);
		for (int i=1;i <albums.size();i++) {
			l11.setText("альбом "+(i+1)+" из "+albums.size());
			System.out.println(albums.get(i));
			String[] acode = albums.get(i).split("/");
			crDir = id+"//"+acode[7]+"//";
			createDir(path+id+"//"+acode[7]);
			photos = getPhotoUrl(albums.get(i));
			for (int i1=1;i1 <photos.size();i1++) {
				String k1 ="фото: "+i1+" из "+(photos.size()-1); 
				l12.setText(k1);
				try {
					File f = new File(path+crDir+"//"+getPhotoName(photos.get(i1))+".jpg");
					if(f.exists()){} 
					else{
					saveUrl(path+crDir+"/"+getPhotoName(photos.get(i1))+".jpg",photos.get(i1));
					}
					} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			photos.clear();
		}
		System.out.println("-------end--------");
	}//public static void goParse()
	
	
}
class alb{
	String adress;
	String type;
}
