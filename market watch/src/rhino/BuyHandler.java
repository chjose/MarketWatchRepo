/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rhino;

/**
 *
 * @author marshed
 */
public class BuyHandler extends EquityProcessor implements Runnable{
    private boolean swicthOFF=false;
    float purchasePrice;
    static float totalProfit=0;
    //it triggers squareoff  call
    public BuyHandler(String StockName,float purchasePrice){
       name=StockName;
       this.purchasePrice=purchasePrice;
       new Thread(this).start();
    }

    @Override
    public void run() {
        process();
    }

    private void process() {
        while(!swicthOFF){
            checkForSellPattern();
        }
    }

    private void checkForSellPattern() {
        if(isUpdated()){
            if( checkForFlatPrice()){
                triggerSell();
            }
        }
    }
    private boolean checkForFlatPrice(){
        //its the condition where the price is unchanged for x mins
        int lastInstance = priceDetails.size()-1;
        int sampleCount = Configuration.maxFlatTime;
        int count=(lastInstance+1)-sampleCount;
        if(count<0) return false;
        if(count>lastInstance) return false;
        float prices[] = new float[sampleCount];
        for(int i=0;count<=lastInstance;count++){
            prices[i++] = getPriceForInstance(count);
        }
        float priceChange=bottomTopPercentageDifference(prices[0], prices[sampleCount-1]);
        if(priceChange> Configuration.idealHoldPercentage){
            return false;
        }
        if(priceChange<0&&priceChange> Configuration.maxAllowedLoss){
            float currentProfit=bottomTopPercentageDifference(purchasePrice, getLastPrice());
            if(currentProfit>.5){
                return true;//sell
            }
            else if(currentProfit<Configuration.maxAllowedLoss){
                return true;
            }
        }
         int totalEquals=0;
         for(int i=0;i<Configuration.maxFlatTime-1;i++){
           
             if(negligiblyEqual(prices[i],prices[i+1])){
                totalEquals++;
            }
             if(totalEquals>=Configuration.maxFlatTime/2){
                 return true;
             }
            
        }
         return false;
        
    }
    int addTime(int t1,int min){
        int sum=t1+min;
        if(sum%100>60){
            int extra = (sum%100)-60;
            int hour=t1/100;
            hour=hour+1;
            hour=(hour*100)+extra;
        }
        return sum;
    }

    private void triggerSell() {
        System.out.println("square of" + name +" bought price :"+purchasePrice+" sold price:"+getLastPrice()+ "profit :"+bottomTopPercentageDifference(purchasePrice, getLastPrice()));
        totalProfit+=bottomTopPercentageDifference(purchasePrice, getLastPrice());
        System.out.println("current profit :"+totalProfit);
        swicthOFF = true;
    }

    private boolean checkForPriceDip() {
        
       float diff = bottomTopPercentageDifference(getPriceForInstance(priceDetails.size()-totalInstance),getLastPrice());
       if(diff>=1){
           return true;
       }
       else{
           return false;
       }
    }
}
