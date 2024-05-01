package edu.canisius.csc213.project2.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.canisius.csc213.project2.quotes.StockQuote;

import java.io.IOException;

public class PolygonJsonReplyTranslator {

    public StockQuote translateJsonToFinancialInstrument(String json) throws IOException {
      
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(json);

  
        JsonNode resultsNode = rootNode.path("results");
        if (resultsNode.isArray() && resultsNode.size() > 0) {
            JsonNode quoteNode = resultsNode.get(0);
            String symbol = rootNode.path("ticker").asText();
            double closePrice = quoteNode.path("c").asDouble(); 
            double highestPrice = quoteNode.path("h").asDouble(); 
            double lowestPrice = quoteNode.path("l").asDouble();
            int numberOfTransactions = quoteNode.path("n").asInt(); 
            double openPrice = quoteNode.path("o").asDouble(); 
            long timestamp = quoteNode.path("t").asLong(); 
            double tradingVolume = quoteNode.path("v").asDouble(); 

          
            return new StockQuote(symbol, closePrice, highestPrice, lowestPrice, numberOfTransactions,
                    openPrice, timestamp, tradingVolume);
        } else {
            throw new IOException("No results found in JSON response.");
        }
    }
}