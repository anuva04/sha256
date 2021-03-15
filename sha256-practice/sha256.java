
/**
 * Write a description of sha256 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.*;
import java.math.BigInteger;
import java.lang.Math;
import java.util.Arrays;

public class sha256 {
    static String reverse(String input){ 
        char[] a = input.toCharArray(); 
        int l, r = 0; 
        r = a.length - 1; 
  
        for (l = 0; l < r; l++, r--){  
            char temp = a[l]; 
            a[l] = a[r]; 
            a[r] = temp; 
        }
        return String.valueOf(a); 
    }
    
    public String convertMsgToBinary(String msg){
        int n = msg.length();
        String res = "";
        for(int i=0; i<n; i++){
            int val = Integer.valueOf(msg.charAt(i));
            String bin = "";
            while(val>0){
                if (val % 2 == 1) 
                { 
                    bin += '1'; 
                } 
                else
                    bin += '0'; 
                val /= 2;
            }
            bin = reverse(bin);
            res += bin;
        }
        return res;
        
    }
    
    String[] H0 = {"0x6a09e667", "0xbb67ae85", "0x3c6ef372", "0xa54ff53a", "0x510e527f", "0x9b05688c", "0x1f83d9ab", "0x5be0cd19"};
    
    String[] K = {"0x428a2f98", "0x71374491", "0xb5c0fbcf", "0xe9b5dba5", "0x3956c25b", "0x59f111f1", "0x923f82a4", "0xab1c5ed5",
                  "0xd807aa98", "0x12835b01", "0x243185be", "0x550c7dc3", "0x72be5d74", "0x80deb1fe", "0x9bdc06a7", "0xc19bf174",
                  "0xe49b69c1", "0xefbe4786", "0x0fc19dc6", "0x240ca1cc", "0x2de92c6f", "0x4a7484aa", "0x5cb0a9dc", "0x76f988da",
                  "0x983e5152", "0xa831c66d", "0xb00327c8", "0xbf597fc7", "0xc6e00bf3", "0xd5a79147", "0x06ca6351", "0x14292967",
                  "0x27b70a85", "0x2e1b2138", "0x4d2c6dfc", "0x53380d13", "0x650a7354", "0x766a0abb", "0x81c2c92e", "0x92722c85",
                  "0xa2bfe8a1", "0xa81a664b", "0xc24b8b70", "0xc76c51a3", "0xd192e819", "0xd6990624", "0xf40e3585", "0x106aa070",
                  "0x19a4c116", "0x1e376c08", "0x2748774c", "0x34b0bcb5", "0x391c0cb3", "0x4ed8aa4a", "0x5b9cca4f", "0x682e6ff3",
                  "0x748f82ee", "0x78a5636f", "0x84c87814", "0x8cc70208", "0x90befffa", "0xa4506ceb", "0xbef9a3f7", "0xc67178f2"
                 };
                
    
    String rotl(String str, int d){
        String ans = str.substring(d) + str.substring(0, d);
        return ans;
    }
    String rotr(String str, int d){
        return rotl(str, str.length()-d);
    }
    
    String Ch(String x, String y, String z){
        BigInteger x1 = new BigInteger(x);
        BigInteger y1 = new BigInteger(y);
        BigInteger z1 = new BigInteger(z);
        BigInteger a = x1.and(y1);
        BigInteger b = x1.not();
        BigInteger c = b.and(z1);
        String res = a.xor(c).toString(2);
        return res;
    }
    
    String Maj(String x, String y, String z){
        BigInteger x1 = new BigInteger(x);
        BigInteger y1 = new BigInteger(y);
        BigInteger z1 = new BigInteger(z);
        BigInteger a = x1.and(y1);
        BigInteger b = x1.and(z1);
        BigInteger c = y1.and(z1);
        String res = a.xor(b).xor(c).toString(2);
        return res;
    }
    
    String Σ0(String x){
        BigInteger a = new BigInteger(rotr(x, 2));
        BigInteger b = new BigInteger(rotr(x, 13));
        BigInteger c = new BigInteger(rotr(x, 22));
        String res = a.xor(b).xor(c).toString(2);
        return res;
    }
    
    String Σ1(String x){
        BigInteger a = new BigInteger(rotr(x, 6));
        BigInteger b = new BigInteger(rotr(x, 11));
        BigInteger c = new BigInteger(rotr(x, 25));
        String res = a.xor(b).xor(c).toString(2);
        return res;
    }
    
    String σ0(String x){
        BigInteger a = new BigInteger(rotr(x, 7));
        BigInteger b = new BigInteger(rotr(x, 18));
        BigInteger c1 = new BigInteger(x);
        BigInteger c = c1.shiftRight(3); // How to do unsigned right shift?
        String res = a.xor(b).xor(c).toString(2);
        return res;
    }
    
    String σ1(String x){
        BigInteger a = new BigInteger(rotr(x, 17));
        BigInteger b = new BigInteger(rotr(x, 19));
        BigInteger c1 = new BigInteger(x);
        BigInteger c = c1.shiftRight(10); // How to do unsigned right shift?
        String res = a.xor(b).xor(c).toString(2);
        return res;
    }
    
    String pad(String m){
        String binaryMsg = convertMsgToBinary(m);
        int len = binaryMsg.length();
        int pad1Len = 512 - (len+1+64)%512;
        int pad2Len = 64 - Integer.toBinaryString(len).length();
        
        char[] c1 = new char[pad1Len];
        char[] c2 = new char[pad2Len];
        
        Arrays.fill(c1, '0');
        Arrays.fill(c2, '0');
        
        String res = binaryMsg + c1 + c2 + Integer.toBinaryString(len);
        return res;
    }
    
    // Driver code 
    public void main(){ 
        String s = "geeks"; 
        String ans = convertMsgToBinary(s);
        System.out.println(ans);
        
        System.out.println(rotr("GeeksforGeeks", 3));
    }
}
