package siteanalist.control;

import java.io.IOException;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class Analyser {
    private final int EXTERNAL_LINKS = 1;
    private final int INTERNAL_LINKS = 2;
    private final int ALL_LINKS = 3;
   
    private Document doc;
    private URI url;
    
    
    public void connect(String url) throws URISyntaxException, IOException{
        if(!url.contains("http://") && !url.contains("https://"))
            url = "http://"+url; //adiciona o protocolo caso nao tenha sido informado.
        
        this.url = new URI(url);
        doc = Jsoup.connect(url).timeout(60*1000).get();
    }
    
    public String getTitle(){
        return doc.title();
    }
    
    private String getDomainName() {
        String domain = url.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    private Elements getLinks(int option) {
        Elements links = doc.select("a[href]");
        Elements linksRemove = new Elements();
        for (Element link : links){
            if ((link.attr("abs:href").contains(getDomainName()) && option == EXTERNAL_LINKS)
                ||(!link.attr("abs:href").contains(getDomainName()) && option == INTERNAL_LINKS))
                linksRemove.add(link);
        }
        links.removeAll(linksRemove);
        return links;
    }
    
    public Elements getExternalLinks(){
        return getLinks(EXTERNAL_LINKS);
    }

    public Elements getInternalLinks(){
        return getLinks(INTERNAL_LINKS);
    }
    
    public Elements getAllLinks(){
        return getLinks(ALL_LINKS);
    }

    private String extractVersion(String DTD){
        if (DTD.isEmpty())
            return "";
        
        int beginIndex = DTD.indexOf("//DTD");
        beginIndex += 5;
        int endIndex = DTD.indexOf("//",beginIndex);
        
        return DTD.substring(beginIndex,endIndex).trim();
    }
    
    public String getHtmlVersion() { 
        List<Node>nods = doc.childNodes();
         for (Node node : nods) {
            if (node instanceof DocumentType) {
                DocumentType documentType = (DocumentType)node;
                String version = extractVersion(documentType.attr("publicid"));
                if(version.isEmpty())
                    return "HTML5";
                return version;
            }
        }
        return "NULL";
    }
}
