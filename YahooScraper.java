// testing the editing functionality

import javax.swing.*;

	import java.net.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
	import java.io.*;
import java.util.regex.*;

import javax.swing.JFrame;

public class YahooScraper 
{
	public static void main(String[] args) {
    	stockgui frame = new stockgui();
		frame.setTitle("Jason's Application");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,250);
		frame.setVisible(true);
		
	    // call the compile method on the PATTERN object to define the regex expression
	    // call the matcher method on the PATTERN object to perform the expression and ASSIGN result to a MATCHER object
	    // need to call MATCHER.find multiple times to find all matches
	    // need to do a double slash to do an escape character, unlike php, so \r in php is \\r in java
	    // need to escape a double quote in java, so to find a literal ", you do to \"
	    // group(0) always returns the full string
	    // group(1) returns the actual match
	    // groupCount doesn't actually return the number of matches in the string
	}
}
	    


class price_getter
{
	String price_block[][] = new String[4][1];
	 String last_price = "";
	   String pct_change = "";
	    String abs_change = "";
	    
	    String DOW_change = "";
	    String NASADQ_change = "";
	    
	    String ticker_code = "";
		String htmlcode = "";
		
		public econdata getPrice (String ticker) throws IOException
		{
			last_price = "";
			pct_change = "";
			abs_change = "";
			htmlcode = "";
			
			String value = "";
			econdata econ_instance = new econdata();
			ticker_code = ticker;
			URL yahoo = new URL("http://finance.yahoo.com/q?s=" + ticker_code);
			BufferedReader in = new BufferedReader(
						new InputStreamReader(
						yahoo.openStream()));
	
			String inputLine;
	
			while ((inputLine = in.readLine()) != null)
				htmlcode += inputLine;
			//System.out.println(htmlcode);
		    // Create a pattern to match breaks
		    Pattern p = Pattern.compile(".*yfs_l10_" + ticker_code + "\">(.*?)</.*");
		    Matcher fit = p.matcher(htmlcode);
		    fit.find();
		    last_price = fit.group(1);
		    
		    p = Pattern.compile("yfi-price-change-(up|down)+[\\s]*\">(.*?)<");
		    fit = p.matcher(htmlcode);
		    int count = 1;
		    while(fit.find())
		    {
		    	if(fit.group(1).equals("down"))
	    			value = "-" + fit.group(2);
		    	else
		    		value = fit.group(2);
		    	switch(count)
		    	{
		    		
		    		case 1: DOW_change = value; break;
		    		case 2: NASADQ_change = value; break;
		    		case 4: abs_change = value; break;
		    		case 3: pct_change = value; break;
		    	}
		    	count++;
		    	if(count==5)
		    		break;
		    }
		    System.out.println("Last price: " + last_price);
		    System.out.println("DOW change: " + DOW_change);
		    System.out.println("NASDAQ change: " + NASADQ_change);
		    System.out.println("Abs change: " + abs_change);
		    System.out.println("Pct change: " + pct_change);
		    in.close();
		    
		    econ_instance.last_price = last_price;
		    econ_instance.DOW_change = DOW_change;
		    econ_instance.NASADQ_change = NASADQ_change;
		    econ_instance.abs_change = abs_change;
		    econ_instance.pct_change = pct_change;
		    
		    
		    
		    
		    return econ_instance;
		}
		   
}

class econdata implements Serializable
{
	String last_price = "";
	String pct_change = "";
    String abs_change = "";
    
    String DOW_change = "";
    String NASADQ_change = "";
    
    String ticker_code = "";
	String htmlcode = "";
}

class stockgui extends JFrame
{
	String price = "";
	JTextField last_price_display ;
	JTextField ticker_display;
	JTextField abs_change_display;
	JTextField pct_change_display;
	JTextField nasdaq_change_display;
	JTextField dow_change_display;
	
	price_getter engine;
	econdata data;
	
	public stockgui()
	{
		engine = new price_getter();
		setLayout(new GridLayout(7,2));
		JButton getQuote_button = new JButton("Get Quote");
		getQuote_button.addActionListener(new getQuote_Listener());
		JLabel last_price_label = new JLabel("Last Price:");
		JLabel ticker_label = new JLabel("Stock Ticker:");
		JLabel pct_change_label = new JLabel("Absolute Change:");
		JLabel abs_change_label = new JLabel("Percentage Change:");
		JLabel dow_change_label = new JLabel("Dow Change:");
		JLabel nasdaq_change_label = new JLabel("Nasdaq Change:");
		ticker_display = new JTextField("Enter Stock Ticker Here");
		last_price_display = new JTextField("Price of the stock");
		abs_change_display = new JTextField("");
		pct_change_display = new JTextField("");
		nasdaq_change_display = new JTextField("");
		dow_change_display = new JTextField("");
		add(ticker_label);
		add(ticker_display);
		add(last_price_label);
		add(last_price_display);
		add(abs_change_label);
		add(abs_change_display);
		add(pct_change_label);
		add(pct_change_display);
		add(dow_change_label);
		add(dow_change_display);
		add(nasdaq_change_label);
		add(nasdaq_change_display);
		
		add(getQuote_button);
		JMenuBar menuBar;
		JMenu menu;
		JMenu editMenu;
		JMenuItem menuItem = new JMenuItem("Save Ticker");
		menuItem.addActionListener(new exportFunction());
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		editMenu = new JMenu("Edit");
		menu.add(menuItem);
		menuBar.add(menu);
		menuBar.add(editMenu);
		setJMenuBar(menuBar);
		
	}
	
	private class exportFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane.showMessageDialog(null, "YOO");
//			OutputStream os = null;
//			try {
//				os = new FileOutputStream("sdfdsf");
//			} catch (FileNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			ObjectOutput oo = null;
//			try {
//				oo = new ObjectOutputStream(os);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			try {
//				oo.writeObject(data);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			try {
//				oo.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			Object newObj = null;
			InputStream is = null;
			try {
				is = new FileInputStream("sdfdsf");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ObjectInput oi = null;
			try {
				oi = new ObjectInputStream(is);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				newObj = oi.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			econdata temp = (econdata) newObj;
			System.out.println("The saved ticker code was " + temp.DOW_change);
			try {
				oi.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	
	private class getQuote_Listener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				data = engine.getPrice(ticker_display.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, "Getting quote");
			//price = data.last_price;
			
			last_price_display.setText(data.last_price);
			
			if(isNegative(data.abs_change))
				abs_change_display.setForeground(Color.red);
			else
				abs_change_display.setForeground(Color.green);
			abs_change_display.setText(data.abs_change);
			
			if(isNegative(data.pct_change))
				pct_change_display.setForeground(Color.red);
			else
				pct_change_display.setForeground(Color.green);
			pct_change_display.setText(data.pct_change);
			
			if(isNegative(data.NASADQ_change))
				nasdaq_change_display.setForeground(Color.red);
			else
				nasdaq_change_display.setForeground(Color.green);
			nasdaq_change_display.setText(data.NASADQ_change);
			
			if(isNegative(data.DOW_change))
				dow_change_display.setForeground(Color.red);
			else
				dow_change_display.setForeground(Color.green
						);
			dow_change_display.setText(data.DOW_change);
			
			//validate();
		}
		
	}
	
	private static boolean isNegative (String s)
	{
		if(s.charAt(0) == '-')
			return true;
		else
			return false;
	}
}

