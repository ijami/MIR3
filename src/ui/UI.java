package ui;

import author.Author;
import crawler.ArticleCrawler;
import crawler.AuthorCrawler;
import crawler.AuthorParser;
import crawler.Crawler;


public class UI {
	
	private static final boolean DEBUG = true;

	public static void main(String[] args) {
		if(DEBUG){
			Crawler crawler = new AuthorCrawler();
			crawler.crawl();
		}else{	
//			Crawler artiCrawler = new ArticleCrawler();
//			artiCrawler.crawl();
			
		}
	}
}
