package com.geocoderAdd;

import java.sql.*;
import java.util.StringTokenizer;
import java.lang.String;
import java.util.*;
import java.net.URLEncoder;

public class AddTestDrive {
   public static void main(String[] args){
      //for wrm
      ArrayList<Integer> idxList = new ArrayList<Integer>();
      ArrayList<String> addressList = new ArrayList<String>();
      WebReaderMacro wrm = new WebReaderMacro();
      
      //sql connection part
      Connection conn = null;
      
      
      String id = "root";
      String pw = "root";
      String url = "jdbc:mysql://localhost:3306/";
      String driver = "com.mysql.jdbc.Driver";
      Statement stmt=null;
      ResultSet rs;
      
       try{
         Class.forName(driver);
         System.out.println("<Connecting..>");
         conn = DriverManager.getConnection(url,id,pw);
         stmt = conn.createStatement();
         
         System.out.println("Connecting is successful<br>");
         String sql = "use placedb";
         stmt.execute(sql);
         
         sql= "select * from placetable where lat is NULL";
         System.out.println(sql);
         rs=stmt.executeQuery(sql);
         while(rs.next()){
            idxList.add(rs.getInt("idx"));
            addressList.add(rs.getString("address"));
            
            /*
            String name = rs.getString("name");
            String product = rs.getString("product");
            String tellN = rs.getString("tellN");
            String tmpDong = rs.getString("dong");
            double lat = rs.getDouble("lat");
            double lng = rs.getDouble("lng");
            */
         }
         for(int i=0;i<idxList.size();i++){
            StringTokenizer stn = null;
            String tmpAddress = addressList.get(i);
            stn= new StringTokenizer(tmpAddress);
            StringBuffer tmpsbf = new StringBuffer();
            while(stn.hasMoreElements()){
               String tmpString = stn.nextToken();
               if(!tmpString.contains("¾ÆÆÄÆ®")){
                  tmpsbf.append(tmpString);
               }else{
                  tmpsbf.append(" ");
               }
               tmpsbf.append(" ");
            }
            tmpAddress=tmpsbf.toString();
            System.out.println("\n"+idxList.get(i));
            System.out.println(tmpAddress);
            //for string utf encoding
            tmpAddress = URLEncoder.encode(tmpAddress,"UTF-8");
            wrm.Putin2Buffer("https://maps.googleapis.com/maps/api/geocode/json?address="+tmpAddress+"&sensor=false");
            stn= new StringTokenizer(wrm.buffer.toString());
            while(stn.hasMoreElements()){
               String latString = null;
               String lngString = null;
               String tmpString = stn.nextToken();
               if(tmpString.contains("location")){
                  stn.nextToken();
                  stn.nextToken();
                  stn.nextToken();
                  stn.nextToken();
                  latString = stn.nextToken();
                  System.out.println(latString);
                  stn.nextToken();
                  stn.nextToken();
                  lngString = stn.nextToken();
                  System.out.println(lngString);
                  sql="update placetable set lat="+latString+" lng="+lngString+" where idx like "+idxList.get(i);
                  System.out.println(sql);
                  stmt.execute(sql);
                  break; // for while break;
               }
            }
         }
         
         
         
         stmt.close();
         conn.close();
         
       }catch(Exception e) {
         e.printStackTrace();
       }
   }
}