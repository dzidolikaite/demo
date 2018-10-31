package com.example.demo;

import java.util.List;
 
import com.example.demo.Word;
 
public interface WordService {
     
    Word findById(long id);
     
    Word findByName(String name);
     
    void saveWord(Word word);
     
    void updateWord(Word word);
     
    void deleteWordrById(long id);
 
    List<Word> findAllWords();
    
    List<Word> findAllPalindromes();
     
    boolean isWordExist(Word word);
     
}
     
