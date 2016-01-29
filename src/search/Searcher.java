package search;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

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
		for (String host : hosts) {
			InetSocketTransportAddress ista = new InetSocketTransportAddress(InetAddress.getByName(host)
					, 9300);
			client = client.addTransportAddress(ista);
		}
		System.out.println("-----------------------------------");
	}

	public void clientShutDown () {
		client.close();
	}


	public Article[] search (String query, double nameW, double abstractW, double authorW) {
		String nw = Double.toString(nameW);
		String abw = Double.toString(abstractW);
		String auw = Double.toString(authorW);
		SearchResponse response = client.prepareSearch("researchgate")
		        .setTypes("article")
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(QueryBuilders.multiMatchQuery(query, "name^" + nw
		        		, "abstraction^" + abw, "author^" + auw))// Query
		        .setFrom(0).setSize(this.resultNumber).setExplain(false)
		        .execute()
		        .actionGet();
		Article[] result = new Article[this.resultNumber];
		return null;
	}
	
	
}
