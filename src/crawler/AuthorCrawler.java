package crawler;

import author.Author;
import author.AuthorStorage;


public class AuthorCrawler extends Crawler{
	
	private final static String defaultFolderPath = "authors/";
	private final static String logPath = "author-crawler.log";
	private final static int defaultNumberOfCrawlingAuthors = 100;

	private AuthorStorage storage;
	public void setStartUrl(){
		startUrls = new String[1];
		startUrls[0] = "https://www.researchgate.net/researcher/8159937_Zoubin_Ghahramani";
	}
	
	public AuthorCrawler(){
		super(defaultNumberOfCrawlingAuthors, logPath);
		setStartUrl();
		storage = new AuthorStorage(defaultFolderPath);
	}
	
	public AuthorCrawler(String[] start){
		super(defaultNumberOfCrawlingAuthors, logPath);
		startUrls = start;
		storage = new AuthorStorage(defaultFolderPath);
	}

	public AuthorCrawler(int numberOfCrawllingDoc) {
		super(numberOfCrawllingDoc, logPath);
		setStartUrl();
		storage = new AuthorStorage(defaultFolderPath);
	}
	
	public AuthorCrawler(int numberOfCrawllingDoc, String[] start) {
		super(numberOfCrawllingDoc, logPath);
		startUrls = start;
		storage = new AuthorStorage(defaultFolderPath);
	}
	

	@Override
	protected CrawlWorker newCrawlWorker(String url) {
		return new AuthorCrawlWorker(url, this);
	}

	@Override
	public void successfulCrawl(Object author1) {
		Author author = (Author) author1;
		if(storage.numberOfAuthors() >= numberOfCrawllingDoc)
			return;
		numCrawled ++;
		storage.saveAuthor(author);
		for (String string : author.getNextUrls()) {
			scheduler.addUrl(string);
		}
		log("Author " + numCrawled + " crawled!");
		log(author.toString());
	}

	@Override
	public void unsuccessfulCrawl(String url, Exception e) {
		String log = "";
		log += "*************************************************error accured***************************************\n";
		log += "url is " + url + "\n";
		log += e.toString() + "\n";
		for (StackTraceElement element : e.getStackTrace()) {
			log += element.toString() + "\n";
		}
		log(log);
		num_error ++;
	}

}
