/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package executor;

import loader.NSELoader;
import loader.RealTimeFeeder;
import rhino.Analyser;
import test.NSEHelper;

/**
 *
 * @author marshed
 */
public class RealTimeRunner {
    
    public static void  main(String arg[]) throws InterruptedException{
        NSEHelper helper=new NSEHelper();
        helper.update();
        new Thread(helper).start();
        String stocks[]=new NSELoader(100).getAllStocks();
        Analyser analyser = new Analyser(stocks);
        new Thread(new NSEHelper()).start();
        RealTimeFeeder feeder = new RealTimeFeeder();
        feeder.start();
        MarketWatch.Client.main(arg);
    }
}
