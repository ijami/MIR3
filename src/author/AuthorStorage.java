package author;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.google.gson.Gson;

public class AuthorStorage {
	String path;
	File authorFolder;
	Author[] authorRepo;
	
	public AuthorStorage(String path) {
		this.path = path;
		authorFolder = new File(path);
		if(!authorFolder.exists())
			authorFolder.mkdirs();
	}
	
	public void saveAuthor(Author author){
		String serializedAuthor = getJson(author);
		saveJsonToFile(serializedAuthor, path + author.getId() + ".json");
	}
	
	private void saveJsonToFile(String serializedAuthor, String fileName) {
		try {
			PrintWriter writer = new PrintWriter(new File(fileName));
			writer.println(serializedAuthor);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String getJson(Author author){
		Gson gson = new Gson();
		String serializedAuthor = gson.toJson(author);
		return serializedAuthor;
	}
	
	private Author getAuthorFromJson(String json){
		Gson gson = new Gson();
		Author author = gson.fromJson(json, Author.class);
		return author;
	}
	
	public int numberOfAuthors(){
		return authorFolder.listFiles().length;
	}
	
	public Author[] getAuthorsFromRepo(){
		File[] files = authorFolder.listFiles();
		authorRepo = new Author[files.length];
		for (int i = 0; i < files.length; i ++) {
			Scanner inp = null;
			try {
				inp = new Scanner(files[i]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String json = inp.nextLine();
			authorRepo[i] = getAuthorFromJson(json);
		}
		return authorRepo;
	}
	
}
