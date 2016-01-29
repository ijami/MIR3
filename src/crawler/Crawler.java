package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import article.Article;

public abstract class Crawler {
	
	private String logPath;
	
	protected String[] startUrls;
	protected int numberOfCrawllingDoc;;
	protected Scheduler scheduler;
	
	private PrintWriter logWriter;
	private ExecutorService executor;
	protected int numCrawled = 0;
	protected int num_error = 0;
	
	
	public Crawler(int numberOfCrawllingDoc, String logPath) {
		scheduler = new Scheduler();
		this.logPath = logPath;
		this.numberOfCrawllingDoc = numberOfCrawllingDoc;
		
		numCrawled = 0;
		executor = Executors.newFixedThreadPool(30);
		try {
			logWriter = new PrintWriter(new File(this.logPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void crawl(){
		
		for (String string : startUrls)
			scheduler.addUrl(string);
		 
		while(this.numCrawled < numberOfCrawllingDoc){
			if(scheduler.size() > 0){
				String url = scheduler.nextUrl();
				executor.execute(newCrawlWorker(url));
			}
		}
		executor.shutdownNow();
		System.out.println("Crawling ends successfully with " + num_error + " errors. " + numCrawled + " article was crawled! :)");
	}
	
	protected abstract CrawlWorker newCrawlWorker(String url);

	public abstract void successfulCrawl(Article article);
	public abstract void unsuccessfulCrawl(String url, Exception e);
	
	protected void log(String log){
		System.err.println(log);
		synchronized (logWriter) {			
			logWriter.println(log);
		}
	}

	public int getNumberOfCrawllingDoc() {
		return numberOfCrawllingDoc;
	}

	public int getNumCrawled() {
		return numCrawled;
	}
	
	
}
