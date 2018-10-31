package com.example.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
 
import org.springframework.stereotype.Service;
 
import com.example.demo.Word;
  
@Service("wordService")
public class WordServiceImpl implements WordService{
     
    private static final AtomicLong counter = new AtomicLong();
     
    private static List<Word> words;
    
    private static List<Word> words3;
    
    static{
        words = populateDummyUsers();
    }
 
    public List<Word> findAllWords() {
        return words;
    }
    
    public List<Word> findAllPalindromes() {
    	
    	for(Word word : words){
    		StringBuilder word2 = new StringBuilder();
    		word2.append(word);
            if(word.getName().equals(word2.reverse())){
            	words3.add(word);
            }
            word2.setLength(0);
        }

    	return words3;
    }
     
    /*public User findById(long id) {
        for(User user : users){
         if(user.getId() == id){
                return user;
            }
        }
        return null;
    }*/
     
    public Word findByName(String name) {
        for(Word word : words){
            if(word.getName().equalsIgnoreCase(name)){
                return word;
            }
        }
        return null;
    }
     
    public void saveWord(Word word) {
        word.setId(counter.incrementAndGet());
        words.add(word);
    }
 
    public void updateWord(Word word) {
        int index = words.indexOf(word);
        words.set(index, word);
    }
 
    public void deleteWordById(long id) {
    	for (Iterator<Word> iterator = words.iterator(); iterator.hasNext(); ) {
            Word word= iterator.next();
            if (word.getId() == id) {
                iterator.remove();
            }
        }
    }
 
    public boolean isWordExist(Word word) {
        return findByName(word.getName())!=null;
    }
     
    //public void deleteAllUsers(){
    //    users.clear();
   // }
 
    private static List<Word> populateDummyUsers(){
        List<Word> words = new ArrayList<Word>();
        words.add(new Word(counter.incrementAndGet(),"Sam"));
        words.add(new Word(counter.incrementAndGet(),"Tom"));
        words.add(new Word(counter.incrementAndGet(),"Jerome"));
        words.add(new Word(counter.incrementAndGet(),"Silvia"));
        return words;
    }
 
}
