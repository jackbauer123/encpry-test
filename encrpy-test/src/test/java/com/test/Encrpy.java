package com.test;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.junit.Test;


public class Encrpy {
	
	/*
	 * 消息摘要
	 * 
	 * */
	@Test
	public void messagetest() throws Exception{
		String beforeDegist = "asdf";    
		System.out.println("摘要前:"+beforeDegist);      
		    
		//初始信息要转换成字节流的形式    
		byte[] plainText = beforeDegist.getBytes("UTF8");    
		  
		//使用getInstance("算法")来获得消息摘要,这里使用SHA-1的160位算法或者MD5算法  
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");    
		//MessageDigest messageDigest = MessageDigest.getInstance("MD5");    
		    
		System.out.println(messageDigest.getProvider().getInfo());    
		    
		//开始使用算法    
		messageDigest.update(plainText);    
		    
		//输出算法运算结果    
		//String afterDegist = new String(base64Encode(messageDigest.digest()));    
		//System.out.println("摘要后:"+afterDegist); 
		
	}
	
	@Test
	public void privateEncrpy() throws Exception{
		String before = "aXXXsdf"; 
        byte[] plainText = before.getBytes("UTF-8");
        //得到一个使用AES算法的KeyGenerator的实例    
        KeyGenerator keyGen = KeyGenerator.getInstance("AES"); 
        //定义密钥长度128位    
        keyGen.init(128);
        //通过KeyGenerator产生一个key（密钥算法刚才已定义，为AES）    
        Key key = keyGen.generateKey();
        System.out.println("Finish generating AES key="+key); 
        //获得一个私钥加密类Cipher，定义Cipher的基本信息：ECB是加密方式，PKCS5Padding是填充方法    
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        // 把刚才生成的key当作参数，初始化使用刚才获得的私钥加密类，Cipher.ENCRYPT_MODE意思是加密    
        cipher.init(Cipher.ENCRYPT_MODE, key); 
        //私钥加密类Cipher进行加密，加密后返回一个字节流byte[]    
        byte[] cipherText = cipher.doFinal(plainText);
        //以UTF8格式把字节流转化为String    
        String after1 = new String(cipherText,"UTF-8");    
        
        System.out.println("用私钥加密完成："+after1); 
        
        //使用私钥对刚才加密的信息进行解密，看看是否一致，Cipher.DECRYPT_MODE意思是解密钥    
        cipher.init(Cipher.DECRYPT_MODE, key); 
        
        //对刚才私钥加密的字节流进行解密，解密后返回一个字节流byte[]    
        byte[] newPlainText = cipher.doFinal(cipherText);  
        
        
        String after2 = new String(cipherText,"UTF-8");    
        
        System.out.println("用私钥解密完成："+after1); 
        
        // String after2 = new String(base64Encode(newPlainText));    
        // System.out.println("用私钥解密完成："+after2);
        
	}
	
	@Test
	public void publicEnrpy() throws Exception{
		 	String before = "asdf";           
	       byte[] plainText = before.getBytes("UTF8");    
	           
	       //产生一个RSA密钥生成器KeyPairGenerator(顾名思义：一对钥匙生成器)    
	       KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");    
	    
	       //定义密钥长度1024位    
	       keyGen.initialize(1024);    
	  	  
	       //通过KeyPairGenerator产生密钥,注意：这里的key是一对钥匙！！    
	       KeyPair key = keyGen.generateKeyPair();    
	  
	       //获得一个RSA的Cipher类，使用公钥加密    
	       Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");    
	       //System.out.println("/n" + cipher.getProvider().getInfo());    
	  
	       //Cipher.ENCRYPT_MODE意思是加密，从一对钥匙中得到公钥 key.getPublic()    
	       cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());    
	  
	       //用公钥进行加密，返回一个字节流    
	       byte[] cipherText = cipher.doFinal(plainText);    
	  
	       //以UTF8格式把字节流转化为String    
	       String after1 = new String(cipherText, "UTF8");    
	   
	       System.out.println("用公钥加密完成："+after1);    
	  
	       //Cipher.DECRYPT_MODE意思是解密模式，从一对钥匙中得到私钥 key.getPrivate()    
	       cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());    
	  
	  
	       //用私钥进行解密，返回一个字节流    
	       byte[] newPlainText = cipher.doFinal(cipherText);    
	  
	       String after2 = new String(newPlainText, "UTF8");    
	       System.out.println("用私钥解密完成："+after2);  
		
		
	}
	
	
	
	
	
}
