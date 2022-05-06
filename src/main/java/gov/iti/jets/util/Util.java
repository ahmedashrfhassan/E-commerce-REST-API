package gov.iti.jets.util;

import java.util.Base64;

public class Util {

    // encode string base 64 encoder
    public static String encodeString(String inputString){
        return Base64.getEncoder().encodeToString(inputString.getBytes());
    }

    // decode string base 64 decoder
    public static  String decodeString(String outputString){
        byte[] decodedBytes = Base64.getDecoder().decode(outputString);
        return new String(decodedBytes);
    }
}
