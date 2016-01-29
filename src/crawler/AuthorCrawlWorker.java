package crawler;

import author.Author;

public class AuthorCrawlWorker extends CrawlWorker{

	public AuthorCrawlWorker(String url, Crawler crawler) {
		super(url, crawler);
	}
	
	@Override
	public void run() {
		AuthorParser parser = new AuthorParser(url);
		Author author = null;
		try{
			author = parser.pars();
			synchronized (crawler) {				
				crawler.successfulCrawl(author);
			}
			
		}catch(Exception e){
			synchronized (crawler) {				
				crawler.unsuccessfulCrawl(url, e);
			}
		}
	}

}
