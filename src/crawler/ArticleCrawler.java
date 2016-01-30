package crawler;

import article.Article;
import article.ArticleStorage;
import ui.CrawlingStatusBar;

public class ArticleCrawler extends Crawler {
	
	private static final int defaultNumberOfCrawllingDoc = 1000;
//	private final int numberOfAddedUrl = 10;
	private final String articlesFolderPath = "articles/";
	private int indeg;
	private int outdeg;
	
	private CrawlingStatusBar progressBar;
	
	public CrawlingStatusBar getProgressBar() {
		return progressBar;
	}

	
	private ArticleStorage storage;
	
	public void setStartUrls(){
		startUrls = new String[10];
		startUrls[0] = "https://www.researchgate.net/publication/"
				+ "285458515_A_General_Framework_for_Constrained"
				+ "_Bayesian_Optimization_using_Information-based_Search?ev=auth_pub"; 
		startUrls[1] = "https://www.researchgate.net/publication/"
				+ "284579255_Parallel_Predictive_Entropy_Search_for"
				+ "_Batch_Global_Optimization_of_Expensive_Objective_Functions?ev=auth_pub";
		startUrls[2] = "https://www.researchgate.net/publication/"
				+ "283658712_Sandwiching_the_marginal_likelihood_"
				+ "using_bidirectional_Monte_Carlo?ev=auth_pub";
		startUrls[3] = "https://www.researchgate.net/publication/"
				+ "281895707_Dirichlet_Fragmentation_Processes?ev=auth_pub";
		startUrls[4] = "https://www.researchgate.net/publication/"
				+ "273488773_Variational_Infinite_Hidden_Conditional_Random_Fields?ev=auth_pub";
		startUrls[5] = "https://www.researchgate.net/publication/"
				+ "279633530_Subsampling-Based_Approximate_Monte_"
				+ "Carlo_for_Discrete_Distributions?ev=auth_pub";
		startUrls[6] = "https://www.researchgate.net/publication/"
				+ "279309917_An_Empirical_Study_of_Stochastic_Variational_Algorithms"
				+ "_for_the_Beta_Bernoulli_Process?ev=auth_pub";
		startUrls[7] = "https://www.researchgate.net/publication/"
				+ "278332447_MCMC_for_Variationally_Sparse_Gaussian_Processes?ev=auth_pub";
		startUrls[8] = "https://www.researchgate.net/publication/"
				+ "278048012_Neural_Adaptive_Sequential_Monte_Carlo?ev=auth_pub";
		startUrls[9] = "https://www.researchgate.net/publication/"
				+ "277959103_Dropout_as_a_Bayesian_Approximation_Appendix?ev=auth_pub";
	}
	
	public ArticleCrawler(CrawlingStatusBar bar) {
		super(defaultNumberOfCrawllingDoc, "article-crawler.log");
		setStartUrls();
		storage = new ArticleStorage(this.articlesFolderPath);
		indeg = 10;
		outdeg = 10;
		this.progressBar = bar;
	}
	
	public ArticleCrawler(int numberOfCrallingDoc, CrawlingStatusBar bar, int i, int o) {
		super(numberOfCrallingDoc, "article-crawler.log");
		setStartUrls();
		indeg = i;
		outdeg = o;
		storage = new ArticleStorage(this.articlesFolderPath);
		this.progressBar = bar;
	}
	
	public ArticleCrawler(String[] startUrls, CrawlingStatusBar bar) {
		super(defaultNumberOfCrawllingDoc, "article-crawler.log");
		this.startUrls = startUrls;
		indeg = 10;
		outdeg = 10;
		storage = new ArticleStorage(this.articlesFolderPath);
		this.progressBar = bar;
	}
	
	public ArticleCrawler(String[] startUrls, int numberOfCrawllingDoc, CrawlingStatusBar bar) {
		super(numberOfCrawllingDoc, "article-crawler.log");
		this.startUrls = startUrls;
		storage = new ArticleStorage(this.articlesFolderPath);
		indeg = 10;
		outdeg = 10;
		this.progressBar = bar;
	}

	public ArticleCrawler(String[] startUrls, int numberOfCrawllingDoc, CrawlingStatusBar bar, int indeg, int outdeg) {
		super(numberOfCrawllingDoc, "article-crawler.log");
		this.startUrls = startUrls;
		storage = new ArticleStorage(this.articlesFolderPath);
		this.indeg = indeg;
		this.outdeg = outdeg;
		this.progressBar = bar;
	}
	
	@Override
	synchronized public void successfulCrawl(Object article1) {
		Article article = (Article) article1;
		if(storage.numberOfArticles() >= this.numberOfCrawllingDoc)
			return;
		
		numCrawled ++;
		this.getProgressBar().getProgressBar().setValue(100 * numCrawled / numberOfCrawllingDoc);
		this.getProgressBar().getFrame().repaint();
		storage.saveArticle(article);
		int i = 0;
		System.err.println(article.getId() + " size is " + article.getCitedInUrls().size());
		for (String string : article.getCitedInUrls()) {
			System.err.println("salam man injam " + i + indeg);
			scheduler.addUrl(string);
			i++;
			if(i == indeg)
				break;
		}
		i = 0;
		System.err.println(article.getId() + " size is " + article.getRefrenceUrls().size());
		for (String string : article.getRefrenceUrls()) {
			System.err.println("salam man injam " + i + indeg);
			scheduler.addUrl(string);
			System.err.println(scheduler.size());
			i++;
			if(i == outdeg)
				break;
		}
		log("Article " + numCrawled + " crawled!");
		log(article.toString());
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


	@Override
	protected CrawlWorker newCrawlWorker(String url) {
		return new ArticleCrawlWorker(url, this);
	}

}
