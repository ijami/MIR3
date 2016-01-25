package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import article.Article;
import article.ArticleStorage;

public class Crawler {
	private static final int numberOfCrawllingDoc = 2000;
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
	private int numCrawled;
	private ArticleStorage storage;
	private PrintWriter logWriter;
	
	public Crawler() {
		scheduler = new Scheduler();
		storage = new ArticleStorage(ARTICLEFOLDERSPATH);
		numCrawled = 0;
		try {
			logWriter = new PrintWriter(new File(CRAWLLOGPATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void crawl(){
		
		for (String string : startUrls) 
			scheduler.addUrl(string);
		
		int num_error = 0;
		while(storage.numberOfArticles() < numberOfCrawllingDoc){
			String url = scheduler.nextUrl();
			Parser parser = new Parser(url);
			Article article = null;
			try{
				article = parser.pars();
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
			}catch(Exception e){
				log("*************************************************error accured***************************************");
				log("url is " + url);
				for (StackTraceElement element : e.getStackTrace()) {
					log(element.toString());
				}
				num_error ++;
			}
		}
		System.out.println("Crawling ends successfully with " + num_error + " errors. " + numCrawled + " article was crawled! :)");
	}
	
	private void log(String log){
		System.err.println(log);
		logWriter.println(log);
	}

}
