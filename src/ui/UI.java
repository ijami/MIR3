package ui;

import crawler.Crawler;

public class UI {
	
	private static final boolean DEBUG = false;

	public static void main(String[] args) {
		if(DEBUG){
			;
		}else{
			Crawler crawler = new Crawler();
			crawler.crawl();
		}
	}

}
