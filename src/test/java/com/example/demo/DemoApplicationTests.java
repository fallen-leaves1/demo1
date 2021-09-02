package com.example.demo;

import com.example.demo.basic.Result;
import com.example.demo.repository.dao.Book;
import com.example.demo.repository.dao.User;
import com.example.demo.repository.dao.UserLend;
import com.example.demo.repository.mapper.LendMapper;
import com.example.demo.request.Bookreq;
import com.example.demo.request.Lendreq;
import com.example.demo.service.BookService;
import com.example.demo.service.LendService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@MapperScan("com.example.demo.repository.mapper")
@SpringBootTest
@EnableCaching
@EnableTransactionManagement
class DemoApplicationTests {

    @Resource
    private BookService bookService;
    @Resource
    private LendService lendService;
    @Resource
    private UserService userService;

    //通过id查询单本图书
    @Test
    void contextLoad1() {
        Result result=bookService.selectBookByid(1);
        bookService.selectBookByid(1);//第二次没有打印图书信息证明查询了缓存而不是数据库
        System.out.println(result);
    }

    //增加单本图书
    @Test
    void contextLoad2() {
        Bookreq book=new Bookreq();
        book.setBookname("ccc");
        book.setAuthor("王五");
        book.setNum(11);
        Result result=bookService.addBook(book);
        bookService.selectBooks();
        bookService.selectBookByid(3);
        System.out.println(result);
    }

    //修改单本图书
    @Test
    void contextLoad3() {
        Bookreq book=new Bookreq();
        book.setBookname("ccc");
        book.setAuthor("王五");
        book.setNum(11);
        Result result=bookService.updateBook(book);
        bookService.selectBookByname("ccc");
        System.out.println(result);
    }

    //通过id删除单本图书
    @Test
    void contextLoad4() {
        bookService.selectBookByid(3);
        Result result=bookService.delBook("ccc");
        System.out.println(result);
        bookService.selectBookByid(3);
    }

    //查询全部图书
    @Test
    void contextLoad5() {
        Result result=bookService.selectBooks();
        bookService.selectBooks();//查询缓存
        bookService.selectBookByid(1);//没有查询缓存
        System.out.println(result);
    }

    //添加多本图书
    @Test
    void contextLoad6() {
        List<Book> bookList=new ArrayList<Book>();
        bookList.add(new Book(1,"aaa","张三",10));
        bookList.add(new Book(2,"bbb","李四",10));
        Result result=bookService.addBooks(bookList);
        bookService.selectBooks();//没有查询缓存
        System.out.println(result);
    }

    //查询租借情况
    @Test
    void contextLoad7() {
        Result result=lendService.lend_info(null);
        System.out.println(result);
    }

    //单人借一本书
    @Test
    void contextLoad8() {
        Date lenddate=new Date();
        System.out.println("借书时间："+lenddate);
        Lendreq lendreq=new Lendreq();
        lendreq.setUsername("wsb");
        lendreq.setBookname("bbb");
        lendreq.setNum(1);
        Result result=lendService.lend_book(lendreq);
        System.out.println(result);
    }

    //单人还一本书
    @Test
    void contextLoad9() {
        Lendreq lendreq=new Lendreq();
        lendreq.setUsername("wsb");
        Result result=lendService.back_book(lendreq);
        System.out.println(result);
    }

    //登录功能
    @Test
    void contextLoad10() {
        User user=new User("wsb","123a");
        Result result=userService.login(user);
        System.out.println(result);
    }

    @Test
    void test01(){
        String bookname="ccc";
        Result result=bookService.selectBookByname(bookname);
        System.out.println("查询的图书为:"+result.getData());
    }

    @Test
    void test02(){
        Map<String,Object> map=new HashMap<>();
        map.put("author","李四");
        Result result=bookService.selectBookByAll(map);
        System.out.println("查询到的图书为:"+result.getData());
    }

    @Test
    void test03() {
        Lendreq lendreq=new Lendreq();
        System.out.println(StringUtils.hasLength(lendreq.getBookname()));
    }

    @Test
    void test04(){
        String bookname="bbb";
        Result result=bookService.delBook(bookname);
        System.out.println(result);
    }
}
