package com.mobileleader.image.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
/**
 * 자기 자신의 IP 얻기
 * 
 * @author bkcho
 *
 */
public class NetworkUtil {
	private static final Logger logger = LoggerFactory.getLogger(NetworkUtil.class);
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static String getIp() {
		String ret = "";
         try {
				InetAddress Address = InetAddress.getLocalHost();
				ret = Address.getHostAddress();
			} catch (UnknownHostException e) {
				ret = null;
			}  
         return ret;
	}
	
	public static String getIpEx() {
		
		String ret = "";
		
		try {
			if (isWindows()) {
	            logger.info("This is Windows");

				InetAddress Address = InetAddress.getLocalHost();
				ret = Address.getHostAddress();

	        } else if (isMac()) {
	            logger.info("This is Mac");
	        } else if (isUnix()) {
	            logger.info("This is Unix or Linux");

				Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
				
				while (nienum.hasMoreElements()) {
		            NetworkInterface ni = nienum.nextElement();
		            Enumeration<InetAddress> kk = ni.getInetAddresses();
		            
		            while (kk.hasMoreElements()) {
		    			InetAddress inetAddress = (InetAddress) kk.nextElement();
		    			logger.info(inetAddress.getHostName()+" : "+inetAddress.getHostAddress());		    			
		    			ret = inetAddress.getHostAddress();
		    			 
		    			if (inetAddress.getHostName().equals("localhost")) {		    				
		    			} else if (inetAddress.getHostName().equals("0:0:0:0:0:0:0:1%lo")) {
		    			} else {
		    				break;	
		    			}
		            }
		        }

	            logger.info("windows style......");

				InetAddress Address = InetAddress.getLocalHost();
				ret = Address.getHostAddress();
				
				logger.info(ret);

	        } else if (isSolaris()) {
	            logger.info("This is Solaris");
	        } else {
	            logger.info("Your OS is not support!!");
	        }

		} catch(Exception e) {
			ret = null;
		}
		return ret;
	}
		
	
	public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }
  
    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }
  
    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }
  
    public static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

}
