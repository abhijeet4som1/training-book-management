package com.training.bookmanagement.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksDao;

    public Iterable<Books> getAllBooks(){
        return booksDao.findAll();
    }

    @Transactional
    public Books saveBooks(Books books){
        return booksDao.save(books);
    }
}
