package com.madfooat.billinquiry;

public class TestConstants {

    public static final String VALID_XML_RESPONSE =
            "<?xml version=\"1.0\"?> " +
                    "<Bills> " +
                    "<bill id='1'> " +
                    "<dueDate>15-08-2017</dueDate> " +
                    "<amount>198.98</amount> " +
                    "<fees>0.139</fees> " +
                    "</bill>" +
                    "<bill id='2'>" +
                    "<dueDate>13-07-2017</dueDate>" +
                    "<amount>50.989</amount>" +
                    "</bill>" +
                    "</Bills>";

    public static final String INVALID_XML_RESPONSE =
            "<?xml version=\"1.0\"?>" +
                    "<Bills>" +
                    "   <bill id='1'>" +
                    "       <dueDate>15-08-2017</dueDate>" +
                    "       <amount>198.9889</amount>" +
                    "       <fees>0.139</fees>" +
                    "   </bill>" +
                    "</Bills>";


    public static final String VALID_JSON_RESPONSE = "{" +
            "  \"[{" +
            "\"billDueDate\": \"9-7-2017\"," +
            "\"amount\": \"21.001\"" +
            "}, {" +
            "\"billDueDate\": \"15-8-2017\"," +
            "\"amount\": \"34.001\"" +
            "}]";

    public static final String INVALID_JSON_RESPONSE = "{" +
            "  \"[{" +
            "\"billDueDate\": \"15-10-2017\"," +
            "\"amount\": \"34.0771\"" +
            "}]";
}
