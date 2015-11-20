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
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public abstract class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	/***/
	private int number;
	private String owner;
	private GregorianCalendar dateOpened;
	private double balance;
	private AccountType accountType;
	
	/************************************************
	 *Account constructor; sets up the Account.
	 *@param Account Type, account number, account owner, 
	 *the date opened, the account balance
	 *@return none
	 ***********************************************/
	public Account(AccountType type, int num, String own, 
			GregorianCalendar date, double bal){
		accountType = type;
		number = num;
		owner = own;
		dateOpened = date;
		balance = bal;
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountType != other.accountType)
			return false;
		if (Double.doubleToLongBits(balance) != 
				Double.doubleToLongBits(other.balance))
			return false;
		if (dateOpened == null) {
			if (other.dateOpened != null)
				return false;
		} else if (!dateOpened.equals(other.dateOpened))
			return false;
		if (number != other.number)
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	/************************************************
	 *Get account number method.
	 *@param none
	 *@return The Account number of this account
	 ***********************************************/
	public int getNumber() {
		return number;
	}

	/************************************************
	 *Set account number method
	 *@param Int value for Account Number
	 *@return none
	 ***********************************************/
	public void setNumber(int number) {
		this.number = number;
	}

	/************************************************
	 *Get account owner method.
	 *@param none
	 *@return The Account owner of this account
	 ***********************************************/
	public String getOwner() {
		return owner;
	}

	/************************************************
	 *Set account owner method
	 *@param String value for the account owner name
	 *@return none
	 ***********************************************/
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/************************************************
	 *Get account date opened method.
	 *@param none
	 *@return The date opened of this account
	 ***********************************************/
	public GregorianCalendar getDateOpened() {
		return dateOpened;
	}

	/************************************************
	 *Set date opened method
	 *@param Gregorian Calendar value for the date opened
	 *@return none
	 ***********************************************/
	public void setDateOpened(GregorianCalendar dateOpened) {
		this.dateOpened = dateOpened;
	}

	/************************************************
	 *Get account balance method.
	 *@param none
	 *@return The balance of this account
	 ***********************************************/
	public double getBalance() {
		return balance;
	}

	/************************************************
	 *Set account balance method
	 *@param double value for the account balance
	 *@return none
	 ***********************************************/
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/************************************************
	 *Get account type method.
	 *@param none
	 *@return The Account type of this account
	 ***********************************************/
	public AccountType getAccountType() {
		return accountType;
	}

	/************************************************
	 *Set account type method
	 *@param enum value for the account type
	 *@return none
	 ***********************************************/
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	/************************************************
	 *To String method
	 *@param none
	 *@return A string containing all of the account info
	 ***********************************************/
	public String toString(){
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		String d = format.format(dateOpened.getTime());
		return accountType+";"+number+";"+owner+";"+d+";"+balance;
	}
}
