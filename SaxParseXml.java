
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParseXml extends DefaultHandler{

   
    private List<Article> list;
    private List<Conference> list2;
   
    private Article article;
    private Conference conference;
  
    private String tagName;
    //article or conference(0,1)
    private int choice = -1;
    public Connection conn;
    public String s = "create table Article(pubkey varchar(500) PRIMARY KEY, title varchar(2000), journal varchar(500), year int);";
    public String s1 = "create table Inproceedings(pubkey varchar(500) PRIMARY KEY, title varchar(2000), booktitle varchar(2000), year int);";
    public String s2 = "create table Authorship(pubkey varchar(500), authorname varchar(500), PRIMARY KEY(pubkey, authorname));";
    public String s3 = "set client_encoding to 'UTF8';";
    
    public String ss = "INSERT INTO Article(pubkey, title, journal, year) VALUES(?,?,?,?);"; 
    public String ss2 = "INSERT INTO Inproceedings(pubkey, title, booktitle, year) VALUES(?,?,?,?);"; 
    public String ss3 = "INSERT INTO Authorship(pubkey, authorname) VALUES(?,?);";
    
    public long numArticle = 0, numConference = 0, numAuthor = 0; //how many tuples in each table
    public long line = 0; //how many lines
    public StringBuffer str = null;
    
    public SaxParseXml(){
    	conn = JDBC.getconn();
    	try {
    		//PreparedStatement statement4 = conn.prepareStatement(s3);
			//statement4.execute();
			PreparedStatement statement = conn.prepareStatement(s);
			statement.executeUpdate();
			PreparedStatement statement2 = conn.prepareStatement(s1);
			statement2.executeUpdate();
			PreparedStatement statement3 = conn.prepareStatement(s2);
			statement3.executeUpdate();
			
			//System.out.println("line");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    
    public List<Article> getList() {
        return list;
    }
    
    public List<Conference> getList2() {
        return list2;
    }


    public void setList(List<Article> list) {
        this.list = list;
    }
    
    public void setList2(List<Conference> list) {
        this.list2 = list;
    }


    public Article getArticle() {
        return article;
    }
    
    public Conference getConference() {
        return conference;
    }


    public void setArticle(Article student) {
        this.article = student;
    }
    
    public void setConference(Conference conference) {
        this.conference = conference;
    }


    public String getTagName() {
        return tagName;
    }


    public void setTagName(String tagName) {
        this.tagName = tagName;
    }


  
    @Override
    public void startDocument() throws SAXException {
        list=new ArrayList<Article>();
        list2=new ArrayList<Conference>();
    }
    

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
    	str = new StringBuffer(); 
        if(qName.equals("article")){
            article=new Article();
            
            article.setKey(attributes.getValue("key").trim());
            choice = 0;
        }
        else if(qName.equals("inproceedings")){
            conference=new Conference();
           
            conference.setKey(attributes.getValue("key").trim());
            choice = 1;
        }
        //else if(qName.equals("book")||qName.equals("incollection")||qName.equals("phdthesis")||qName.equals("mastersthesis")||qName.equals("www")){
        	//choice = -1;
        //}
        this.tagName=qName;
    }
    
    
    //���ö��  
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if(qName.equals("article")){
        	choice =-1;
            //this.list.add(this.article); ////////////
            List<String> authors = article.getAuthor();
            try {
    			PreparedStatement s1 = conn.prepareStatement(ss);
    			s1.setString(1,article.getKey());
    			s1.setString(2,article.getTitle());
    			s1.setString(3,article.getJournal());
    			s1.setInt(4, article.getYear());
    			s1.execute();
    			numArticle ++;
    			line ++;
    			if(line%100000==0){
    			System.out.println("line: "+line);
    			}
    			if(authors.size()>0){
    			for(String a:authors){
    				PreparedStatement forauthor = conn.prepareStatement(ss3);
    				forauthor.setString(1,article.getKey());
    				forauthor.setString(2,a);
    				forauthor.execute();
    				numAuthor ++;
    				line ++;
    				if(line%100000==0){
        			System.out.println("line: "+line);
    				}
    			}
    			}
    			//System.out.println("line");
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
        else if(qName.equals("inproceedings")){
            //this.list2.add(this.conference); ////////////////////
        	choice =-1;
            List<String> authors = conference.getAuthor();
           try {
    			PreparedStatement s1 = conn.prepareStatement(ss2);
    			s1.setString(1,conference.getKey());
    			s1.setString(2,conference.getTitle());
    			s1.setString(3,conference.getBooktitle());
    			s1.setInt(4, conference.getYear());
    			s1.execute();
    			numConference ++;
    			line ++;
    			if(line%100000==0){
    			System.out.println("line: "+line);
    			}
    			//System.out.println("line");
    			if(authors.size()>0){
    			for(String a:authors){
    				PreparedStatement forauthor = conn.prepareStatement(ss3);
    				forauthor.setString(1,conference.getKey());
    				forauthor.setString(2,a);
    				forauthor.execute();
    				numAuthor ++;
    				line ++;
    				if(line%100000==0){
        			System.out.println("line: "+line);
    				}
    			}
    			}
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
        
        else if(qName.equals("author")||qName.equals("booktitle")){
        	String data=str.toString();
        	if(qName.equals("author")){
        	if(choice==0){
        	  this.article.addAuthor(data);
        	}
        	else if(choice==1){
        	  this.conference.addAuthor(data);
        	}
        	}
        	else{
              	if(choice==1){
              		this.conference.setBooktitle(data);
              	}
        	}
        }
        
        this.tagName=null;
    }
    
    
   
    @Override
    public void endDocument() throws SAXException {
    }
    
   
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {  
        if(this.tagName != null){
            String date=new String(ch,start,length).trim();
            if(choice == 0){
            	if(this.tagName.equals("title")){
                    this.article.setTitle(date);
                }
                else if(this.tagName.equals("journal")){
                    this.article.setJournal(date);
                }
                else if(this.tagName.equals("year")){
                    this.article.setYear(Integer.parseInt(date));
                }
                else if(this.tagName.equals("author")){
                    str.append(ch, start, length);
                    //this.article.addAuthor(date);
                }
            }
            else if(choice == 1){
            	if(this.tagName.equals("title")){
                    this.conference.setTitle(date);
                }
                else if(this.tagName.equals("booktitle")){
                	str.append(ch, start, length);
               
                }
                else if(this.tagName.equals("year")){
                    this.conference.setYear(Integer.parseInt(date));
                }
                else if(this.tagName.equals("author")){
                	str.append(ch, start, length);
                    //this.conference.addAuthor(date);
                }
            }
        }
    }
}