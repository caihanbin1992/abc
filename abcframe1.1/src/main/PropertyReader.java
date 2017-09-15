package main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//关于Properties类常用的操作
public class PropertyReader {
  //根据Key读取Value
  public static String GetValueByKey(String filePath, String key) {
      Properties pps = new Properties();
      try {
          InputStream in = new BufferedInputStream (new FileInputStream(filePath));  
          pps.load(in);
          String value = pps.getProperty(key);
//          System.out.println(key + " = " + value);
          return value;
          
      }catch (IOException e) {
          e.printStackTrace();
          return null;
      }
  }
  
  //读取Properties的全部信息
  public static Map<String, String> GetAllProperties(String filePath) throws IOException {
      Properties pps = new Properties();
      InputStream in = new BufferedInputStream(new FileInputStream(filePath));
      pps.load(in);
      Enumeration<?> en = pps.propertyNames(); //得到配置文件的名字
      Map<String, String> result = new HashMap<String, String>();
      while(en.hasMoreElements()) {
          String strKey = (String) en.nextElement();
          String strValue = pps.getProperty(strKey);
//          System.out.println(strKey + "=" + strValue);
          result.put(strKey, strValue);
      }
      return result;
  }
  
  public static List<String> getAllValues(String filePath) throws IOException{
	  Map<String, String> map = GetAllProperties(filePath);
	  List<String> list = new ArrayList<>(map.values());
	  return list;
  }
  
  //写入Properties信息
  public static void WriteProperties (String filePath, String pKey, String pValue) throws IOException {
      Properties pps = new Properties();
      
      InputStream in = new FileInputStream(filePath);
      //从输入流中读取属性列表（键和元素对） 
      pps.load(in);
      //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。  
      //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
      OutputStream out = new FileOutputStream(filePath);
      pps.setProperty(pKey, pValue);
      //以适合使用 load 方法加载到 Properties 表中的格式，  
      //将此 Properties 表中的属性列表（键和元素对）写入输出流  
      pps.store(out, "Update " + pKey + " name");
  }
  
  public static void main(String [] args) throws IOException{
      /*String value = GetValueByKey("src/conf/function.properties", "5");
      System.out.println(value);*/
      //GetAllProperties("Test.properties");
//      WriteProperties("src/conf/function.properties","5", "sphere.30D");
      List<String> fNs = getAllValues("src/conf/function.properties");
      System.out.println(fNs);
  }
}
