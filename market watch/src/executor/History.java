/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package executor;

import loader.NSELoader;
import rhino.Analyser;

/**
 *
 * @author arsh
 */
public class History {
      public static void main(String arg[]) throws InterruptedException{
       
          String stocks[]= new NSELoader(1000).getAllStocks();
       Analyser analyser = new Analyser(stocks);
      MarketWatch.Client.main(arg);
    }
}
