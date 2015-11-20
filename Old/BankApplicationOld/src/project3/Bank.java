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
import javax.swing.JFrame;

public class Bank {
	static JFrame frame;
	public static void main(String[] args) {
		frame = new JFrame ("Bank");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		BankGUI panel = new BankGUI();
		frame.getContentPane().add(panel);
		frame.setJMenuBar(panel.menuBar);
		frame.setSize(1000, 315);
		frame.setVisible(true);
	}

}
