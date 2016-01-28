package ui;

import pagerank.ArticlePageRanker;
import pagerank.PageRanker;
import article.Article;
import article.ArticleStorage;


public class UI {
	
	private static final boolean DEBUG = false;

	public static void main(String[] args) {
		if(DEBUG){
		}else{
			
			ArticleStorage storage = new ArticleStorage("articles/");
			Article[] articles = storage.getArticlesFromRepo();
			ArticlePageRanker pageRanker = new ArticlePageRanker(articles);
			ArticleStorage rankedStorage = new ArticleStorage("rankedarticles/");
			pageRanker.pageRank(0.1, 0.000000001);
			for (Article article : articles) {
				rankedStorage.saveArticle(article);
			}
			
		}
	}
}
