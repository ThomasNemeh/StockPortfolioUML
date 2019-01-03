import java.util.ArrayList;
public class Investor {
	private String investorID;
	public ArrayList<Bond> owned = new ArrayList<Bond>();
	
	public Investor(String ID) {
		investorID = ID;
	}
	
}
