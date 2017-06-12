package com.test;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.junit.Test;

public class DataCertificate {
	
	
	/**  
	  * 此例是对“数字证书”文件的操作  
	   * java平台（在机器上安装jdk）为你提供了密钥库(证书库)，cmd下提供了keytool命令就可以创建证书库  
	   *   
	   * 在运行此例前：  
	   * 在c盘目录下创建一个证书，指定证书库为BocsoftKeyLib，创建别名为TestCertification的一条证书，它指定用   
	   * RSA 算法生成，且指定密钥长度为1024，证书有效期为1年  
	   * 导出证书文件为TC.cer已存于本地磁盘C:/  
	   * 密码是qazzaq 
	   */   
	@Test
	public void dataCert(){
			try {    
		        //前提：将证书库中的一条证书导出到证书文件(我写的例子里证书文件叫TC.cer)    
		        //从证书文件TC.cer里读取证书信息    
		        CertificateFactory cf = CertificateFactory.getInstance("X.509");   
		  
		  
		        FileInputStream in = new FileInputStream("G:/TC.crt");   
		  
		  
		        //将文件以文件流的形式读入证书类Certificate中   
		        Certificate c = cf.generateCertificate(in);   
		        System.err.println("转换成String后的证书信息："+c.toString());  
		          
		        //或者不用上面代码的方法，直接从证书库中读取证书信息，和上面的结果一摸一样    
		        String pass="qazzaq";       
		        FileInputStream in2=new FileInputStream("G:/BocsoftKeyLib");       
		  
		  
		        KeyStore ks=KeyStore.getInstance("JKS");       
		        ks.load(in2,pass.toCharArray());    
		        String alias = "TestCertification"; //alias为条目的别名    
		        Certificate c1=ks.getCertificate(alias);    
		        System.err.println("转换成String后的证书信息："+c.toString());    
		          
		        //获取获取X509Certificate类型的对象，这是证书类获取Certificate的子类，实现了更多方法    
		        X509Certificate t=(X509Certificate)c;    
		        //从信息中提取需要信息    
		        System.out.println("版本号:"+t.getVersion());       
		        System.out.println("序列号:"+t.getSerialNumber().toString(16));       
		        System.out.println("主体名："+t.getSubjectDN());       
		        System.out.println("签发者："+t.getIssuerDN());       
		        System.out.println("有效期："+t.getNotBefore());       
		        System.out.println("签名算法："+t.getSigAlgName());   
		  
		  
		        byte [] sig=t.getSignature();//签名值    
		        PublicKey pk = t.getPublicKey();     
		        byte [] pkenc=pk.getEncoded();       
		        System.out.println("公钥：");       
		        for(int i=0;i<pkenc.length;i++){    
		            System.out.print(pkenc[i]+",");       
		        }    
		        System.err.println();  
		            
		        //证书的日期有效性检查，颁发的证书都有一个有效性的日期区间    
		        Date TimeNow=new Date();          
		        t.checkValidity(TimeNow);       
		        System.out.println("证书的日期有效性检查:有效的证书日期！"); 
			} 
		        //验证证书签名的有效性，通过数字证书认证中心(CA)机构颁布给客户的CA证书，比如：caroot.crt文件    
		        //我手里没有CA颁给我的证书，所以下面代码执行不了  
			
		       	/*FileInputStream in3=new FileInputStream("caroot.crt");     
		        //获取CA证书  
		        Certificate cac = cf.generateCertificate(in3); 
		 
		 
		        //获取CA的公钥     
		        PublicKey pbk=cac.getPublicKey();     
		        //c为本地证书，也就是待检验的证书，用CA的公钥校验数字证书c的有效性  
		        c.verify(pbk);               
		    } catch(CertificateExpiredException e){//证书的日期有效性检查:过期      
		        System.out.println("证书的日期有效性检查:过期");         
		    } catch(CertificateNotYetValidException e){ //证书的日期有效性检查:尚未生效      
		        System.out.println("证书的日期有效性检查:尚未生效");      
		    } catch (CertificateException ce) {   
		        ce.printStackTrace();   
		    } catch (FileNotFoundException fe) {   
		        fe.printStackTrace();   
		    } /*catch (IOException ioe){  
		          
		    } catch (KeyStoreException kse){  
		          
		    }*/ catch (Exception e){    
		        e.printStackTrace();    
		    }   
		}     
	
	
}
