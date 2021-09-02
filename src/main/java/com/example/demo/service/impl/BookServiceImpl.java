package com.example.demo.service.impl;

import com.example.demo.basic.Result;
import com.example.demo.repository.dao.Book;
import com.example.demo.repository.mapper.BookMapper;
import com.example.demo.repository.mapper.LendMapper;
import com.example.demo.request.Bookreq;
import com.example.demo.service.BookService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.basic.Errors.*;

@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
public class BookServiceImpl implements BookService {

    @Resource
    BookMapper bookMapper;
    @Resource
    LendMapper lendMapper;

    //@CachePut(value = "books", key = "#book.bookname")//value为缓存名字(缓存是一个map),key为缓存map的key且是方法传递的参数
    @Override
    public Result addBook(Bookreq bookreq) {
        if (bookreq.hasIllegalField()) return Result.fail(INVALID_PARAMS);
        if (bookMapper.selectBookByname(bookreq.getBookname()) != null) return Result.fail(REPEAT_DATA);
        Book book=new Book();
        book.setBookname(bookreq.getBookname());
        book.setAuthor(bookreq.getAuthor());
        book.setNum(bookreq.getNum());
        int code=bookMapper.addBook(book);//返回改变的行数
        return code == 1 ? Result.OK : Result.fail(SERVER_ERROR);
    }

    //@CacheEvict(value = "books", key = "#bookname")
    @Override
    public Result delBook(String bookname) {
        Book book=null;
        book=bookMapper.selectBookByname(bookname);
        if(book==null) {
            return Result.fail(RECORD_NOT_EXISTS);
        } else if (lendMapper.lend_exist(bookname)>0){
            return Result.fail(SPECIAL_ERROR);
        }else {
            int code=bookMapper.deleteBook(bookname);
            return code == 1 ? Result.OK : Result.fail(SERVER_ERROR);
        }
    }

    //@CachePut(value = "books", key = "#book.bookname")
    @Override
    public Result updateBook(Bookreq bookreq) throws RuntimeException{
        int code=0;
        Book record_book=null;
        if (bookreq.hasIllegalField()) return Result.fail(INVALID_PARAMS);
        record_book=bookMapper.selectBookByname(bookreq.getBookname());
        if (record_book == null) return Result.fail(RECORD_NOT_EXISTS);
        record_book.setAuthor(bookreq.getAuthor());
        record_book.setNum(bookreq.getNum());
        System.out.println("修改后的图书:"+record_book);
        code=bookMapper.updateBook(record_book);//返回影响的行数
        return code == 1 ? Result.OK : Result.fail(SERVER_ERROR);
    }

    //@Cacheable(value = "books",key = "#id")
    @Override
    public Result selectBookByid(int id) {
        Book book=bookMapper.selectBookById(id);
        System.out.println(book);
        return Result.ok(book);
    }

    //@Cacheable(value = "books")//缓存未实现
    @Override
    public Result selectBooks() {
        List<Book> list=bookMapper.selectBooks();
        for (Book book:list) {
            System.out.println(book);
        }
        return Result.ok(list);
    }

    //@CachePut(value = "books")
    @Override
    public Result addBooks(List<Book> bookList) {
        int code=0;
        for (Book book: bookList) {
            try{
                if (bookMapper.selectBookById(book.getId()) != null) {
                    throw new RuntimeException("记录存在");
                }
                else {
                    code+=bookMapper.addBook(book);//累加改变的行数
                }
            }catch (RuntimeException exception){
                exception.printStackTrace();
                return Result.fail(INVALID_PARAMS);
            }
        }
        return code >= 1 ? Result.OK : Result.fail(SERVER_ERROR);
    }

    //@Cacheable(value = "books", key = "#bookname")
    @Override
    public Result selectBookByname(String bookname) {
        Book book=bookMapper.selectBookByname(bookname);
        System.out.println(book);
        return Result.ok(book);
    }

    //@Cacheable(value = "books")
    @Override
    public Result selectBookByAll(Map map) {
        List<Book> books=new ArrayList<>();
        books=bookMapper.selectBookByAll(map);
        System.out.println(books);
        return Result.ok(books);
    }
}
