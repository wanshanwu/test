import java.io.IOException;
import java.sql.SQLException;
public class main2014302580008 {
	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub	
		
		myThread testSingle = new myThread();
		System.out.println("单线程爬取中");
		long singlebegintime = System.currentTimeMillis();
		testSingle.getDataBySingleThread();
		long singleendtime = System.currentTimeMillis();
		System.out.println("单线程时间为"+(singleendtime - singlebegintime)+"ms");
		
		myThread testMulti = new myThread();
		System.out.println("多线程爬取中");
		long multibegintime = System.currentTimeMillis();
		testMulti.getDataByMultiThread();
		while(true){
			if(Thread.activeCount() == 1)
				break;
		}
		long multiendtime = System.currentTimeMillis();
		System.out.println("多线程时间为"+(multiendtime - multibegintime)+"ms");
	}
}


