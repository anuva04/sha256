package SHA256;

import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.swing.*;


public class HashForm extends JFrame implements ActionListener {
	
   JPanel panel;
   JLabel enter, hash, hashval, names;
   JTextField message;
   JButton submit;
   
   HashForm () {
	   
	   int w = 600;
	   int h = 600;
	   int curr = h/12;
      
       enter = new JLabel();
       enter.setText("Enter the message to be hashed :");
       enter.setLocation(h/12,curr);
       enter.setSize(w-100,h/12);
       curr+=(h/12+h/30);
            
       message = new JTextField();
       message.setLocation(h/12,curr);
       message.setSize(w-100,h/12);
       curr+=(h/12+h/30);
       
       submit = new JButton("Get hash!");
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
       names.setText("Submitted By: Anuva, Trishna, Monjita, Nandini, Arshita, Zoheb");
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

      submit.addActionListener(this);
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
	   
      String m = message.getText();
      HashAlgo n = new HashAlgo();
    
  	  byte[] b = n.hash(m.getBytes(StandardCharsets.US_ASCII));
  	  
  	  StringBuilder sb = new StringBuilder("");
  	
  	  for(byte i : b)
  	    sb.append(String.format("%02x",i)); 
  	  
  	  String hexval = sb.toString();
  	
  	  hashval.setText(hexval);
   }
}