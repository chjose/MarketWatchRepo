/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

/**
 *
 * @author arsh
 */
public class NSEHelper implements Serializable,Runnable {

    ArrayList<Float> list;

    public NSEHelper(ArrayList<Float> list) {
        this.list = list;
    }
    public NSEHelper() {
    
    }
    public ArrayList<Float> getValues() {
        return list;
    }
    public static ArrayList<Float> nseValues;


    static DocumentBuilder db;
    static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    static {
        try {
            db = dbf.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            System.err.println("loading data failed for nse");
        }
    }
    public static void update(){
        InputStream stream = null;
            Document doc = null;
            try {
                String url = "http://money.rediff.com/money1/chart_nse.php";
                stream = new URL(url).openStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                try {
                    db = dbf.newDocumentBuilder();

                } catch (ParserConfigurationException ex) {
                }


                doc = db.parse(stream);

            } catch (Exception ex) {
            } finally {
                try {
                    stream.close();
                } catch (IOException ex) {
                    Logger.getLogger(RediffParser.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            String values[] = doc.getFirstChild().getTextContent().split("\n");
            nseValues = new ArrayList<>();
            for (int i = 31; i < values.length; i++) {
                nseValues.add(Float.parseFloat(values[i]));

            }
    }
    public static synchronized float getNseValue(float time) {
        time = time * 2;
        if (nseValues == null) {
           update();
        }

        if (nseValues.size() <= time) {
            return nseValues.get(nseValues.size() - 1);
        } else {
            return nseValues.get((int) time);
        }

    }

    @Override
    public void run() {
        while(true){
            try {
                update();
                Thread.sleep(20*10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NSEHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
