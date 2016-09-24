/*
 * @author ljw
 * @since 2016.03.31
 *
 * +buffer:StringBuffer
 * +putin2Buffer(prmURL):void
 * 
 */ 


package com.geocoderAdd;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebReaderMacro {
  public StringBuffer buffer;
  public WebReaderMacro(){}
  public void Putin2Buffer(String prmURL){
    HttpURLConnection connection = null;
    try {
      // 요청 URL
      URL url = new URL(prmURL);
      
      connection = (HttpURLConnection)url.openConnection();
      // 요청 방식 (GET or POST)
      connection.setRequestMethod("GET");
      // 요청 응답 타임아웃 설정
      connection.setConnectTimeout(3000);
      // 읽기 타임아웃 설정
      connection.setReadTimeout(3000);
      // 컨텐츠의 캐릭터셋이 euc-kr 이라면 (connection.getInputStream(), "euc-kr")
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"EUC-KR"));
      buffer = new StringBuffer(); 
      int read = 0; 
      char[] cbuff = new char[1024]; 
      while ((read = reader.read(cbuff)) > 0) {
        buffer.append(cbuff, 0, read); 
      }
      int i=0;
      reader.close();    
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}

/*
 * 
 * codeFinished 16.04.03
 * file-classifying at 16.05.03
 * 
 */