/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siteanalist;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import siteanalist.control.*;

/**
 *
 * @author mbruschz
 */
public class AnalyserTest {
    Analyser analyser;
    String URL = "https://spigotdesign.com/";
    
    public AnalyserTest() {
    }

    @Test
    public void connectionTest() {
        analyser = new Analyser();
        try {
            analyser.connect(URL);
            Assert.assertNotEquals("",analyser.getTitle());
        } catch (IOException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        analyser = null;
    }
    
    @Test(expected = URISyntaxException.class)
    public void connectionURISyntaxExceptionTest() throws URISyntaxException, IOException {
        analyser = new Analyser();
        analyser.connect("url incorreta");
        Assert.assertNotEquals("",analyser.getTitle());
        analyser = null;
    }
    
    @Test
    public void getTitleTest() {
        analyser = new Analyser();
        try {
            analyser.connect(URL);
            Assert.assertNotEquals("",analyser.getTitle());
        } catch (IOException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        analyser = null;
    }
    
    @Test
    public void getExternalLinksTest() {
        analyser = new Analyser();
        try {
            analyser.connect(URL);
            Assert.assertNotEquals(0,analyser.getExternalLinks().size());
        } catch (IOException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        analyser = null;
    }
    
    @Test
    public void getInternalLinksTest() {
        analyser = new Analyser();
        try {
            analyser.connect(URL);
            Assert.assertNotEquals(0,analyser.getInternalLinks().size());
        } catch (IOException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        analyser = null;
    }
    
    @Test
    public void getHtmlVersionTest() {
        analyser = new Analyser();
        try {
            analyser.connect("https://www.w3.org/1999/xhtml/");
            Assert.assertEquals("XHTML+RDFa 1.0",analyser.getHtmlVersion());
            
            analyser.connect("https://www.w3.org/TR/html401/");
            Assert.assertEquals("HTML 4.01 Transitional",analyser.getHtmlVersion());
            
            analyser.connect("http://neware.com.br/downloads.php");
            Assert.assertEquals("HTML5",analyser.getHtmlVersion());
            
        } catch (IOException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AnalyserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        analyser = null;
    }
}
