/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rhino;

import java.util.ArrayList;

/**
 *
 * @author marshed
 */
public class EquityProcessor {

    protected String name;
    protected ArrayList<String> priceDetails;
    protected int lastInstanceTime = 0;
    protected int totalInstance;
    protected boolean isDebugging=Configuration.debugging;
    public EquityProcessor(){
        
    }
    boolean negligiblyEqualorGreater(float p1, float p2) {
        //percentage difference between the instance
        if (p1 < p2) {
            return true;
        }
        float negligibleDiff = (p2 * Configuration.negligiblePercentage) / 100;
        if (p2 + negligibleDiff >= p1) {
            return true;
        } else {
            return false;
        }
    }

    boolean negligiblyEqualorLesser(float p1, float p2) {
        //percentage difference between the instance
        if (p1 > p2) {
            return true;
        }
        float negligibleDiff = (p2 * Configuration.negligiblePercentage) / 100;
        if (p2 - negligibleDiff < p1) {
            return true;
        } else {
            return false;
        }
    }

    float bottomTopPercentageDifference(float p1, float p2) {
        float percentage = (p1 / p2) * 100;
        return 100 - percentage;

    }

    boolean isUpdated() {
        String lastPriceDetails = priceDetails.get(priceDetails.size() - 1);
        int updatedTime = Integer.parseInt(lastPriceDetails.split(":")[0]);
        if (updatedTime > lastInstanceTime) {
            return true;
        } else {
            return false;
        }
    }

    float getLastPrice() {
        return getPriceForInstance(priceDetails.size() - 1);
    }

    int getLastInstance() {
        int lastInstance = priceDetails.size() - 1;
        if (lastInstance < 0) {
            return 0;
        }
        String value = priceDetails.get(lastInstance);
        return Integer.parseInt(value.split(":")[0]);
    }

    float getPriceForInstance(int instance) {
        String lastPriceDetails = priceDetails.get(instance);
        //System.err.println("last price "+lastPriceDetails+" ");
        lastPriceDetails=lastPriceDetails.replaceAll(",", "");
        float lastPrice = Float.parseFloat(lastPriceDetails.split(":")[1]);
        return lastPrice;
    }
    float getOpenPrice(){
        return getPriceForInstance(0);
    }
    boolean negligiblyEqual(float p1, float p2) {
        //percentage difference between the instance
        if (p2 > p1) {
            float t = p1;
            p1 = p2;
            p2 = t;
        }
        float negligibleDiff = (p2 * Configuration.negligiblePercentage) / 100;
        if (p2 + negligibleDiff > p1) {
            return true;
        } else {
            return false;
        }
    }
    
    int addTime(int t1,int t2){
        int t1sec=t1%100;
        int t2sec=t2%100;
        int secs=t1sec+t2sec;
        int extra=0;
        if(secs>60){
           extra=1;
           secs=secs-60;
        }
        int t1min=t1/100;
        int t2min=t2/100;
        int mins=(t1+t2+extra)*100;
        return mins+secs;
    }
}
