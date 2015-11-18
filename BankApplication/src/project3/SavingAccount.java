package project3;
/*************************************************
 * For Project 3, you will develop a program that 
 * maintains accounts for a bank. The application 
 * allows you to add, delete, update, and sort accounts. 
 * It also provides functionality to save and load/restore 
 * accounts to and from a file using three different formats 
 * – binary (serialized), text, and XML
 * 
 *@author Anthony Nguyen
 *@date November 2015
 *************************************************/
import java.util.GregorianCalendar;

public class SavingAccount extends Account{
	private static final long serialVersionUID = 1L;

	private double minBalance;
	private double interestRate;
	
	public SavingAccount(AccountType type, int num, String own, GregorianCalendar date, double bal, double rate, double min){
		super(type, num, own, date, bal);
		interestRate=rate;
		minBalance=min;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(interestRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SavingAccount other = (SavingAccount) obj;
		if (Double.doubleToLongBits(interestRate) != Double.doubleToLongBits(other.interestRate))
			return false;
		if (Double.doubleToLongBits(minBalance) != Double.doubleToLongBits(other.minBalance))
			return false;
		return true;
	}


	public double getMinBalance() {
		return minBalance;
	}


	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}


	public double getInterestRate() {
		return interestRate;
	}


	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}


	public String toString(){
		
		return super.toString()+" ; "+minBalance+" ; "+interestRate;
	}
}
