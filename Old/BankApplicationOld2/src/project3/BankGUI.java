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
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class BankGUI extends JPanel {
	/**JList used to display a list of bank accounts.*/
	private JList<Account> list;
	/**JTable used to display a list of bank accounts.*/
	private JTable table;
	/**Radio Button, select to set the account type to checking.*/
	private JRadioButton checkingAccountBtn;
	/**Radio Button, select to set the account type to saving.*/
	private JRadioButton savingsAccountBtn;
	/**Text Field, enter the account number in this.*/
	private JTextField accountNumText;
	/**Text Field, enter the account owner's name in this.*/
	private JTextField accountOwnerText;
	/**Text Field, enter the account's current balance in this.*/
	private JTextField accountBalanceText;
	/**Text Field, enter the account's monthly fee in this.*/
	private JTextField monthlyFeeText;
	/**Text Field, enter the account's interest rate in this.*/
	private JTextField interestRateText;
	/**Text Field, enter the account's minimum balance in this.*/
	private JTextField minimumBalanceText;
	/**Button that lets you add accounts.*/
	private JButton addBtn;
	/**Button that lets you update accounts.*/
	private JButton updateBtn;
	/**Button that lets you clear text fields.*/
	private JButton clearBtn;
	/**Button that lets you delete accounts (handle with care).*/
	private JButton deleteBtn;
	/**Enum to tell the Add button what type of account to add.*/
	private AccountType type=AccountType.CHECKING;
	/**Date Picker to pick the date that the account was opened.*/
	private JDatePickerImpl datePicker;
	/**Date Model use to set initial selected date.*/
	private UtilDateModel model;
	/**Bank Model, that holds all of the accounts.*/
	private BankModel bankModel;
	/**Menu Bar, just a menu bar.*/
	protected JMenuBar menuBar;
	/**Menu Item, save to text.*/
	private JMenuItem saveText;
	/***Menu Item, load from text.*/
	private JMenuItem loadText;
	/***Menu Item, save to serialized.*/
	private JMenuItem saveSerial;
	/***Menu Item, load from serialized.*/
	private JMenuItem loadSerial;
	/***Menu Item, save to XML.*/
	private JMenuItem saveXML;
	/***Menu Item, load from XML.*/
	private JMenuItem loadXML;
	/***Menu Item, sorts account by account number.*/
	private JMenuItem sortAccount;
	/***Menu Item, sorts account by account owner name.*/
	private JMenuItem sortOwner;
	/***Menu Item, sorts account by date opened.*/
	private JMenuItem sortDate;
	/***Menu Item, deletes all accounts.*/
	private JMenuItem deleteAll;

	/************************************************
	 *Bank GUI constructor; sets up the bank GUI.
	 *@param none
	 *@return none
	 ***********************************************/
	public BankGUI() {
		//Initializes bank model
		bankModel = new BankModel();

		setLayout(new GridLayout(1, 2, 5, 0));
		JPanel right = new JPanel();
		right.setBorder(null);

		right.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		//Button listener for buttons
		ButtonListener listener = new ButtonListener();

		//Mouse listener for the JList
		MouseListener mouseListener = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (list.getSelectedIndex()!=-1){
					accountNumText.setText(Integer.toString(
							bankModel.getNumber(
									list.getSelectedIndex())));
					accountOwnerText.setText(bankModel.getOwner(
							list.getSelectedIndex()));
					accountBalanceText.setText(Double.toString(
							bankModel.getBalance(
									list.getSelectedIndex())));

					Date date = new Date();
					date = bankModel.getDate(list.getSelectedIndex());
					DateFormat year = new SimpleDateFormat("yyyy");
					DateFormat day = new SimpleDateFormat("dd");
					DateFormat month = new SimpleDateFormat("MM");
					String y = year.format(date);
					String d = day.format(date);
					String m = month.format(date);

					model.setDate(Integer.parseInt(y), 
							Integer.parseInt(m)-1, Integer.parseInt(d));
					if (bankModel.getType(list.getSelectedIndex())==
							AccountType.CHECKING){
						monthlyFeeText.setText(Double.toString(
								bankModel.getMonthlyFee(
										list.getSelectedIndex())));
						checkingAccountBtn.setSelected(true);
						savingsAccountBtn.setSelected(false);
						accountTypeChange();
					}
					if (bankModel.getType(list.getSelectedIndex())==
							AccountType.SAVING){
						minimumBalanceText.setText(Double.toString(
								bankModel.getMinBalance(
										list.getSelectedIndex())));
						interestRateText.setText(Double.toString(
								bankModel.getInterestRate(
										list.getSelectedIndex())));
						checkingAccountBtn.setSelected(false);
						savingsAccountBtn.setSelected(true);
						accountTypeChange();
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (list.getSelectedIndex()!=-1){
					//Loads Account Number into text field
					accountNumText.setText(Integer.toString(
							bankModel.getNumber(
									list.getSelectedIndex())));

					//Loads Account Owner into Text Field
					accountOwnerText.setText(bankModel.getOwner(
							list.getSelectedIndex()));

					//Loads Account Balance into Text Field
					accountBalanceText.setText(Double.toString(
							bankModel.getBalance(
									list.getSelectedIndex())));

					//Loads the date opened into the date picker
					Date date = new Date();
					date = bankModel.getDate(list.getSelectedIndex());
					DateFormat year = new SimpleDateFormat("yyyy");
					DateFormat day = new SimpleDateFormat("dd");
					DateFormat month = new SimpleDateFormat("MM");
					String y = year.format(date);
					String d = day.format(date);
					String m = month.format(date);
					model.setDate(Integer.parseInt(y), 
							Integer.parseInt(m)-1, Integer.parseInt(d));

					//Loads monthly fee if account is checking
					if (bankModel.getType(list.getSelectedIndex())==
							AccountType.CHECKING){
						monthlyFeeText.setText(Double.toString(
								bankModel.getMonthlyFee(
										list.getSelectedIndex())));
						checkingAccountBtn.setSelected(true);
						savingsAccountBtn.setSelected(false);
						accountTypeChange();
					}

					//loads interest rate and min balance if account is
					//savings
					if (bankModel.getType(list.getSelectedIndex())==
							AccountType.SAVING){
						minimumBalanceText.setText(Double.toString(
								bankModel.getMinBalance(
										list.getSelectedIndex())));
						interestRateText.setText(Double.toString(
								bankModel.getInterestRate(
										list.getSelectedIndex())));
						checkingAccountBtn.setSelected(false);
						savingsAccountBtn.setSelected(true);
						accountTypeChange();
					}
				}
			}

		};

		//Mouse listener for the JTable
		MouseListener mouseListenerTable = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (table.getSelectedRow() != -1){
					NumberFormat numFormat = new DecimalFormat("#0.00");

					accountNumText.setText(Integer.toString(
							bankModel.getNumber(
									table.getSelectedRow())));
					accountOwnerText.setText(bankModel.getOwner(
							table.getSelectedRow()));
					accountBalanceText.setText(numFormat.format(
							bankModel.getBalance(
									table.getSelectedRow())));
					Date date = new Date();
					date = bankModel.getDate(table.getSelectedRow());
					DateFormat year = new SimpleDateFormat("yyyy");
					DateFormat day = new SimpleDateFormat("dd");
					DateFormat month = new SimpleDateFormat("MM");
					String y = year.format(date);
					String d = day.format(date);
					String m = month.format(date);
					model.setDate(Integer.parseInt(y), 
							Integer.parseInt(m)-1, Integer.parseInt(d));
					if (bankModel.getType(table.getSelectedRow())==
							AccountType.CHECKING){
						monthlyFeeText.setText(numFormat.format(
								bankModel.getMonthlyFee(
										table.getSelectedRow())));
						checkingAccountBtn.setSelected(true);
						savingsAccountBtn.setSelected(false);
						accountTypeChange();
					}
					if (bankModel.getType(table.getSelectedRow())==
							AccountType.SAVING){
						minimumBalanceText.setText(numFormat.format(
								bankModel.getMinBalance(
										table.getSelectedRow())));
						interestRateText.setText(numFormat.format(
								bankModel.getInterestRate(
										table.getSelectedRow())));
						checkingAccountBtn.setSelected(false);
						savingsAccountBtn.setSelected(true);
						accountTypeChange();
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (table.getSelectedRow() != -1){
					NumberFormat numFormat = new DecimalFormat("#0.00");

					accountNumText.setText(Integer.toString(
							bankModel.getNumber(
									table.getSelectedRow())));
					accountOwnerText.setText(bankModel.getOwner(
							table.getSelectedRow()));
					accountBalanceText.setText(numFormat.format(
							bankModel.getBalance(
									table.getSelectedRow())));
					Date date = new Date();
					date = bankModel.getDate(table.getSelectedRow());
					DateFormat year = new SimpleDateFormat("yyyy");
					DateFormat day = new SimpleDateFormat("dd");
					DateFormat month = new SimpleDateFormat("MM");
					String y = year.format(date);
					String d = day.format(date);
					String m = month.format(date);
					model.setDate(Integer.parseInt(y), 
							Integer.parseInt(m)-1, Integer.parseInt(d));
					if (bankModel.getType(table.getSelectedRow())==
							AccountType.CHECKING){
						monthlyFeeText.setText(numFormat.format(
								bankModel.getMonthlyFee(
										table.getSelectedRow())));
						checkingAccountBtn.setSelected(true);
						savingsAccountBtn.setSelected(false);
						accountTypeChange();
					}
					if (bankModel.getType(table.getSelectedRow())==
							AccountType.SAVING){
						minimumBalanceText.setText(numFormat.format(
								bankModel.getMinBalance(
										table.getSelectedRow())));
						interestRateText.setText(numFormat.format(
								bankModel.getInterestRate(
										table.getSelectedRow())));
						checkingAccountBtn.setSelected(false);
						savingsAccountBtn.setSelected(true);
						accountTypeChange();
					}
				}
			}
		};

		//JMenus for JMenu Bar
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu sortMenu = new JMenu("Sort");
		menuBar.add(sortMenu);

		//JMenu Items for file menu
		saveText = new JMenuItem("Save");
		saveText.addActionListener(listener);
		loadText = new JMenuItem("Load");
		loadText.addActionListener(listener);
		saveSerial = new JMenuItem("Save (Serial)");
		saveSerial.addActionListener(listener);
		loadSerial = new JMenuItem("Load (Serial)");
		loadSerial.addActionListener(listener);
		saveXML = new JMenuItem("Save (XML)");
		saveXML.addActionListener(listener);
		loadXML = new JMenuItem("Load (XML)");
		loadXML.addActionListener(listener);
		deleteAll = new JMenuItem("Delete All");
		deleteAll.addActionListener(listener);
		fileMenu.add(saveText);
		fileMenu.add(loadText);
		fileMenu.add(saveSerial);
		fileMenu.add(loadSerial);
		fileMenu.add(saveXML);
		fileMenu.add(loadXML);
		fileMenu.add(deleteAll);

		//JMenu Items for sort menu
		sortAccount = new JMenuItem("Sort by #");
		sortAccount.addActionListener(listener);
		sortOwner = new JMenuItem("Sort by name");
		sortOwner.addActionListener(listener);
		sortDate = new JMenuItem("Sort by date");
		sortDate.addActionListener(listener);
		sortMenu.add(sortAccount);
		sortMenu.add(sortOwner);
		sortMenu.add(sortDate);

		//list = new JList(bankModel);
		//scrollPane.setViewportView(list);
		//list.addMouseListener(mouseListener);

		table = new JTable(bankModel);
		scrollPane.setViewportView(table);
		table.addMouseListener(mouseListenerTable);
		table.setShowGrid(false);
		table.setFillsViewportHeight(true);
		//updateTable();

		add(right);

		JPanel accountTypePanel = new JPanel();
		accountTypePanel.setBorder(new TitledBorder(UIManager.getBorder(
				"TitledBorder.border"), 
				"Account Type", TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		accountTypePanel.setBounds(10, 11, 325, 40);
		right.add(accountTypePanel);
		accountTypePanel.setLayout(new GridLayout(1, 2, 5, 0));

		checkingAccountBtn = new JRadioButton("Checking Account");
		accountTypePanel.add(checkingAccountBtn);
		checkingAccountBtn.addActionListener(listener);
		checkingAccountBtn.setSelected(true);

		savingsAccountBtn = new JRadioButton("Savings Account");
		accountTypePanel.add(savingsAccountBtn);
		savingsAccountBtn.addActionListener(listener);

		JPanel accountInfoPanel = new JPanel();
		accountInfoPanel.setBounds(10, 57, 325, 182);
		right.add(accountInfoPanel);
		accountInfoPanel.setLayout(null);

		JLabel accountNumLabel = new JLabel("Account Number:");
		accountNumLabel.setBounds(6, 9, 124, 14);
		accountInfoPanel.add(accountNumLabel);

		accountNumText = new JTextField();
		accountNumText.setBounds(140, 6, 175, 20);
		accountInfoPanel.add(accountNumText);
		accountNumText.setColumns(10);

		JLabel accountOwnerLabel = new JLabel("Account Owner:");
		accountOwnerLabel.setBounds(6, 35, 124, 14);
		accountInfoPanel.add(accountOwnerLabel);

		accountOwnerText = new JTextField();
		accountOwnerText.setBounds(140, 32, 175, 20);
		accountInfoPanel.add(accountOwnerText);
		accountOwnerText.setColumns(10);

		JLabel dateOpenedLabel = new JLabel("Date Opened:");
		dateOpenedLabel.setBounds(6, 61, 124, 14);
		accountInfoPanel.add(dateOpenedLabel);

		model = new UtilDateModel();
		Date date = new Date();
		DateFormat year = new SimpleDateFormat("yyyy");
		DateFormat day = new SimpleDateFormat("dd");
		DateFormat month = new SimpleDateFormat("MM");
		String y = year.format(date);
		String d = day.format(date);
		String m = month.format(date);
		model.setDate(Integer.parseInt(y), Integer.parseInt(m)-1, 
				Integer.parseInt(d));
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, 
				new DateLabelFormatter());
		SpringLayout springLayout = 
				(SpringLayout) datePicker.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, 
				datePicker.getJFormattedTextField(), 0, 
				SpringLayout.SOUTH, datePicker);
		datePicker.setBounds(140, 58, 175, 20);
		accountInfoPanel.add(datePicker);

		JLabel lblNewLabel = new JLabel("Account Balance:");
		lblNewLabel.setBounds(6, 87, 124, 14);
		accountInfoPanel.add(lblNewLabel);

		accountBalanceText = new JTextField();
		accountBalanceText.setBounds(140, 84, 175, 20);
		accountInfoPanel.add(accountBalanceText);
		accountBalanceText.setColumns(10);

		JLabel monthlyFeeLabel = new JLabel("Monthly Fee:");
		monthlyFeeLabel.setBounds(6, 113, 124, 14);
		accountInfoPanel.add(monthlyFeeLabel);

		monthlyFeeText = new JTextField();
		monthlyFeeText.setBounds(140, 110, 175, 20);
		accountInfoPanel.add(monthlyFeeText);
		monthlyFeeText.setColumns(10);

		JLabel interestRateLabel = new JLabel("Interest Rate:");
		interestRateLabel.setBounds(6, 139, 124, 14);
		accountInfoPanel.add(interestRateLabel);

		interestRateText = new JTextField();
		interestRateText.setBounds(140, 136, 175, 20);
		accountInfoPanel.add(interestRateText);
		interestRateText.setColumns(10);

		JLabel minimumBalanceLabel = new JLabel("Minimum Balance:");
		minimumBalanceLabel.setBounds(6, 165, 124, 14);
		accountInfoPanel.add(minimumBalanceLabel);

		minimumBalanceText = new JTextField();
		minimumBalanceText.setBounds(140, 162, 175, 20);
		accountInfoPanel.add(minimumBalanceText);
		minimumBalanceText.setColumns(10);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(345, 29, 130, 200);
		right.add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(4, 1, 10, 20));

		addBtn = new JButton("Add");
		buttonPanel.add(addBtn);
		addBtn.addActionListener(listener);

		updateBtn = new JButton("Update");
		buttonPanel.add(updateBtn);
		updateBtn.addActionListener(listener);

		deleteBtn = new JButton("Delete");
		buttonPanel.add(deleteBtn);
		deleteBtn.addActionListener(listener);

		clearBtn = new JButton("Clear");
		buttonPanel.add(clearBtn);
		clearBtn.addActionListener(listener);

		accountTypeChange();

	}

	/************************************************
	 *Private Method that disables the unused text fields
	 *depending on the type of bank account selected.
	 *@param none
	 *@return none
	 ***********************************************/
	private void accountTypeChange(){
		if (checkingAccountBtn.isSelected()==true){
			type = AccountType.CHECKING;
			minimumBalanceText.setText("");
			interestRateText.setText("");
			minimumBalanceText.setEnabled(false);
			interestRateText.setEnabled(false);
			monthlyFeeText.setEnabled(true);
		}

		if (savingsAccountBtn.isSelected()==true){
			type=AccountType.SAVING;
			monthlyFeeText.setText("");
			minimumBalanceText.setEnabled(true);
			interestRateText.setEnabled(true);
			monthlyFeeText.setEnabled(false);
		}
	}

	/************************************************
	 *Listener class, makes buttons do stuff!
	 *@param none
	 *@return none
	 ***********************************************/
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//Unchecks Saving Account if Checking is selected.
			if (checkingAccountBtn==e.getSource()){
				savingsAccountBtn.setSelected(false);
				if (checkingAccountBtn.isSelected()==false){
					checkingAccountBtn.setSelected(true);
				}
				accountTypeChange();
			}

			//Unchecks Checking Account if Saving is selected
			if (savingsAccountBtn==e.getSource()){
				checkingAccountBtn.setSelected(false);
				if (savingsAccountBtn.isSelected()==false){
					savingsAccountBtn.setSelected(true);
				}
				accountTypeChange();
			}

			//Adds an account with the given info
			if (addBtn==e.getSource()){
				//Adds a checking account
				if (checkingAccountBtn.isSelected()==true){
					try {
						bankModel.addChecking(type, Integer.parseInt(
								accountNumText.getText()),
								accountOwnerText.getText(),
								datePicker.getJFormattedTextField().
								getText(),
								Double.parseDouble(
										accountBalanceText.getText()), 
								Double.parseDouble(
										monthlyFeeText.getText()));
					}
					catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, 
								"Invalid Input");
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, 
								"ParseException");
					}
				}

				//Adds a savings account
				if (savingsAccountBtn.isSelected()==true){
					try {
						bankModel.addSaving(type, Integer.parseInt(
								accountNumText.getText()),
								accountOwnerText.getText(),
								datePicker.getJFormattedTextField().
								getText(),
								Double.parseDouble(
										accountBalanceText.getText()), 
								Double.parseDouble(
										interestRateText.getText()), 
								Double.parseDouble(
										minimumBalanceText.getText()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, 
								"Invalid Input");
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, 
								"ParseException");
					}
				}
			}

			//Updates the selected account with the given info
			if (updateBtn==e.getSource()){
				//Updates selected checking account
				if (checkingAccountBtn.isSelected()==true){
					if (table.getSelectedColumn() != -1){
						try {
							//bankModel.updateChecking(type, 
							//Integer.parseInt(accountNumText.getText()),
							//accountOwnerText.getText(),
							//datePicker.getJFormattedTextField().getText(),
							//Double.parseDouble(
							//accountBalanceText.getText()), 
							//Double.parseDouble(monthlyFeeText.getText()),
							//list.getSelectedIndex());

							bankModel.updateChecking(type, Integer.parseInt(
									accountNumText.getText()),
									accountOwnerText.getText(),
									datePicker.getJFormattedTextField().
									getText(),
									Double.parseDouble(
											accountBalanceText.getText()), 
									Double.parseDouble(
											monthlyFeeText.getText()), 
									table.getSelectedRow());
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, 
									"Invalid Input");
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, 
									"ParseException");
						}
					}
				}

				//Updates selected savings account
				if (savingsAccountBtn.isSelected()==true){
					if (table.getSelectedColumn() !=-1){
						try {
							//bankModel.updateSaving(type, Integer.parseInt(
							//accountNumText.getText()),
							//accountOwnerText.getText(),
							//datePicker.getJFormattedTextField().getText(),
							//Double.parseDouble(
							//accountBalanceText.getText()), 
							//Double.parseDouble(interestRateText.getText())
							//, Double.parseDouble(
							//minimumBalanceText.getText()), 
							//list.getSelectedIndex());

							bankModel.updateSaving(type, Integer.parseInt(
									accountNumText.getText()),
									accountOwnerText.getText(),
									datePicker.getJFormattedTextField().
									getText(),
									Double.parseDouble(
											accountBalanceText.getText()), 
									Double.parseDouble(
											interestRateText.getText()), 
									Double.parseDouble(
											minimumBalanceText.getText()), 
									table.getSelectedRow());
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, 
									"Invalid Input");
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, 
									"ParseException");
						}
					}
				}
			}

			//Deletes the selected account
			if (deleteBtn==e.getSource()){
				//bankModel.delete(list.getSelectedIndex());
				bankModel.delete(table.getSelectedRow());
			}

			//Clears the text fields, resets the date, and sets the 
			//selected account type to checking
			//(does not update the account)
			if (clearBtn==e.getSource()){
				checkingAccountBtn.setSelected(true);
				savingsAccountBtn.setSelected(false);
				accountTypeChange();
				accountNumText.setText("");
				accountOwnerText.setText("");
				accountBalanceText.setText("");
				monthlyFeeText.setText("");
				interestRateText.setText("");
				minimumBalanceText.setText("");
				Date date = new Date();
				DateFormat year = new SimpleDateFormat("yyyy");
				DateFormat day = new SimpleDateFormat("dd");
				DateFormat month = new SimpleDateFormat("MM");
				String y = year.format(date);
				String d = day.format(date);
				String m = month.format(date);
				model.setDate(Integer.parseInt(y), Integer.parseInt(m)-1
						, Integer.parseInt(d));
			}

			//Deletes all accounts
			if (deleteAll==e.getSource()){
				bankModel.deleteAll();
			}

			//Sorts by account number
			if (sortAccount==e.getSource()){
				bankModel.sortAccount();
			}

			//Sorts by account owner
			if (sortOwner==e.getSource()){
				bankModel.sortName();
			}

			//Sorts by the date opened
			if (sortDate==e.getSource()){
				bankModel.sortDate();
			}

			//Saves to text file
			if (saveText==e.getSource()){
				bankModel.save();
			}

			//Loads from text file
			if (loadText==e.getSource()){
				bankModel.load();
			}

			//Saves to serialized file
			if (saveSerial==e.getSource()){
				bankModel.saveSerial();
			}

			//Loads from serialized file
			if (loadSerial==e.getSource()){
				bankModel.loadSerial();
			}

			//Saves to XMLfile
			if (saveXML==e.getSource()){
				bankModel.saveXML();
			}

			//Loads from XML file
			if (loadXML==e.getSource()){
				bankModel.loadXML();
			}
		}
	}
}

