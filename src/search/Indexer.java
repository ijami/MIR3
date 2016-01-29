package search;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.sun.javafx.collections.MappingChange.Map;

import article.ArticlesReader;

public class Indexer {

	private TransportClient client;
	
	
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
	
	public void makeIndex (String indexName) {
		CreateIndexRequestBuilder createIndexRequestBuilder = 
				client.admin().indices().prepareCreate(indexName);
		createIndexRequestBuilder.execute().actionGet();
	}
	
	
	public boolean indexArticle (String article) {
		IndexResponse response = client.prepareIndex("researchgate", "article")
		        .setSource(article).execute().actionGet();
		System.out.println(response.getContext());
//		// Index name
//		System.out.println(response.getIndex());
//		// Type name
//		System.out.println(response.getType());
//		// Document ID (generated or not)
//		System.out.println(response.getId());
//		// Version (if it's the first time you index this document, you will get: 1)
//		System.out.println(response.getVersion());
//		// isCreated() is true if the document is a new one, false if it has been updated
//		System.out.println(response.isCreated());
		return response.isCreated();
	}
	
	
	public void clientShutDown () {
		client.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException, UnknownHostException {
		Indexer indexer = new Indexer();
		ArrayList<String> arr = new ArrayList();
		arr.add("localhost");
		indexer.clientStartUp(arr);
//		ArticlesReader ar = new ArticlesReader("C:/Users/rafie/Desktop/articles");
//		for (int i = 0; i < 64; i++) {
//			String art = ar.getArticleAsJSON(i);
//			System.out.println(art);
//			indexer.indexArticle(art);
//		}
		String query = "Structured with chance probable";
//		SearchResponse response = indexer.client.prepareSearch().execute().actionGet();
				SearchResponse response = indexer.client.prepareSearch("researchgate")
		        .setTypes("article")
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(QueryBuilders.multiMatchQuery(query, "name^4.3", "abstraction^.2", "author^1"))// Query
		        .setFrom(0).setSize(6).setExplain(false)
		        .execute()
		        .actionGet();
		System.out.println(response);
		SearchHits sh = response.getHits();
//		System.out.println(sh);
	}
	
}
