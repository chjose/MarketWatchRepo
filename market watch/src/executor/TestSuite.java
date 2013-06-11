/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package executor;

import loader.NSELoader;
import rhino.Analyser;
import rhino.Configuration;

/**
 *
 * @author arsh
 */
public class TestSuite {
    public static void main(String arg[]){
        String dates[]={"5_5_2013","4_5_2013"};
        String stocks[]= new NSELoader(1000).getAllStocks();
        for(String date:dates){
            Configuration.dateOnHistory=date;
             
       Analyser analyser = new Analyser(stocks);
        }
    }
}
