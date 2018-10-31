package com.example.demo;

import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
 
import com.example.demo.Word;
import com.example.demo.WordService;
import com.example.demo.CustomError;
 
@RestController
@RequestMapping("/api")
public class RestApiController {
 
    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
 
    @Autowired
    WordService wordService; //Service which will do all data retrieval/manipulation work
 
    // -------------------Retrieve All Words---------------------------------------------
 
    @RequestMapping(value = "/word/", method = RequestMethod.GET)
    public ResponseEntity<List<Word>> listAllWords() {
        List<Word> words = wordService.findAllWords();
        if (words.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Word>>(words, HttpStatus.OK);
    }
    
 // -------------------Retrieve All Palindromes---------------------------------------------
    
    @RequestMapping(value = "/palindrome/", method = RequestMethod.GET)
    public ResponseEntity<List<Word>> listAllPalindromes() {
        List<Word> words = wordService.findAllPalindromes();
        if (words.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Word>>(words, HttpStatus.OK);
    }
 
    // -------------------Retrieve Single Word------------------------------------------
 
    @RequestMapping(value = "/word/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching Word with id {}", id);
        Word word = wordService.findById(id);
        if (word == null) {
            logger.error("Word with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Word with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Word>(word, HttpStatus.OK);
    }
 
    // -------------------Create a Word-------------------------------------------
 
    @RequestMapping(value = "/word/", method = RequestMethod.POST)
    public ResponseEntity<?> createWord(@RequestBody Word word, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
 
        if (wordService.isWordExist(word)) {
            logger.error("Unable to create. A word with name {} already exist", word.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Word with name " + 
            word.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        wordService.saveWord(word);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/word/{id}").buildAndExpand(word.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Update a word ------------------------------------------------
 
    @RequestMapping(value = "/word/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateWord(@PathVariable("id") long id, @RequestBody Word word) {
        logger.info("Updating Word with id {}", id);
 
        Word currentWord = wordService.findById(id);
 
        if (currentWord == null) {
            logger.error("Unable to update. Word with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate.Word with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
 
        currentWord.setWord(word.getWord());
 
        wordService.updateWord(currentWord);
        return new ResponseEntity<Word>(currentWord, HttpStatus.OK);
    }
 
    // ------------------- Delete a Word-----------------------------------------
 
    @RequestMapping(value = "/word/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWord(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Word with id {}", id);
 
        Word word = wordService.findById(id);
        if (word == null) {
            logger.error("Unable to delete. Word with id {} not found.", id);
            return new ResponseEntity(new CustomError("Unable to delete. Word with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        wordService.deleteWordById(id);
        return new ResponseEntity<Word>(HttpStatus.NO_CONTENT);
    }
    }




