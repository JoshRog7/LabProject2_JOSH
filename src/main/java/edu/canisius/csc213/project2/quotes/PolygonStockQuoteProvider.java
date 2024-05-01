package edu.canisius.csc213.project2.quotes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import edu.canisius.csc213.project2.util.*;

public class PolygonStockQuoteProvider implements StockQuoteProvider{

    public String getEndpointUrl(String symbolName, String date, String apiKey) {
        try {
            
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            
            throw new IllegalArgumentException("Invalid date format. Date should be in the format 'yyyy-MM-dd'.");
        }

        
        return "https://api.polygon.io/v2/aggs/ticker/" + symbolName +
                "/range/1/day/" + date + "/" + date + "?apiKey=" + apiKey;
    }
   @Override
    public StockQuote getStockQuote(String stockQuoteEndpoint) throws IOException {
        String json = sendGetRequest(stockQuoteEndpoint);
        PolygonJsonReplyTranslator jft = new PolygonJsonReplyTranslator();
        return jft.translateJsonToFinancialInstrument(json);

    }

    public static String sendGetRequest(String endpointUrl) {
        try {
            // Create URL object
            URL url = new URL(endpointUrl);
    
            // Open connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
    
            con.setRequestMethod("GET");
    
    
            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
    
           
            con.disconnect();
    
       
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
