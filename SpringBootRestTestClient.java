package com.example.demo;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
 
import com.example.demo.Word;
import org.springframework.web.client.RestTemplate;
  
 
public class SpringBootRestTestClient {
  
    public static final String REST_SERVICE_URI = "http://localhost:8080/DemoApplication/api";
      
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllWords(){
        System.out.println("Testing listAllWords API-----------");
          
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> wordsMap = restTemplate.getForObject(REST_SERVICE_URI+"/word/", List.class);
          
        if(wordsMap!=null){
            for(LinkedHashMap<String, Object> map : wordsMap){
                System.out.println(""
                		+ "Word : id="+map.get("id")+", Name="+map.get("name"));
            }
        }else{
            System.out.println("No word exists----------");
        }
    }
      
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllPalindromes(){
        System.out.println("Testing listAllPalindromes API-----------");
          
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> palindromesMap = restTemplate.getForObject(REST_SERVICE_URI+"/palindrome/", List.class);
          
        if(palindromesMap!=null){
            for(LinkedHashMap<String, Object> map : palindromesMap){
                System.out.println(""
                		+ "Palindrome : id="+map.get("id")+", Name="+map.get("name"));
            }
        }else{
            System.out.println("No palindrome exists----------");
        }
    }
      
    
    /* GET */
    private static void getWord(){
        System.out.println("Testing getWord API----------");
        RestTemplate restTemplate = new RestTemplate();
        Word word = restTemplate.getForObject(REST_SERVICE_URI+"/word/1", Word.class);
        System.out.println(word);
    }
      
    /* POST */
    private static void createWord() {
        System.out.println("Testing create Word API----------");
        RestTemplate restTemplate = new RestTemplate();
        Word word = new Word(0,"Sarah");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/word/", word, Word.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
  
    /* PUT */
    private static void updateWord() {
        System.out.println("Testing update Word API----------");
        RestTemplate restTemplate = new RestTemplate();
        Word word  = new Word(1,"Tomy");
        restTemplate.put(REST_SERVICE_URI+"/word/1", word);
        System.out.println(word);
    }
    
    /* DELETE */
    private static void deleteWord() {
        System.out.println("Testing delete Word API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/word/3");
    }
  
    public static void main(String args[]){
        listAllWords();
        getWord();
        createWord();
        listAllWords();
        updateWord();
        listAllWords();
        deleteWord();
        listAllWords();
        listAllPalindromes();
        listAllWords();
    }
}
