package com.mobileleader.edoc.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA2 해싱값 
 * 
 * @author bkcho
 *
 */
public class CryptoSHA2 {

	//SHA-256    
    public static String getSHA256Hashing(String str) {
        String rtnSHA = "";
        
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
            sh.update(str.getBytes()); 
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer(); 
            
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            rtnSHA = sb.toString();
            
        }catch(NoSuchAlgorithmException e){
            rtnSHA = null; 
        }
        return rtnSHA;
    }
    
    public static String getEncodedSHA256Hashing(String a_origin){
        String encryptedSHA256 = "";
        MessageDigest md = null;
        
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(a_origin.getBytes(), 0, a_origin.length());
            encryptedSHA256 = new BigInteger(1, md.digest()).toString(16); 
        } catch (NoSuchAlgorithmException e) {
        	encryptedSHA256 = null;
        }
        
        return encryptedSHA256;
    }
    
    public static String getIdEnc(String id) {
		if (id.length() == 13) {
			return CryptoSHA2.getEncodedSHA256Hashing(id);	
		}
		
		return id;
    }
}
