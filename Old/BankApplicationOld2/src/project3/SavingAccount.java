package project3;
/*************************************************
 * For Project 3, you will develop a program that 
 * maintains accounts for a bank. The application 
 * allows you to add, delete, update, and sort accounts. 
 * It also provides functionality to save and load/restore 
 * accounts to and from a file using three different formats 
 * � binary (serialized), text, and XML
 * 
 *@author Anthony Nguyen
 *@date November 2015
 *************************************************/
import java.util.GregorianCalendar;

public class SavingAccount extends Account{
	private static final long serialVersionUID = 1L;

	private double minBalance;
	private double interestRate;
	
	/************************************************
	 *Account constructor; sets up the Account.
	 *@param Account Type, account number, account owner, 
	 *the date opened, the account balance, interest rate, and
	 *minimum balance
	 *@return none
	 ***********************************************/
	public SavingAccount(AccountType type, int num, String own, 
			GregorianCalendar date, double bal, double rate, 
			double min){
		super(type, num, own, date, bal);
		interestRate=rate;
		minBalance=min;
	}
	
	/************************************************
	 *Equals method, checks if this account is equal
	 *to another account.
	 *@param Another Account
	 *@return True if this account is equal to other
	 *account
	 ***********************************************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SavingAccount other = (SavingAccount) obj;
		if (Double.doubleToLongBits(interestRate) != 
				Double.doubleToLongBits(other.interestRate))
			return false;
		if (Double.doubleToLongBits(minBalance) != 
				Double.doubleToLongBits(other.minBalance))
			return false;
		return true;
	}

	/************************************************
	 *Get minimum balance method, gets the minimum balance
	 *for the account
	 *@param none
	 *@return the minimum balace fee as a double value
	 ***********************************************/
	public double getMinBalance() {
		return minBalance;
	}

	/************************************************
	 *Set minimum balance method, sets the minimum balance
	 *for the account
	 *@param the minimum balance as a double value
	 *@return none
	 ***********************************************/
	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}

	/************************************************
	 *Get interest rate method, gets the interest rate for the account
	 *@param none
	 *@return the interest rate as a double value
	 ***********************************************/
	public double getInterestRate() {
		return interestRate;
	}

	/************************************************
	 *Set interest rate method, sets the interest rate for the account
	 *@param the interest rate as a double value
	 *@return none
	 ***********************************************/
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/************************************************
	 *To String method
	 *@param none
	 *@return A string containing all of the account info
	 ***********************************************/
	public String toString(){
		
		return super.toString()+";"+interestRate+";"+minBalance;
	}
}
