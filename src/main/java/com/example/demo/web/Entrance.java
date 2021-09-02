package com.example.demo.web;

import com.example.demo.basic.Result;
import com.example.demo.repository.dao.Book;
import com.example.demo.request.Bookreq;
import com.example.demo.request.Lendreq;
import com.example.demo.service.BookService;
import com.example.demo.service.LendService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Entrance {

    @Resource
    BookService bookService;
    @Resource
    LendService lendService;

    //通过id查询单本图书,不适用
    @PostMapping("/select")
    public Result select(@RequestParam("id") Integer int_id) {
        return bookService.selectBookByid(int_id);
    }

    //通过条件查询图书
    @PostMapping("/selectbycondition")
    public Result seletcbycond(@RequestParam("selectval") String value, @RequestParam("condition") String cond){
        Map<String,Object> map=new HashMap<>();
        if (cond.contains("author")) {
            map.put("author",value);
        }else {
            map.put("bookname",value);
        }
        Result result=bookService.selectBookByAll(map);
        return result;
    }

    //查询全部图书
    @PostMapping("/selectall")
    public Result selectall() {
        return bookService.selectBooks();
    }

    //增加单本图书
    @PostMapping("/addbook")
    public Result addbook(Bookreq bookreq) {
        System.out.println("addbook");
        System.out.println(bookreq);
        return bookService.addBook(bookreq);
    }

    //修改单本图书
    @PostMapping("/update")
    public Result update(Bookreq bookreq) {
        System.out.println("updatebook");
        System.out.println(bookreq);
        return bookService.updateBook(bookreq);
    }

    //通过书名删除单本图书
    @PostMapping("/delete")
    public Result delete(String bookname) {
        System.out.println("delbook");
        return bookService.delBook(bookname);
    }

    //单人借一本书
    @PostMapping("/lend")
    public Result lendbook(Lendreq lendreq) {
        return lendService.lend_book(lendreq);
    }

    //单人还一本书
    @PostMapping("/back")
    public Result backbook(Lendreq lendreq) {
        return lendService.back_book(lendreq);
    }

    //查询租借情况
    @PostMapping("/querybycondition")
    public Result lendInfo(@RequestParam("selectus")String selectus) {//即使传递无参字符串也!=null而是=“”
        System.out.println(selectus);
        return StringUtils.hasLength(selectus) ? lendService.lend_info(selectus) : lendService.lend_info(null);
    }

    //查询租借情况
    @PostMapping("/queryall")
    public Result lendAll() {
        return lendService.lend_info(null);
    }

}
