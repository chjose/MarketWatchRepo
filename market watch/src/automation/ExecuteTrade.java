/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arsh
 */
public class ExecuteTrade {
    private static boolean newCall=false;
     private static  List<String> call=new ArrayList<>();
    public static String getCall(){
        while(call.isEmpty()){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecuteTrade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String callString=call.get(0);
        call.remove(0);
        return new String(callString);
    }
    public static synchronized void executeSellOrder(String scrip,int quantity,float price,float target,float stopLoss){
       BigDecimal bd = new BigDecimal(target);
    BigDecimal TARGET = bd.setScale(2, RoundingMode.UP);
       call.add("Sell Nse "+scrip+" "+quantity+" "+roundOFF(price)+" "+TARGET.doubleValue()+" "+roundOFF(stopLoss));
       newCall=true;
    }
    private static String roundOFF(float value){
        BigDecimal bd = new BigDecimal(value);
        BigDecimal TARGET = bd.setScale(2, RoundingMode.UP);
        return ""+TARGET.doubleValue();
    }
}
