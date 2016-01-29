package crawler;

import article.Article;

public class ArticleCrawlWorker extends CrawlWorker{

	public ArticleCrawlWorker(String url, Crawler crawler) {
		super(url, crawler);
	}

	@Override
	public void run() {
		ArticleParser parser = new ArticleParser(url);
		Article article = null;
		try{
			article = parser.pars();
			synchronized (crawler) {				
				crawler.successfulCrawl(article);
			}
			
		}catch(Exception e){
			synchronized (crawler) {				
				crawler.unsuccessfulCrawl(url, e);
			}
		}
	}

}
