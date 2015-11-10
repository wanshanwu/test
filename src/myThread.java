import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class myThread extends Thread {
	private LinkedList<String> urls;
    public class CrawlRunnable implements Runnable{//run start两方法
    	//private String name;
    	private String url;
        CrawlRunnable(String u){
        	//name = i;
        	url = u;
        }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ImagineCrawler crawlInf = new ImagineCrawler();
			//crawlInf.setHtml("teacher"+name+"html");
			//crawlInf.setUrl(url);
			//crawlInf.getHtml();
			//File file2 = new File("./crawlfiles/teacher"+ name +".html");
			try {
				crawlInf.getDataFromTeacher(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }
    //单线程
    public void getDataBySingleThread() throws IOException, SQLException {
		ImagineCrawler crawlURLs = new ImagineCrawler();
		//crawlURLs.getHtml();
		//File file = new File("./crawlfiles/teachers1.html");
		urls = crawlURLs.getURLsFromTeachers();
		
		ImagineCrawler crawInf = new ImagineCrawler();
		Iterator<String> it = urls.iterator();//迭代器 循环
		while(it.hasNext()){
			//crawInf.setHtml("teacher"+ String.valueOf(num) +".html");
			//crawInf.setUrl(it.next());
			//crawInf.getHtml();
			//File file2 = new File("./crawlfiles/teacher"+ String.valueOf(num) +".html");
			crawInf.getDataFromTeacher(it.next());

			
		}
	}
    //多线程
    public void getDataByMultiThread() throws IOException{
		ExecutorService exec = Executors.newFixedThreadPool(5); //创建线程池  
		ImagineCrawler crawlURLs = new ImagineCrawler();
		//File file = new File("./crawlfiles/teachers1.html");
        urls = crawlURLs.getURLsFromTeachers();
		Iterator<String> it = urls.iterator();
		while(it.hasNext()){
			String s = it.next();
			CrawlRunnable c = new CrawlRunnable(s);
			exec.execute(new Thread(c));
		}
		exec.shutdown();
	}
	 
}
