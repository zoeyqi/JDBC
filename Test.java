
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class Test {
    

	public static void main(String[] args) {
        SAXParser parser = null;
        //Connection conn = JDBC.getconn();
        //System.setProperty("entityExpansionLimit", "2500000");
       
        try {
            
            parser = SAXParserFactory.newInstance().newSAXParser();
            
            SaxParseXml parseXml=new SaxParseXml();
            
            InputStream stream=SaxParseXml.class.getClassLoader().getResourceAsStream("dblp.xml");
            
            parser.parse(stream, parseXml);
            
            /*List<Article> list=parseXml.getList();
            List<Conference> list2=parseXml.getList2();
            for(Article student:list){
                System.out.println("key:"+student.getKey()+"\ttitle:"+student.getTitle()+"\tjournal:"+student.getJournal()+"\tyear:"+student.getYear());
                List<String> authors = student.getAuthor();
                for(String a:authors)
                System.out.println("author: "+a);
            }
            for(Conference student:list2){
                System.out.println("key:"+student.getKey()+"\ttitle:"+student.getTitle()+"\tbooktitle:"+student.getBooktitle()+"\tyear:"+student.getYear());
                List<String> authors = student.getAuthor();
                for(String a:authors)
                System.out.println("author: "+a);
            }*/
            long numArticle = parseXml.numArticle, numConference = parseXml.numConference, numAuthor = parseXml.numAuthor;
            System.out.println("article: "+numArticle);
            System.out.println("conference: "+numConference);
            System.out.println("author: "+numAuthor);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

