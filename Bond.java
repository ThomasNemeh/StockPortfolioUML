import java.text.DecimalFormat;
import java.util.ArrayList;

public class Bond {

   public String bondID;
   public String date;
   public int freq;
   public String type;
   public int term;
   public double couponVal, investmentVal, price;
   public ArrayList<Payment> payments;

   public Bond(String bondID, String date, int freq, String type, double couponVal, double investmentVal, double price, int term){
       this.bondID = bondID;
       this.date = date;
       this.freq = freq;
       this.type = type;
       this.couponVal = couponVal;
       this.investmentVal = investmentVal;
       this.price = price;
       this.term = term;
       payments = new ArrayList<Payment>();
   }

   public double computePayout(){

       	double sum = 0;
        for (int i = 0; i < term; i++){
        	sum += couponVal;
        }
        sum += investmentVal;
        return sum;
       
   }

   public double computeValue(double r){

       double result = 0;
       
           for (int i = 1; i <= term-1; i++){
               result += couponVal/Math.pow(1+r, i);
           }
           result += (investmentVal + couponVal)/Math.pow(1+r, term);
           return result;
       
   }

   public double computeMacaulay(double r){

       double result = 0;

       for (int i = 1; i <= term; i++){
           result += i*couponVal/Math.pow(1+r, i);
       }

       result = (result+(term*investmentVal)/Math.pow(1+r, term))/computeValue(r);
       return result;
   }
   
   public double  computePrice(double r) {
		return (couponVal * (Math.pow(1 + r, term) - 1) / (r * Math.pow(1+r, term)) + 100/Math.pow(1+r, term));
	}
	
	public double computeIRR() {
		double upper = 0;
		double lower = 100;
		double target = 50;
		double guessPrice = computePrice(target);
		while (Math.abs(guessPrice - price) > .00001) {
			if (guessPrice < price) {
				lower = target;
			}
			else {
				upper = target;
			}
			target = (upper + lower) / 2;
			guessPrice = computePrice(target);
		}
		DecimalFormat numberFormat = new DecimalFormat("#.0000");
		return Double.parseDouble(numberFormat.format(target));
	}
	
	public void computePayment() {
		if (payments.size() == term - 1) {
			Payment p = new Payment(term, couponVal + investmentVal);
			payments.add(p);
		}
		else {
			Payment p = new Payment(term, couponVal);
			payments.add(p);
		}
		
	}
}
