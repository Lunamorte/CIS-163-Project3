package project3;

import java.util.GregorianCalendar;

public class CheckingAccount extends Account{
	private static final long serialVersionUID = 1L;
	private double monthlyFee;
	
	/************************************************
	 *Account constructor; sets up the Account.
	 *@param Account Type, account number, account owner, 
	 *the date opened, the account balance, and monthly fee
	 *@return none
	 ***********************************************/
	public CheckingAccount(AccountType type, int num, String own, 
			GregorianCalendar date, double bal, double fee){
		super(type, num, own, date, bal);
		monthlyFee=fee;
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
		CheckingAccount other = (CheckingAccount) obj;
		if (Double.doubleToLongBits(monthlyFee) != 
				Double.doubleToLongBits(other.monthlyFee))
			return false;
		return true;
	}

	/************************************************
	 *Get monthly fee method, gets the monthly fee for the account
	 *@param none
	 *@return the monthly fee as a double value
	 ***********************************************/
	public double getMonthlyFee() {
		return monthlyFee;
	}

	/************************************************
	 *Set monthly fee method, sets the monthly fee for the account
	 *@param the monthly fee as a double value
	 *@return none
	 ***********************************************/
	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	/************************************************
	 *To String method
	 *@param none
	 *@return A string containing all of the account info
	 ***********************************************/
	public String toString(){

		return super.toString()+";"+monthlyFee;
	}
}
