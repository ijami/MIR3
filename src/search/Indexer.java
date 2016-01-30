package search;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import article.Article;
import article.ArticleStorage;
import ui.IndexingStatusBar;


public class Indexer {

	private TransportClient client;
	
	
	public void clientStartUp (ArrayList<String> hosts) throws UnknownHostException {
		Settings settings = Settings.settingsBuilder().build();
		Client c = TransportClient.builder().settings(settings).build();
		client = (TransportClient) c;
		InetSocketTransportAddress ista = new InetSocketTransportAddress(InetAddress.getByName("localhost")
					, 9300);
		client = client.addTransportAddress(ista);
	}
	
	public void makeIndex (String indexName) {
		CreateIndexRequestBuilder createIndexRequestBuilder = 
				client.admin().indices().prepareCreate(indexName);
		createIndexRequestBuilder.execute().actionGet();
	}
	
	
	public void indexArticle (String article) {
//		IndexResponse response = 
				client.prepareIndex("researchgate", "article")
		        .setSource(article).execute().actionGet();
	}
	
	public void indexCorpus (String path, IndexingStatusBar bar) {
//		try {
//			FileUtils.deleteDirectory(new File("C:/Users/rafie/workspace/lib/elasticsearch-2.1.1/data/elasticsearch/nodes/0/indices"));
//			FileUtils.deleteDirectory(new File("C:/Users/rafie/workspace/lib/elasticsearch-2.1.1/data/elasticsearch/nodes/1/indices"));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try {
			clientStartUp(null);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArticleStorage as = new ArticleStorage(path);
		Article[] articles = as.getArticlesFromRepo();
		bar.setTotal(articles.length);
		for (Article article : articles) {
			indexArticle(as.getJson(article));
			bar.progress();
			bar.getProgressBar().setValue(100 * bar.getCounter() / bar.getTotal());
			bar.getFrame().repaint();
		}
	}
	
	
	public void clientShutDown () {
		client.close();
	}
	
}
