package search;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import article.Article;

public class Searcher {
	
	private TransportClient client;
	private int resultNumber;
	
	public Searcher(int resultNumber) {
		this.resultNumber = resultNumber;
	}
	
	public void setResultNumber(int resultNumber) {
		this.resultNumber = resultNumber;
	}
	
	public int getResultNumber() {
		return resultNumber;
	}
	
	
	public void clientStartUp (ArrayList<String> hosts) throws UnknownHostException {
		Settings settings = Settings.settingsBuilder().build();
		Client c = TransportClient.builder().settings(settings).build();
		client = (TransportClient) c;
		System.out.println(client.nodeName());
//		for (String host : hosts) {
			InetSocketTransportAddress ista = new InetSocketTransportAddress(InetAddress.getByName("localhost")
					, 9300);
			client = client.addTransportAddress(ista);
//		}
		System.out.println("-----------------------------------");
	}

	public void clientShutDown () {
		client.close();
	}


	public Article[] search (String query, double nameW, double abstractW, double authorW) {
		System.out.println(query);
		String nw = Double.toString(nameW);
		String abw = Double.toString(abstractW);
		String auw = Double.toString(authorW);
		System.err.println(query);
		System.err.println(nw);
		System.err.println(abw);
		System.err.println(auw);
		SearchResponse response = client.prepareSearch("researchgate")
		        .setTypes("article")
		        .setSearchType(SearchType.DEFAULT)
		        .setQuery(QueryBuilders.multiMatchQuery(query, 
//		        		"name^" + nw
//		        		,
		        		"abstraction^" + abw
		        		,
		        		"authors^" + auw))// Query
		        .setFrom(0).setSize(this.resultNumber).setExplain(false)
		        .execute()
		        .actionGet();
//		System.out.println(response);
		
		Article[] result = new Article[(this.resultNumber < response.getHits().getHits().length) ? this.resultNumber : response.getHits().getHits().length];
		Article[] te = pageRankedSearch(response);
		for (int i = 0; i < resultNumber && i < response.getHits().getHits().length; i++) {
			result[i] = te[i];
		}
		return result;
	}
	
	
	public Article[] pageRankedSearch (SearchResponse response) {
		SearchHit[] arr = response.getHits().getHits();
		Article[] arts = new Article[arr.length];
		ArticlePair[] pairs = new ArticlePair[arr.length];
		Article temp = new Article();
		for (int i = 0; i < arts.length; i++) {
			Article a = temp.getArticleFromJson(arr[i].getSourceAsString());
			pairs[i] = new ArticlePair(a, 
					(double) arr[i].getScore() *
					(2 + a.getPageRank())
					);
		}
		Arrays.sort(pairs);
		Article[] result = new Article[arr.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = pairs[i].article;
		}
		return result;
	}
	
}

class ArticlePair implements Comparable<ArticlePair> {
	Article article;
	double score;
	public ArticlePair(Article a, double s) {
		article = a;
		score = s;
	}
	
	@Override
	public int compareTo(ArticlePair arg0) {
		if (this.score > arg0.score)
			return 1;
		else if (this.score == arg0.score)
			return 0;
		else 
			return -1;
	}
}
