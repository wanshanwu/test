import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImagineCrawler { 
	
	private String html = "http://www.wpi.edu/academics/cs/research-interests.html";
	private Connection conn;
	public LinkedList<String> getURLsFromTeachers() throws IOException{
		LinkedList<String> urls = new LinkedList<String>();
		Document doc = Jsoup.connect(html).get();
		Element half = doc.select("half").first();
		
		Elements ed = half.select("a");
		for(Element e:ed){
			urls.add(e.attr("href"));
		}
		return urls;
	}
	 public void getDataFromTeacher(String url) throws IOException{
		 String[] inf = new String[4];
		 inf[3] = "";
		 String email = "[a-zA-Z]+[a-zA-Z0-9\\_]*\\@([a-zA-Z]+\\.)+[a-zA-Z0-9]+";
		 String tel = "[0-9]{3}\\-[0-9]+";
		 Document doc = Jsoup.connect(url).get();
		 Elements breif = doc.select("div.col-md-10");
		 Element field = doc.select("div.szll_wz").first();
		 Elements ep = field.select("p");
		 for (Element el:ep){
			 if(el.text()!=null){
				 inf[3]+=el.text();
			 }
		 }
			 
			
		 
		 String details = breif.select("p").text();           
			String st = details.replaceAll(" *, *", "");               
			inf[0] = breif.select("h3").text();             
			String[] servrals = st.split(" ");                        
			for(String s:servrals)                                     
			{
				if(s.matches(tel))                                
				{
					inf[1] = s;
				}else if (s.matches(email)) {                      
					inf[2] = s;
				}
			}
			for(int i=0;i<inf.length;i++){
				System.out.println(inf[i]);
			}
		 
		 toDataBase(inf);
	 }
	 //database
	 public void toDataBase(String[] imformation){
		 
		 try{
			 
		 
			 Statement stmt = conn.createStatement();//命令行接口

			 
	         String sql = "insert into teachers(name,tel,mail,sit) values('" + imformation[0]+"','"+imformation[1]+"','"+imformation[2]+"','"+imformation[3]+"')";
	         stmt.executeUpdate(sql);
	         
	         
		 	}catch (SQLException e) {
	            System.out.println("MySQL操作错误");
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	 }
	 public void createConnection(){
		 Connection conn = null;//应用程序与数据库链接
		 String sql;
		 String url = "jdbc:mysql://127.0.0.1:3306/MySQL?"  
	                + "user=&password=&useUnicode=true&characterEncoding=UTF8";
		 
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 conn = DriverManager.getConnection(url);
			 
	 }catch (Exception e) {
         System.out.println("MySQL操作错误");
         e.printStackTrace();
     } 
	 }
	

}
