import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.swing.*;

//Form GUI for taking inputs from the user and displaying the output

public class HashForm extends JFrame implements ActionListener {
	
   JPanel panel;
   JLabel enter, hash, hashval, names;
   JTextField message;
   JButton submit;
   
   HashForm () {
	   
	   int w = 900; //width of the panel
	   int h = 600; //height of the panel
	   int curr = h/12; //setting y-axis value for swing components
      
       enter = new JLabel();
       enter.setText("Enter the message to be hashed :");
       enter.setLocation(h/12,curr);
       enter.setSize(w-100,h/12);
       curr+=(h/12+h/30);
            
       message = new JTextField();
       message.setLocation(h/12,curr);
       message.setSize(w-100,h/12);
       curr+=(h/12+h/30);
       
       submit = new JButton("Hash it !");
       submit.setBackground(Color.CYAN);
       submit.setLocation(h/12,curr);
       submit.setSize(w-100,h/12);
       curr+=(h/12+h/30);
      
       hash = new JLabel();
       hash.setText("Hash value :");
       hash.setLocation(h/12,curr);
       hash.setSize(w-100,h/12);
       curr+=(h/12+h/30);
      
       hashval = new JLabel();
       hashval.setLocation(h/12,curr);
       hashval.setSize(w-100,h/12);
       hashval.setOpaque(true);
       hashval.setBackground(Color.WHITE);
       hashval.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
       curr+=(h/12+h/30);
       
       names = new JLabel();
       names.setText("Submitted By: Anuva (1814001), Trishna (1814017), Monjita (1814019), Nandini (1814021), Arshita (1814050), Zoheb (1814051)");
       names.setLocation(h/12,curr);
       names.setSize(w-100,h/12);
       
            
       panel = new JPanel();
       panel.setLayout(null);
       panel.add(enter);
       panel.add(message);
       panel.add(submit);
       panel.add(hash);
       panel.add(hashval);
       panel.add(names);
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      submit.addActionListener(this); //adding action listener to the button, so that output is diplayed when clicked
      add(panel, BorderLayout.CENTER);
      setTitle("SHA-256 Calculator");
      setSize(w,h);
      setVisible(true);
      setResizable(false);
      
   }
   
   public static void main(String[] args) {
      new HashForm();
   }
   
   public void actionPerformed(ActionEvent ae) {
	   
	  //This method runs when the button is clicked
	   
      String m = message.getText(); // user entered message string is read
      HashAlgo n = new HashAlgo();
      
      // The message String is parsed into a byte array where every element represents the ASCII value 
      // of the characters of the String

      byte[] b = n.hash(m.getBytes(StandardCharsets.US_ASCII)); //hash method is called
      
    //The hash method returns the bytes of the hash value

      StringBuilder sb = new StringBuilder("");

      for(byte i : b)
         sb.append(String.format("%02x",i)); //the bytes of the hash value converted to hexadecimal gives the final output

      String hexval = sb.toString();

      hashval.setText(hexval); //the final output is displayed
   }
}