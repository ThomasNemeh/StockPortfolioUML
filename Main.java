import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static HashMap<String, Investor> portfolioDatabase = new HashMap<String,Investor>();
	public static HashMap<String, Bond> bondDatabase = new HashMap<String,Bond>();
	
   public static void main(String[] args) {
    	//Test Case:
    	Investor Bob =  new Investor("Bob");
    	portfolioDatabase.put("Bob", Bob);
    	inputBondInformation("Bob", "testBond1", 5, "May 21, 1997", "U.K. government Bond", 5, 1, 100, 103);
    	inputBondInformation("Bob", "testBond2", 10, "May 21, 1997", "U.K. government Bond", 4, 1, 100, 95);
    	inputBondInformation("Bob", "testBond2", 20, "May 21, 1997", "U.K. government Bond", 3, 1, 100, 92);
    	inputBondInformation("Bob", "testBond2", 15, "May 21, 1997", "U.K. government Bond", 2, 1, 100, 120);
    	System.out.println("Payouts: ");
    	showPayouts("Bob");
    	System.out.println("Values: (r = .05)");
    	showValues("Bob", .05);
    	System.out.println("Macaulay durations: (r = .05)");
    	showMacaulayDurations("Bob",.05);
    	System.out.println("Values: (r = .02)");
    	showValues("Bob", .02);
    	System.out.println("Macaulay durations: (r = .02)");
    	showMacaulayDurations("Bob",.02);
    	System.out.println("Internal Rate of Returns: ");
    	showInternalRateOfReturns("Bob");
    	
    }

    
    
    public static void inputBondInformation(String investorID, String bondID, String length, String date, String type,
			String couponVal, String frequencyVal, String investmentVal, String priceVal) {
    	if (!portfolioDatabase.containsKey(investorID)) {
    		System.out.println("This investor does not exist");
    	}
    	else {
    		Bond b = new Bond(bondID, date, Integer.parseInt(frequencyVal), type, Double.parseDouble(couponVal), Double.parseDouble(investmentVal), Double.parseDouble(priceVal), Integer.parseInt(length));
    		portfolioDatabase.get(investorID).owned.add(b);
    		bondDatabase.put(bondID, b);
    	}
	}
    
    public static void showPayouts(String investorID) {
    	if (!portfolioDatabase.containsKey(investorID)) {
    		System.out.println("This investor does not exist");
    	}
    	else {
    		Investor investor = portfolioDatabase.get(investorID);
    		for (int x = 0; x < investor.owned.size(); x++) {
    			Bond b = investor.owned.get(x);
    			System.out.println(b.bondID + ": " + b.computePayout());
    		}
    	}
    }
    
    public static void showValues(String investorID, double r) {
    	if (!portfolioDatabase.containsKey(investorID)) {
    		System.out.println("This investor does not exist");
    	}
    	else {
    		Investor investor = portfolioDatabase.get(investorID);
    		for (int x = 0; x < investor.owned.size(); x++) {
    			Bond b = investor.owned.get(x);
    			System.out.println(b.bondID + ": " + b.computeValue(r));
    		}
    	}
    }
    
    public static void showMacaulayDurations(String investorID, double r) {
    	if (!portfolioDatabase.containsKey(investorID)) {
    		System.out.println("This investor does not exist");
    	}
    	else {
    		Investor investor = portfolioDatabase.get(investorID);
    		for (int x = 0; x < investor.owned.size(); x++) {
    			Bond b = investor.owned.get(x);
    			System.out.println(b.bondID + ": " + b.computeMacaulay(r));
    		}
    	}
    }
    
    public static void showInternalRateOfReturns(String investorID) {
    	if (!portfolioDatabase.containsKey(investorID)) {
    		System.out.println("This investor does not exist");
    	}
    	else {
    		Investor investor = portfolioDatabase.get(investorID);
    		for (int x = 0; x < investor.owned.size(); x++) {
    			Bond b = investor.owned.get(x);
    			System.out.println(b.bondID + ": " + b.computeIRR());
    		}
    	}
    }
    
    public static void showBondInformation(String bondID) {
    	if (!bondDatabase.containsKey(bondID)) {
    		System.out.println("This bond does not exist");
    	}
    	else {
    		Bond b = bondDatabase.get(bondID);
    		   System.out.println("Date: " + b.date);
    		   System.out.println("Frequency " + b.freq);
    		   System.out.println("Source: " + b.type);
    		   System.out.println("Term: " + b.term);
    		   System.out.println("Coupon: " + b.couponVal);
    		   System.out.println("Investment: " + b.investmentVal);
    		   System.out.println("Price: " + b.price);
    		   System.out.println("Payments sent: " + b.payments.size());
    	}
    }
    
    public static void sendPayment(String bondID, String investorID) {
    	if (!bondDatabase.containsKey(bondID)) {
    		System.out.println("This bond does not exist");
    	}
    	else if (!portfolioDatabase.containsKey(investorID)) {
    		System.out.println("This investor does not exist");
    	}
    	else {
    		Investor p = portfolioDatabase.get(investorID);
    		Bond b = bondDatabase.get(bondID);
    		if (!p.owned.contains(b)) {
    			System.out.println("This investor does not own this bond");
    		}
    		else {
    			if (b.payments.size() == b.term) {
    				System.out.println("The investor has recieved all the payments for this bond. This bond will be removed.");
    				p.owned.remove(b);
    			}
    			else {
    				b.computePayment();
    				System.out.println("Payment sent");
    			}
    		}
    	}
    	
    }
}
