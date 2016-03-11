import java.util.ArrayList;
import java.util.List;

public class Article {
    
    private String key;
    private String title;
    private String journal;
    private int year;
    private ArrayList<String> authors;
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
 
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getJournal() {
        return journal;
    }
    
    public void setJournal(String journal) {
        this.journal = journal;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public List<String> getAuthor(){
    	return authors;
    }
    
    public void addAuthor(String author) {
    	if(!this.authors.contains(author)){
        this.authors.add(author);
    	}
    }
    
    public Article(){
    	key="";
    	title="";
    	year=0;
    	journal="";
    	authors = new ArrayList<String>();
    }
  
}