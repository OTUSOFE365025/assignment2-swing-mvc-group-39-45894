// This window emulates the scanning of an item. Every time the buttom is pressed
// it will send a notification of a UPC code

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;


public class Scanner {
	// Scanner uses Swing framework to create a UPC code
	 private JFrame frame;
	 private JPanel scannerPanel;
	 private JButton scanButton;
	 private Random random = new Random();//creates a random object
	 private ArrayList<String> upcCodes = new ArrayList<>();

	 
	 public Scanner() throws IOException {
		  frame = new JFrame("Scanner");
		  frame.getContentPane().setLayout(new BorderLayout());
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setSize(100, 100);
		  frame.setLocation(300,50);
		  frame.setVisible(true);
		  
		  
		  // Create UI elements
		  scanButton = new JButton("Scan");
		  scannerPanel = new JPanel();
		  
		  // Add UI element to frame
		  scannerPanel.add(scanButton);
		  frame.getContentPane().add(scannerPanel);

		  LoadFromFile("products.txt");
	 }

	public String generateUPC() {
		if(upcCodes.isEmpty()){
			return null;
		}
		String upc = upcCodes.get(random.nextInt(upcCodes.size()));
		return upc;
	}

	public void LoadFromFile(String file) throws IOException {
		try (java.util.Scanner scan = new java.util.Scanner(new java.io.File(file), java.nio.charset.StandardCharsets.UTF_8)) {


		while(scan.hasNextLine()){
				String line = scan.nextLine();

				if(line.isEmpty()) {//skip line if blank
					continue;
				}

				if(line.toLowerCase().startsWith("upc")){//skip line if it is the header line
					continue;
				}

				String[] parts = line.split("\\s+");
				if (parts.length >= 1) {
					upcCodes.add(parts[0]);
				}
			}//end loop

			}//end try

		}//end method

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JPanel getScannerPanel() {
		return scannerPanel;
	}

	public void setScannerPanel(JPanel scannerPanel) {
		this.scannerPanel = scannerPanel;
	}

	public JButton getScanButton() {
		return scanButton;
	}

	public void setScanButton(JButton scanButton) {
		this.scanButton = scanButton;
	}	 
	 

}
