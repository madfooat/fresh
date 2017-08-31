package com.madfooat.billinquiry;

import com.madfooat.billinquiry.domain.Bill;
import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;


import java.util.*;
import java.math.BigDecimal;
import java.io.*;
import java.text.*;
import java.lang.reflect.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class XMLParseBillInquiryResponse implements ParseBillInquiryResponse {
	
     private static String reversDateFormate(String date){
        
        String day,monthe,year;
        day = date.substring(0,2);
        monthe = date.substring(3,5);
        year= date.substring(6);
     
        date = year+'-'+monthe+'-'+day;
      
        return date;
        
    }
    
     private static void checkCurrancyFormat(String formatText)throws InvalidBillInquiryResponse{
         
                int integerPlaces = formatText.indexOf('.');
                int decimalPlaces = formatText.length() - integerPlaces - 1;
                if(decimalPlaces >3 ){
                    throw new  InvalidBillInquiryResponse();
                }
    }
    
    private static void checkValidation(List<Bill> billList) throws InvalidBillInquiryResponse{
        
        for(Bill bill : billList) {
            
            if(bill.getDueDate() == null || bill.getDueAmount() == null){
                throw new  InvalidBillInquiryResponse();
            }else{
                if(bill.getDueDate().getTime() > Calendar.getInstance().getTimeInMillis()){
                     throw new  InvalidBillInquiryResponse();
                }
               
               checkCurrancyFormat( bill.getDueAmount().toString());
               
                if(bill.getFees() != null){
                    checkCurrancyFormat(bill.getFees().toString());
                   
                }
            }
        }

        
    }
    
   
    @Override
    public List<Bill> parse(String billerResponse) throws InvalidBillInquiryResponse {
        // Write your implementation
		 List<Bill> billList = new ArrayList<Bill>();
    String fXmlFile = billerResponse; 
    try{
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc =	dBuilder.parse(new InputSource(new ByteArrayInputStream(fXmlFile.getBytes("utf-8"))));

	doc.getDocumentElement().normalize();
	NodeList nList = doc.getElementsByTagName("bill");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
        
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Bill billresponer = new Bill();
			Element eElement = (Element) nNode;
            String dateString =  eElement.getElementsByTagName("dueDate").item(0).getTextContent();
            dateString = reversDateFormate(dateString);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
            Date newDate;
            newDate = df.parse(dateString);
			billresponer.setDueDate(newDate);
			
			String amountString =  eElement.getElementsByTagName("dueAmount").item(0).getTextContent();
			BigDecimal amount = new BigDecimal(amountString);
	    	billresponer.setDueAmount(amount);
	    	
	        if( eElement.getElementsByTagName("fees").getLength() > 0){
			String feesString = eElement.getElementsByTagName("fees").item(0).getTextContent();
			BigDecimal fees = new BigDecimal(feesString);
            billresponer.setFees(fees);
	        }
            billList.add(billresponer);
            
		}
		
		checkValidation(billList);
	}
                    }catch (Exception e) {
                             e.printStackTrace();
                    }
                    
                    
                   

        return billList;
    }
}
