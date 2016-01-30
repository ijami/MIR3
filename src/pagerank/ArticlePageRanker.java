package pagerank;

import java.util.ArrayList;
import java.util.HashMap;

import article.Article;
import article.ArticleStorage;
import ui.PagerankStatusBar;

public class ArticlePageRanker {
	
	Article[] articles;
	HashMap<Long, Integer> map;
	PagerankStatusBar progressBar;
	
	public ArticlePageRanker(Article[] articles, PagerankStatusBar bar) {
		this.articles = articles;
		map = new HashMap<Long, Integer>();
		progressBar = bar;
	}
	
	public void pageRank(double alpha, double threashold){
		setMapping();
		int s = articles.length;
		double[][] matrix = new double[s][s];
		for(int i = 0; i < s; i ++){
			matrix[i] = new double[s];
			ArrayList<Long> refrences = articles[i].getRefrenceIDs();
			for (Long long1 : refrences) {
				if(map.containsKey(long1)){
					int index = map.get(long1);
					if(index == i)
						continue;
					matrix[i][index] = 1;
				}
			}
		}
		PageRanker pageRanker = new PageRanker();
		double[] ranks = pageRanker.pageRank(matrix, alpha, threashold);
		for (int i = 0; i < ranks.length; i++)
			articles[i].setPageRank(ranks[i]);
		
		ArticleStorage storate = new ArticleStorage("sag/");
		for (int i = 0; i < articles.length; i++) {
			storate.saveArticle(articles[i]);
			progressBar.getProgressBar().setValue(100 * (i + 1) / s);
			progressBar.getFrame().repaint();
		}
		
	}

	private void setMapping() {
		for (int i = 0; i < articles.length; i++)
			map.put(articles[i].getId(), i);
	}

}
