package crawler;

import article.Article;

public class Crawler {
	private static final int numberOfCrawllingDoc = 11;
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
		+ "279848500_mcclustext-manual?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "279848498_mcclustext_10tar?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "279633530_Subsampling-Based_Approximate_Monte_"
		+ "Carlo_for_Discrete_Distributions?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "279309917_An_Empirical_Study_of_Stochastic_Variational_Algorithms"
		+ "_for_the_Beta_Bernoulli_Process?ev=auth_pub",
		"https://www.researchgate.net/publication/"
		+ "278332447_MCMC_for_Variationally_Sparse_Gaussian_Processes?ev=auth_pub"
	};
	
	private Scheduler scheduler;
	private int numCrawled;
	
	public Crawler() {
		scheduler = new Scheduler();
		numCrawled = 0;
	}
	
	public void crawl(){
		for (String string : startUrls) 
			scheduler.addUrl(string);
		
		int num_error = 0;
		while(numCrawled < numberOfCrawllingDoc){
			String url = scheduler.nextUrl();
			Parser parser = new Parser(url);
			Article article = null;
			try{
				article = parser.pars();
			}catch(Exception e){
				System.err.println("*************************************************error accured***************************************");
				e.printStackTrace();
				num_error ++;
				continue;
			}
			numCrawled ++;
			for (String string : article.getCitedInUrls()) {
				scheduler.addUrl(string);
			}
			for (String string : article.getRefrenceUrls()) {
				scheduler.addUrl(string);
			}
			System.err.println("Article " + numCrawled + " crawled!");
			System.err.println(article);
		}
		System.out.println("Crawling ends successfully with " + num_error + " errors. " + numCrawled + " article was crawled! :)");
	}

}
