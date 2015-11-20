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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.net.URL;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BankModel extends AbstractTableModel{
	/**Array List containing the accounts.*/
	private ArrayList<Account> acts;

	/*****************************************************************************************************
	 * Default Constructor initializing the Arraylist.
	 * @param none
	 * @return none
	 * @return none
	 *****************************************************************************************************/
	public BankModel(){
		acts = new ArrayList();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Adds a Checking account with the inputed parameters.
	 * @param The type of account (checking or saving) as a String, an int for the account number,
	 * the name of the account owner as a String, the date as a String, a double for the current balance,
	 * and a double for the monthly fee.
	 * @return none
	 *****************************************************************************************************/
	public void addChecking(AccountType type, int num, String own, String dateString, double bal, double fee) throws ParseException{
		boolean duplicate = false;

		//Checks for duplicate account number
		for (int i = 0; i < acts.size(); i++){
			if (num == acts.get(i).getNumber()){
				JOptionPane.showMessageDialog(null, "Duplicate Account Number");
				duplicate = true;
			}
		}

		//Converts the date from a string into GregorianCalendar
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		Date d = format.parse(dateString);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);

		//Creates the checking account
		Account act = new CheckingAccount(type, num, own, c, bal, fee);

		//Adds the account to the account list
		if (duplicate == false){
			acts.add(act);
		}

		//fire
		//fireIntervalAdded(this, acts.size()-1, acts.size()-1);
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Adds a Saving account with the inputed parameters.
	 * @param The type of account (checking or saving) as a String, an int for the account number,
	 * the name of the account owner as a String, the date as a String, a double for the current balance,
	 * and a double the interest rate, and a double for the minimum balance.
	 * @return none
	 *****************************************************************************************************/
	public void addSaving(AccountType type, int num, String own, String dateString, double bal, double rate, double min) throws ParseException{
		boolean duplicate = false;
		//Checks for duplicate account number
		for (int i = 0; i < acts.size(); i++){
			if (num == acts.get(i).getNumber()){
				JOptionPane.showMessageDialog(null, "Duplicate Account Number");
				duplicate = true;
			}
		}

		boolean belowMin = false;
		//Checks to see if account balance meets minimal balance
		if (bal < min){
			JOptionPane.showMessageDialog(null, "Account Balance is below the minimal balance.");
			belowMin = true;
		}

		boolean negativeBal = false;
		//Checks to see if account balance is less than or equal to 0
		if (bal <= 0){
			JOptionPane.showMessageDialog(null, "Saving Account Balance is less than or equal to 0");
			negativeBal = true;
		}

		//Converts the date from a string into GregorianCalendar
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		Date d = format.parse(dateString);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);

		//Creates the saving account
		Account act = new SavingAccount(type, num, own, c, bal, rate, min);

		if (belowMin == false && negativeBal == false && duplicate == false){
			//Adds the account to the account list
			acts.add(act);
		}

		//fire
		//fireIntervalAdded(this, acts.size()-1, acts.size()-1);
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Updates a Checking account with the inputed parameters.
	 * @param The type of account (checking or saving) as a String, an int for the account number,
	 * the name of the account owner as a String, the date as a String, a double for the current balance,
	 * a double for the monthly fee, and an int i for the index of the selected account.
	 * @return none
	 *****************************************************************************************************/
	public void updateChecking(AccountType type, int num, String own, String dateString, double bal, double fee, int i) throws ParseException{
		boolean duplicate = false;
		//Checks for duplicate account number
		for (int j = 0; j < acts.size(); j++){
			if (num == acts.get(j).getNumber()){
				JOptionPane.showMessageDialog(null, "Duplicate Account Number");
				duplicate = true;
			}
		}

		//Converts the date from a string into GregorianCalendar
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		Date d = format.parse(dateString);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);

		//Creates the checking account
		Account act = new CheckingAccount(type, num, own, c, bal, fee);

		//Removes old Account Entry

		//Adds the updated account to the account list into the previous' location
		if (duplicate == false){
			acts.remove(i);
			acts.add(i, act);
		}

		//fire
		//fireContentsChanged(this, 0, acts.size());
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Updates a Saving account with the inputed parameters.
	 * @param The type of account (checking or saving) as a String, an int for the account number,
	 * the name of the account owner as a String, the date as a String, a double for the current balance,
	 * and a double the interest rate, a double for the minimum balance, 
	 * and an int i for the index value of the selected account.
	 * @return none
	 *****************************************************************************************************/
	public void updateSaving(AccountType type, int num, String own, String dateString, double bal, double rate, double min, int i) throws ParseException{
		boolean duplicate = false;
		//Checks for duplicate account number
		for (int j = 0; j < acts.size(); j++){
			if (num == acts.get(j).getNumber()){
				JOptionPane.showMessageDialog(null, "Duplicate Account Number");
				duplicate = true;
			}
		}

		boolean belowMin = false;
		//Checks to see if account balance meets minimal balance
		if (bal < min){
			JOptionPane.showMessageDialog(null, "Account Balance is below the minimal balance.");
			belowMin = true;
		}

		boolean negativeBal = false;
		//Checks to see if account balance is less than or equal to 0
		if (bal <= 0){
			JOptionPane.showMessageDialog(null, "Saving Account Balance is less than or equal to 0");
			negativeBal = true;
		}

		//Converts the date from a string into GregorianCalendar
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		Date d = format.parse(dateString);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);

		//Creates the saving account
		Account act = new SavingAccount(type, num, own, c, bal, rate, min);

		//Adds the updated account to the account list into the previous' location
		if (duplicate == false){
			acts.remove(i);
			acts.add(i, act);
		}

		//fire
		//fireContentsChanged(this, 0, acts.size());
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Deletes the account at the selected index.
	 * @param An int for the index value of the selected account.
	 * @return none
	 *****************************************************************************************************/
	public void delete(int i){
		acts.remove(i);

		//fire
		//fireIntervalRemoved(this, 0, acts.size()-1);
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Gets the account type (Checking or Saving) of the selected account.
	 * @param An int i for the index value of the selected account.
	 * @return The account number as an int.
	 *****************************************************************************************************/
	public AccountType getType(int i){
		return acts.get(i).getAccountType();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Gets the account type (Checking or Saving) of the selected account.
	 * @param An int i for the index value of the selected account.
	 * @return The account type (Checking or Saving) as a String.
	 *****************************************************************************************************/
	public int getNumber(int i){
		return acts.get(i).getNumber();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Gets the owner of the selected account.
	 * @param An int i for the index of the selected account.
	 * @return The account owner as a String.
	 *****************************************************************************************************/
	public String getOwner(int i){
		return acts.get(i).getOwner();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Gets the date opened of the selected account.
	 * @param An int i for the index of the selected account.
	 * @return The account type (Checking or Saving) as a String.
	 *****************************************************************************************************/
	public Date getDate(int i){
		Date d = new Date();
		d = acts.get(i).getDateOpened().getTime();
		return d;
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Gets the current balance of the selected account.
	 * @param An int i for the index value of the selected account.
	 * @return The account's balance as double.
	 *****************************************************************************************************/
	public double getBalance(int i){
		return acts.get(i).getBalance();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Gets the monthly fee of the selected account.
	 * @param An int i for the index value of the selected account.
	 * @return The account's monthly fee as double.
	 *****************************************************************************************************/
	public double getMonthlyFee(int i){
		return ((CheckingAccount) acts.get(i)).getMonthlyFee();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Gets the minimum balance of the selected account.
	 * @param An int i for the index value of the selected account.
	 * @return The account's minimum balance as double.
	 *****************************************************************************************************/
	public double getMinBalance(int i){
		return ((SavingAccount) acts.get(i)).getMinBalance();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Gets the interest rate of the selected account.
	 * @param An int i for the index value of the selected account.
	 * @return The account's interest rate as double.
	 *****************************************************************************************************/
	public double getInterestRate(int i){
		return ((SavingAccount) acts.get(i)).getInterestRate();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Sorts the account array list by account number.
	 * @param none
	 * @return none
	 *****************************************************************************************************/
	public void sortAccount(){
		List<Account> accounts = acts;

		Collections.sort(accounts, new Comparator<Account>() {

			@Override
			public int compare(Account account1, Account account2) {
				// TODO Auto-generated method stub
				if (account1.getNumber() > account2.getNumber())
					return 1;
				if (account1.getNumber() < account2.getNumber())
					return -1;
				return 0;
			}
		});

		acts=(ArrayList<Account>) accounts;

		//fire
		//fireContentsChanged(this, 0, acts.size());
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Sorts the account array list by the Date Opened.
	 * @param none
	 * @return none
	 *****************************************************************************************************/
	public void sortDate(){
		List<Account> accounts = acts;

		Collections.sort(accounts, new Comparator<Account>() {
			@Override
			public int compare(Account account1, Account account2) {
				// TODO Auto-generated method stub
				return account1.getDateOpened().compareTo(account2.getDateOpened());
			}
		});

		acts=(ArrayList<Account>) accounts;

		//fire
		//fireContentsChanged(this, 0, acts.size());
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Sorts the account array list alphabetically by 
	 * the account owner's name.
	 * @param none
	 * @return none
	 *****************************************************************************************************/
	public void sortName(){
		List<Account> accounts = acts;

		Collections.sort(accounts, new Comparator<Account>() {
			@Override
			public int compare(Account account1, Account account2) {
				// TODO Auto-generated method stub
				return account1.getOwner().compareTo(account2.getOwner());
			}
		});

		acts=(ArrayList<Account>) accounts;

		//fire
		//fireContentsChanged(this, 0, acts.size());
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Saves the account array list as a text file.
	 * @param none
	 * @return none
	 *****************************************************************************************************/
	public void save() {
		String fileName = JOptionPane.showInputDialog (null, "Enter the file Name");
		PrintWriter pw;
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		String d;
		try {
			pw = new PrintWriter(new FileOutputStream(fileName+".txt"));
			for (int i=0; i < acts.size(); i++){
				d = format.format(acts.get(i).getDateOpened().getTime());
				pw.println(acts.get(i).toString());
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Loads the account array list from a text file.
	 * @param none
	 * @return none
	 *****************************************************************************************************/
	public void load() {
		deleteAll();
		String fileName = JOptionPane.showInputDialog (null, "Enter the file Name");
		try {
			Scanner fileReader = new Scanner(new File(fileName+".txt"));
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			int lines = 0;
			while (reader.readLine() != null) {
				lines++;
			}
			reader.close();
			for (int i=0; i < lines; i++){
				String s = fileReader.nextLine();
				String accountParts[] = s.split(";");
				int count = s.length() - s.replace(";", "").length();
				if (count==5){
					addChecking(AccountType.valueOf(accountParts[0]), Integer.parseInt(accountParts[1]), accountParts[2], accountParts[3],
							Double.parseDouble(accountParts[4]), Double.parseDouble(accountParts[5]));
				}
				if (count==6){
					addSaving(AccountType.valueOf(accountParts[0]), Integer.parseInt(accountParts[1]), accountParts[2], accountParts[3], 
							Double.parseDouble(accountParts[4]), Double.parseDouble(accountParts[5]),Double.parseDouble(accountParts[6]));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "File Not Found");
			e.printStackTrace();
		} 
		catch(IOException e){
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Saves the account array list as a serialized file.
	 * @param none
	 * @return none
	 *****************************************************************************************************/
	public void saveSerial(){
		String fileName = JOptionPane.showInputDialog (null, "Enter the file Name");
		try{
			FileOutputStream fileOutput= new FileOutputStream(fileName);
			ObjectOutputStream objectOutput= new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(acts);
			objectOutput.close();
			fileOutput.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Loads the account array list from a serialized file.
	 * @param none
	 * @return none
	 *****************************************************************************************************/
	public void loadSerial(){
		String fileName = JOptionPane.showInputDialog (null, "Enter the file Name");
		try{
			FileInputStream fileInput = new FileInputStream(fileName);
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			acts = (ArrayList) objectInput.readObject();
			objectInput.close();
			fileInput.close();
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null, "File Not Found");
			e.printStackTrace();
			return;
		}
		catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//fire
		//fireContentsChanged(this, 0, acts.size());
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Deletes all accounts.
	 * @param An int i for the index of the selected account.
	 * @return The Account at index i.
	 *****************************************************************************************************/
	public void deleteAll(){
		acts.clear();
		fireTableDataChanged();
	}

	/*****************************************************************************************************
	 * Public Method overrides getElementAt in AbstractListModel. Gets the selected account.
	 * @param An int i for the index of the selected account.
	 * @return The Account at index i.
	 *****************************************************************************************************/
	//@Override
	//public Object getElementAt(int i) {
	// TODO Auto-generated method stub
	//return acts.get(i).toString();
	//}

	/*****************************************************************************************************
	 * Public Method overrides getSize in AbstractListModel. Gets the number of accounts in the array list.
	 * @param none
	 * @return The number of accounts as an int value.
	 *****************************************************************************************************/
	//@Override
	//public int getSize() {
	// TODO Auto-generated method stub
	//return acts.size();
	//}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Saves the account array list as a XML file.
	 * @param none
	 * @return none
	 *****************************************************************************************************/
	public void saveXML() {
		String fileName = JOptionPane.showInputDialog (null, "Enter the file Name");
		try {

			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();

			Element root = doc.createElement("AccountInfo");
			doc.appendChild(root);

			Element Details = doc.createElement("Details");
			root.appendChild(Details);


			for (Account act : acts) {

				Element accountType = doc.createElement("AccountType");
				accountType.appendChild(doc.createTextNode(String.valueOf(act
						.getAccountType())));
				Details.appendChild(accountType);

				Element number = doc.createElement("Number");
				number.appendChild(doc.createTextNode(String.valueOf(act.getNumber())));
				Details.appendChild(number);

				Element owner = doc.createElement("Owner");
				owner.appendChild(doc.createTextNode(String.valueOf(act.getOwner())));
				Details.appendChild(owner);

				DateFormat d = new SimpleDateFormat("MM-dd-yyyy");
				String dateString = d.format(act.getDateOpened().getTime());

				Element date = doc.createElement("DateOpened");
				date.appendChild(doc.createTextNode(String.valueOf(dateString)));
				Details.appendChild(date);

				Element balance = doc.createElement("AccountBalance");
				balance.appendChild(doc.createTextNode(String.valueOf(act.getBalance())));
				Details.appendChild(balance);

				if (act.getAccountType() == AccountType.CHECKING){
					Element fee = doc.createElement("MonthlyFee");
					fee.appendChild(doc.createTextNode(String.valueOf(((CheckingAccount) act).getMonthlyFee())));
					Details.appendChild(fee);
				}

				if (act.getAccountType() == AccountType.SAVING){
					Element rate = doc.createElement("InterestRate");
					rate.appendChild(doc.createTextNode(String.valueOf(((SavingAccount) act).getInterestRate())));
					Details.appendChild(rate);

					Element min = doc.createElement("MinimumBalance");
					min.appendChild(doc.createTextNode(String.valueOf(((SavingAccount) act).getMinBalance())));
					Details.appendChild(min);
				}

			}

			// Save the document to the disk file
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();

			// format the XML nicely
			aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			aTransformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			try {
				// location and name of XML file you can change as per need
				FileWriter fos = new FileWriter(fileName+".xml");
				StreamResult result = new StreamResult(fos);
				aTransformer.transform(source, result);

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (TransformerException e) {
			e.printStackTrace();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/*****************************************************************************************************
	 * Public Method called from BankGUI. Loads accounts from a XML file.
	 * @param none
	 * @return none
	 *****************************************************************************************************/
	public void loadXML() {
		String fileName = JOptionPane.showInputDialog (null, "Enter the file Name");
		try {

			File file = new File(fileName+".xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);

			for (int i = 0; i < document.getElementsByTagName("AccountType").getLength(); i++){
				AccountType type = AccountType.valueOf(document.getElementsByTagName("AccountType").item(i).getTextContent());

				int num = Integer.parseInt(document.getElementsByTagName("Number").item(i).getTextContent());

				String own = document.getElementsByTagName("Owner").item(0).getTextContent();

				String dateString = document.getElementsByTagName("DateOpened").item(i).getTextContent();

				double bal = Double.parseDouble(document.getElementsByTagName("AccountBalance").item(i).getTextContent());

				if (type == AccountType.CHECKING){
					double fee = Double.parseDouble(document.getElementsByTagName("MonthlyFee").item(i).getTextContent());

					addChecking(type, num, own, dateString, bal, fee);
				}

				if (type == AccountType.SAVING){
					double rate = Double.parseDouble(document.getElementsByTagName("InterestRate").item(i).getTextContent());
					double min = Double.parseDouble(document.getElementsByTagName("MinimumBalance").item(i).getTextContent());

					addSaving(type, num, own, dateString, bal, rate, min);
				}	
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*****************************************************************************************************
	 * Public Method overrides getColumnCount in AbstractTableModel. Gets the number of columns for the JTable.
	 * @param none
	 * @return The number of columns in the table; fixed to 4.
	 *****************************************************************************************************/
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	/*****************************************************************************************************
	 * Public Method overrides getRowCount in AbstractTableModel. Gets the number of accounts in the array list.
	 * @param none
	 * @return The number of rows for the JTable which is equal to the number of accounts in arrayList acts.
	 *****************************************************************************************************/
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return acts.size();
	}

	/*****************************************************************************************************
	 * Public Method overrides getValueAt in AbstractTableModel. Gets the value of each cell in the JTable.
	 * @param Two integers i and j, referring to the row and coulmn of the cell.
	 * @return The value of the cell in row i and column j.
	 *****************************************************************************************************/
	@Override
	public Object getValueAt(int i, int j) {
		// TODO Auto-generated method stub
		String[][] actList = new String[acts.size()][4];

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

		NumberFormat numFormat = new DecimalFormat("#0.00");

		for (int y=0; y < acts.size(); y++){
			String d = dateFormat.format(getDate(y));
			actList[y][0]=Integer.toString(getNumber(y));
			actList[y][1]=getOwner(y);
			actList[y][2]="$"+numFormat.format(getBalance(y));
			actList[y][3]=d;
		}

		return actList[i][j];
	}

	/*****************************************************************************************************
	 * Public Method overrides getColumnName in AbstractTableModel. Gets the JTable column names.
	 * @param An int i referring to a specific column of the JTable
	 * @return The name of column i.
	 *****************************************************************************************************/
	@Override
	public String getColumnName(int i) {
		String[] COLUMN_NAMES = {"Account Number", "Account Owner", "Account Balance", "Date Opened"};
		return COLUMN_NAMES[i];
	}

}
