package SHA256;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.swing.*;


public class SHA256Implementation {
	
	// SHA-256 Hashing Algorithm class
	   private static class HashAlgo {

	    // ************** STEP-1 ****************
	    // Method for converting integer array to byte array
	    
	    private static byte[] intToByte(int[] intArray) {
	        ByteBuffer buf = ByteBuffer.allocate(intArray.length * Integer.BYTES);
	        for (int element : intArray) {
	            buf.putInt(element);
	        }
	        return buf.array();
	    }

	    // ****************** STEP-2 *****************
	    // SHA-256 logical functions

	    private static int ch(int x, int y, int z) {
	        return (x & y) | ((~x) & z);
	    }

	    private static int maj(int x, int y, int z) {
	        return (x & y) | (x & z) | (y & z);
	    }

	    private static int bigSig0(int x) {
	        return Integer.rotateRight(x, 2) ^ Integer.rotateRight(x, 13) ^ Integer.rotateRight(x, 22);
	    }

	    private static int bigSig1(int x) {
	        return Integer.rotateRight(x, 6) ^ Integer.rotateRight(x, 11) ^ Integer.rotateRight(x, 25);
	    }

	    private static int smallSig0(int x) {
	        return Integer.rotateRight(x, 7) ^ Integer.rotateRight(x, 18) ^ (x >>> 3);
	    }

	    private static int smallSig1(int x) {
	        return Integer.rotateRight(x, 17) ^ Integer.rotateRight(x, 19) ^ (x >>> 10);
	    }

	    // ************** STEP - 3 ************
	    // Method for padding the message

	    private static int[] pad(byte[] message) {
	    	
	        // inalBlock denotes the last block to be padded with the message
	        // Length of message array gives the number of characters in the message String

	        // We have to group the bytes into N blocks of 512 bits = 64 bytes each
	        
	        
	        int finalBlockLength = message.length % 64; // finalBlockLength represents the number of bytes remaining after creating blocks from existing message

	        int blockCount; // blockCount = number of blocks
	        if(finalBlockLength + 1 + 8 > 64) blockCount = message.length / 64 + 2;
	        else blockCount = message.length / 64 + 1;

	        // Number of integers required to represent padded message = blockCount * number of integers per block
	        final IntBuffer res = IntBuffer.allocate(blockCount * (64 / Integer.BYTES));

	        ByteBuffer buf = ByteBuffer.wrap(message);
	        int n = message.length / Integer.BYTES;
	        for (int i = 0; i < n; ++i) {
	            res.put(buf.getInt());
	        }
	        
	        // Copy the remaining bytes (less than 4, since int type = 4 bytes) and append 1 bit (rest is zero bits)
	        ByteBuffer remainder = ByteBuffer.allocate(4);
	        remainder.put(buf).put((byte) 0b10000000).rewind();
	        res.put(remainder.getInt());

	        // place original message length as 64-bit(2 bytes) integer at the end 

	        res.position(res.capacity() - 2);

	        long msgLength = message.length * 8L;
	        res.put((int) (msgLength >>> 32)); // stores first 32 bits
	        res.put((int) msgLength); // stores next 32 bits

	        return res.array();
	    }

	    // ************* STEP - 4 ******************
	    // SHA-256 constants

		private static final int[] K = {
	            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
	            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
	            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
	            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
	            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
	            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
	            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
	            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
	    };

	    // Initial hash values

	    private static final int[] H0 = {
	            0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
	    };

	    // working arrays
	    private static final int[] W = new int[64];
	    private static final int[] H = new int[8];
	    private static final int[] temp = new int[8];

	    // **************** STEP - 5 *********************
	    // MAIN HASHING ALGORITHM

	    private static byte[] hash(byte[] message) {
	        // Initial hash values are copied to an intermediate H array
	        System.arraycopy(H0, 0, H, 0, H0.length);

	        // Preprocessing (padding the message)
	        int[] words = pad(message);

	        // Parse the message into groups of 16 words each
	        int n = words.length / 16;
	        for (int i = 0; i < n; ++i) {

	            // initialize W from the block's words
	            // Preparing the message schedule
	            System.arraycopy(words, i * 16, W, 0, 16);
	            for (int t = 16; t < W.length; ++t) {
	                W[t] = smallSig1(W[t - 2]) + W[t - 7] + smallSig0(W[t - 15]) + W[t - 16];
	            }

	            // initialising temp = H
	            System.arraycopy(H, 0, temp, 0, H.length);

	            // operate on temp
	            // working variables
	            for (int t = 0; t < W.length; ++t) {
	                int t1 = temp[7] + bigSig1(temp[4]) + ch(temp[4], temp[5], temp[6]) + K[t] + W[t];
	                int t2 = bigSig0(temp[0]) + maj(temp[0], temp[1], temp[2]);
	                System.arraycopy(temp, 0, temp, 1, temp.length - 1);
	                temp[4] += t1;
	                temp[0] = t1 + t2;
	            }

	            // add values in temp to values in H
	            // intermediate hash values
	            for (int t = 0; t < H.length; ++t) {
	                H[t] += temp[t];
	            }

	        }

	        return intToByte(H);
	    }
	    
	}
	   
	   
	 //Form GUI for taking inputs from the user and displaying the output

	   private static class HashForm extends JFrame implements ActionListener {
	   	
	      private JPanel panel;
	      private JLabel enter, hash, hashval, names;
	      private JTextField message;
	      private JButton submit;
	      
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
	   
	   public static void main(String[] args) {
	         new HashForm();
	      }

}
