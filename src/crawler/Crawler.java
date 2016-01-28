package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import article.Article;
import article.ArticleStorage;

public class Crawler {
	private static final int numberOfCrawllingDoc = 1000;
	private static final int numberOfAddedUrl = 10;
	private static final String ARTICLEFOLDERSPATH = "articles/";
	private static final String CRAWLLOGPATH = "crawl.log";
	
	private static final String[] startUrls = {
		"https://www.researchgate.net/publication/"
		+ "285458515_A_General_Framework_for_Constrained"
		+ "_Bayesian_Optimization_using_Information-based_Search?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "284579255_Parallel_Predictive_Entropy_Search_for"
		+ "_Batch_Global_Optimization_of_Expensive_Objective_Functions?ev=auth_pub", 
		"https://www.researchgate.net/publication/"
		+ "283658712_Sandwiching_the_marginal_likelihood_"
		+ "using_bidirectional_Monte_Carlo?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "281895707_Dirichlet_Fragmentation_Processes?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "273488773_Variational_Infinite_Hidden_Conditional_Random_Fields?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "279633530_Subsampling-Based_Approximate_Monte_"
		+ "Carlo_for_Discrete_Distributions?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "279309917_An_Empirical_Study_of_Stochastic_Variational_Algorithms"
		+ "_for_the_Beta_Bernoulli_Process?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "278332447_MCMC_for_Variationally_Sparse_Gaussian_Processes?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "278048012_Neural_Adaptive_Sequential_Monte_Carlo?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "277959103_Dropout_as_a_Bayesian_Approximation_Appendix?ev=auth_pub"
	};
	
	private Scheduler scheduler;
	private ArticleStorage storage;
	private PrintWriter logWriter;
	private ExecutorService executor;
	private int numCrawled = 0;
	private int num_error = 0;
	
	public Crawler() {
		scheduler = new Scheduler();
		storage = new ArticleStorage(ARTICLEFOLDERSPATH);
		numCrawled = 0;
		executor = Executors.newFixedThreadPool(30);
		try {
			logWriter = new PrintWriter(new File(CRAWLLOGPATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void crawl(){
		
		for (String string : startUrls)
			scheduler.addUrl(string);
		 
		while(storage.numberOfArticles() < numberOfCrawllingDoc){
			if(scheduler.size() > 0){
				System.err.println("number of articles = " + storage.numberOfArticles());
				String url = scheduler.nextUrl();
				executor.execute(new CrawlWorker(url, this));
			}
		}
		executor.shutdownNow();
		System.out.println("Crawling ends successfully with " + num_error + " errors. " + numCrawled + " article was crawled! :)");
	}
	
	synchronized public void successfulCrawl(Article article){
		numCrawled ++;
		storage.saveArticle(article);
		int i = 0;
		for (String string : article.getCitedInUrls()) {
			scheduler.addUrl(string);
			i++;
			if(i == numberOfAddedUrl)
				break;
		}
		i = 0;
		for (String string : article.getRefrenceUrls()) {
			scheduler.addUrl(string);
			i++;
			if(i == numberOfAddedUrl)
				break;
		}
		log("Article " + numCrawled + " crawled!");
		log(article.toString());
	}
	
	synchronized public void unsuccessfulCrawl(String url, Exception e){
		log("*************************************************error accured***************************************");
		log("url is " + url);
		for (StackTraceElement element : e.getStackTrace()) {
			log(element.toString());
		}
		num_error ++;
	}
	
	private void log(String log){
		System.err.println(log);
		synchronized (logWriter) {			
			logWriter.println(log);
		}
	}
}

class CrawlWorker implements Runnable{
	String url;
	Crawler crawler;
	
	public CrawlWorker(String url, Crawler crawler) {
		this.url = url;
		this.crawler = crawler;
	}

	@Override
	public void run() {
		Parser parser = new Parser(url);
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
