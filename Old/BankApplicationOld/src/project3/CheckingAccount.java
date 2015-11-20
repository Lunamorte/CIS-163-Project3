package project3;

import java.util.GregorianCalendar;

public class CheckingAccount extends Account{
	private static final long serialVersionUID = 1L;
	private double monthlyFee;

	public CheckingAccount(AccountType type, int num, String own, GregorianCalendar date, double bal, double fee){
		super(type, num, own, date, bal);
		monthlyFee=fee;
	}

	
@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(monthlyFee);
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
		CheckingAccount other = (CheckingAccount) obj;
		if (Double.doubleToLongBits(monthlyFee) != Double.doubleToLongBits(other.monthlyFee))
			return false;
		return true;
	}


public double getMonthlyFee() {
		return monthlyFee;
	}


	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}


public String toString(){
		
		return super.toString()+" ; "+monthlyFee;
	}
}
