package com.test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

import org.junit.Test;

public class Signatures {
	
	/**  
	  * 此例子是数字签名的例子，使用RSA私钥对消息摘要（这里指的是原始数据）进行签名，然后使用公钥验证签名  
	  *   
	  * A通过使用B的公钥加密数据后发给B，B利用B的私钥解密就得到了需要的数据（进过B公钥加密的数据只有B的私钥能够  
	  * 解开，C没有B的私钥，所以C解不开，但C可以使用B的公钥加密一份数据发给B，这样一来，问题来了，B收到的数据到  
	  * 底是A发过来的还是C发过来的呢）  
	  * 由于私钥是唯一的，那么A就可以利用A自己的私钥进行加密，然后B再利用A的公钥来解密，就可以确定：一定是A的消  
	  * 息，数字签名的原理就基于此  
	  *   
	  * 总结：A想将目标数据传给B，此时A需要准备1和2两部分  
	  * 1：A使用B的公钥将原始信息加密，以起到保密作用（只有B的私钥能解开，其他人使用其他钥匙都解不开，当然就保密咯）  
	  * 2：A使用A的私钥将原始信息的摘要进行签名，以起到接收方B确定是A发过来的作用（A用A的私钥对目标数据的摘要进行签  
	  * 名，然后传给B,同时，C用C的私钥对任意信息进行签名也传给B，B想接受的是A的数据（比如说一个转帐请求），于是B  
	  * 就通过A的公钥对接受到的两个信息进行解密，解开的就是A（A的公钥能且只能解开A的私钥加密的数据））  
	  */   
	
	
	
	@Test
	public void signatureTest() throws Exception{
		 String before = "asdfa"; 
		 byte[] plainText = before.getBytes();    
		
		 //形成RSA公钥对    
		 KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");    
		 keyGen.initialize(1024);    
		 KeyPair key = keyGen.generateKeyPair();    
		  
		 //使用私钥签名**********************************************************    
		 Signature sig = Signature.getInstance("SHA1withRSA");    
		 sig.initSign(key.getPrivate());//sig对象得到私钥    
		 	  
		 //签名对象得到原始数据    
		 sig.update(plainText);//sig对象得到原始数据(现实中用的是原始数据的摘要，摘要的是单向的，即摘要算法后无法解密)    
		 byte[] signature = sig.sign();//sig对象用私钥对原始数据进行签名，签名后得到签名signature    
		 System.out.println(sig.getProvider().getInfo());    
		  
		 String after1 = new String(signature);    
		  System.out.println("/n用私钥签名后:"+after1);    
		  
		 //使用公钥验证  
		// key = keyGen.generateKeyPair();   
		 sig.initVerify(key.getPublic());//sig对象得到公钥   
		  		  
		 //签名对象得到原始信息   
		 sig.update(plainText);//sig对象得到原始数据(现实中是摘要)    
		 //篡改数据
		 //String before1 = "asdfasss"; 
		 //byte[] plainText1 = before.getBytes();  
		 //sig.update(plainText1);
		 
		 
	     if (sig.verify(signature)) {//sig对象用公钥解密签名signature得到原始数据(即摘要)，一致则true    
	         System.out.println("签名验证正确！！"+new String(plainText));    
	     } else {    
	         System.out.println("签名验证失败！！");    
	     }    
		
	}
}
