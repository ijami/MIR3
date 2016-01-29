package crawler;


abstract class CrawlWorker implements Runnable{
	String url;
	Crawler crawler;
	
	public CrawlWorker(String url, Crawler crawler) {
		this.url = url;
		this.crawler = crawler;
	}

	@Override
	public abstract void run();
}