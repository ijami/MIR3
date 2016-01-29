package article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import com.google.gson.Gson;

public class ArticleStorage {
	
	String path;
	File articleFolder;
	Article[] articleRepo;
	
	public ArticleStorage(String path) {
		this.path = path;
		articleFolder = new File(path);
		if(!articleFolder.exists())
			articleFolder.mkdirs();
	}
	
	public void saveArticle(Article article){
		String serializedArticle = getJson(article);
		saveJsonToFile(serializedArticle, path + article.getId() + ".json");
	}
	
	private void saveJsonToFile(String serializedArticle, String fileName) {
		try {
			PrintWriter writer = new PrintWriter(new File(fileName));
			writer.println(serializedArticle);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String getJson(Article article){
		Gson gson = new Gson();
		String serializedArticle = gson.toJson(article);
		return serializedArticle;
	}

	private Article getArticleFromJson(String json){
		Gson gson = new Gson();
		Article article = gson.fromJson(json, Article.class);
		return article;
	}
	
	public int numberOfArticles(){
		return articleFolder.listFiles().length;
	}
	
	public Article[] getArticlesFromRepo(){
		File[] files = articleFolder.listFiles();
		articleRepo = new Article[files.length];
		for (int i = 0; i < files.length; i ++) {
			Scanner inp = null;
			try {
				inp = new Scanner(files[i]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String json = inp.nextLine();
			articleRepo[i] = getArticleFromJson(json);
		}
		return articleRepo;
	}

}
