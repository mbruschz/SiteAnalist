package siteanalist.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import siteanalist.control.Analyser;

public class Main {

    public static void main(String args[]) {
        String URL;
        
        if(args == null || args.length == 0){
            URL = (String) JOptionPane.showInputDialog(null, "Enter URL:", "Analyse",JOptionPane.PLAIN_MESSAGE, null,null,null);         
        }else{
            URL = args[0];
        } 
          
        if (URL.isEmpty()){
            System.err.println("A URL is required! Exiting!");
            System.exit(0);
        }
        
        Analyser analyser = new Analyser();
        String response, protocol, version, domain;
        try {
            analyser.connect(URL);
            JOptionPane.showMessageDialog(null, "Analysing: "+URL+"\n\n"+
                                                "HTML Version: "+analyser.getHtmlVersion()+"\n"+
                                                "Page Title: "+analyser.getTitle()+"\n"+
                                                "External Links: "+analyser.getExternalLinks().size()+"\n"+
                                                "Internal Links: "+analyser.getInternalLinks().size()+"\n"
                                                ,"Analyser",JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid URL!", "Error",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
