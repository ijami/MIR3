package pagerank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import article.Article;

public class ArticlePageRanker {
	
	Article[] articles;
	HashMap<Long, Integer> map;
	
	public ArticlePageRanker(Article[] articles) {
		this.articles = articles;
		map = new HashMap<Long, Integer>();
	}
	
	public void pageRank(double alpha, double threashold){
		setMapping();
		int sum = 0;
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
					sum ++;
					matrix[i][index] = 1;
				}
			}
		}
		PageRanker pageRanker = new PageRanker();
		double[] ranks = pageRanker.pageRank(matrix, alpha, threashold);
		for (int i = 0; i < ranks.length; i++)
			articles[i].setPageRank(ranks[i]);
		
	}

	private void setMapping() {
		for (int i = 0; i < articles.length; i++)
			map.put(articles[i].getId(), i);
	}

}
