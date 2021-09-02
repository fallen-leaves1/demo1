package com.example.demo.service;

import com.example.demo.basic.Result;
import com.example.demo.repository.dao.Book;
import com.example.demo.request.Bookreq;

import java.util.List;
import java.util.Map;

public interface BookService {

    Result addBook(Bookreq bookreq);
    Result delBook(String bookname);
    Result updateBook(Bookreq bookreq);
    Result selectBookByid(int id);
    Result selectBooks();
    Result addBooks(List<Book> bookList);
    Result selectBookByname(String bookname);
    Result selectBookByAll(Map map);
}
