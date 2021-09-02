package com.example.demo.repository.mapper;

import com.example.demo.repository.dao.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookMapper {
    Book selectBookById(int id);
    int addBook(@Param("book") Book book);//传递对象需要注解@Param
    int deleteBook(String bookname);
    int updateBook(@Param("book") Book book);
    List<Book> selectBooks();
    Book selectBookByname(String bookname);
    List<Book> selectBookByAll(Map map);
}
