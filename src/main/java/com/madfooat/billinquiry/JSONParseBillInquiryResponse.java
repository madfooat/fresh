package com.madfooat.billinquiry;

import com.madfooat.billinquiry.domain.Bill;
import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;

import java.util.List;

public class JSONParseBillInquiryResponse implements ParseBillInquiryResponse {
	    
     private static String reversDateFormate(String date){
        
        
        
        String day,monthe,year;
        day = date.substring(0,2);
        monthe = date.substring(3,5);
        year= date.substring(6);
     
        date = year+'-'+monthe+'-'+day;
      
        return date;
        
    }
    
    private static  String fixDateFormat(String Response){
        
        
       for (int i = -1; (i = Response.indexOf("dueDate", i + 1)) != -1; i++) {
               
             partOfDate =  Response.substring(i+11,i+21);
             partOfDateRevrs =  reversDateFormate(partOfDate);
             Response = Response.replace(partOfDate,partOfDateRevrs);
             
             
             } 
      
        return Response;
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
		
        billerResponse = fixDateFormat(billerResponse);
        Bill[] billerResponseList = new Gson().fromJson(billerResponse,Bill[].class);
        List<Bill> billList = Arrays.asList(billerResponseList);
        checkValidation(billList);
        return billList;
    }
}
