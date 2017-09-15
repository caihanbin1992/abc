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

//����Properties�ೣ�õĲ���
public class PropertyReader {
  //����Key��ȡValue
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
  
  //��ȡProperties��ȫ����Ϣ
  public static Map<String, String> GetAllProperties(String filePath) throws IOException {
      Properties pps = new Properties();
      InputStream in = new BufferedInputStream(new FileInputStream(filePath));
      pps.load(in);
      Enumeration<?> en = pps.propertyNames(); //�õ������ļ�������
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
  
  //д��Properties��Ϣ
  public static void WriteProperties (String filePath, String pKey, String pValue) throws IOException {
      Properties pps = new Properties();
      
      InputStream in = new FileInputStream(filePath);
      //���������ж�ȡ�����б�����Ԫ�ضԣ� 
      pps.load(in);
      //���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�  
      //ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
      OutputStream out = new FileOutputStream(filePath);
      pps.setProperty(pKey, pValue);
      //���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��  
      //���� Properties ���е������б�����Ԫ�ضԣ�д�������  
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
