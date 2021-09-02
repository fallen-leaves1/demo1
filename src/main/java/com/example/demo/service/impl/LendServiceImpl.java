package com.example.demo.service.impl;

import com.example.demo.basic.Result;
import com.example.demo.repository.dao.Book;
import com.example.demo.repository.dao.UserLend;
import com.example.demo.repository.mapper.BookMapper;
import com.example.demo.repository.mapper.LendMapper;
import com.example.demo.request.Lendreq;
import com.example.demo.service.LendService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import static com.example.demo.basic.Errors.*;

@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
public class LendServiceImpl implements LendService {

    @Resource
    BookMapper bookMapper;
    @Resource
    LendMapper lendMapper;

    @Override
    public Result lend_book(Lendreq lendreq){
        Date lenddate=new Date();
        int code=0;
        Book book=null;
        //不赋值的情况下属性为null
        if (lendreq.hasIllegalField()) {
            System.out.println("传入参数不合法"+"num="+lendreq.getNum()+",bookname="+lendreq.getBookname());
            return Result.fail(INVALID_PARAMS);
        }
        book=bookMapper.selectBookByname(lendreq.getBookname());
        try {
            if (book == null) return Result.fail(RECORD_NOT_EXISTS);
            if (book.getNum()==0||book.getNum()-lendreq.getNum()<0) return Result.fail(RECORD_LIMITED);
            bookMapper.updateBook(new Book(book.getId(),book.getBookname(),book.getAuthor(),book.getNum()-lendreq.getNum()));
            System.out.println("出库成功");
            UserLend userLend=new UserLend(0,lendreq.getUsername(),lendreq.getBookname(),lendreq.getNum(),lenddate,null);
            code=lendMapper.lendbook(userLend);
        }catch (RuntimeException exception){
            //throw new RuntimeException(exception);//不能使用catch捕获异常在抛出异常，这样不能抛给代理类无法进行事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动设置事务回滚
        }
        return code == 1 ? Result.OK : Result.fail(SERVER_ERROR);
    }

    @Override
    public Result back_book(Lendreq lendreq) {
        if (lendreq.hasIllegalField()) {
            System.out.println("传入参数不合法"+"num="+lendreq.getNum()+",bookname="+lendreq.getBookname());
            return Result.fail(INVALID_PARAMS);
        }
        int code=0;
        Date backdate=new Date();
        UserLend userLend=null;
        List<UserLend> list=lendMapper.lendinfo(lendreq.getUsername());
        for (UserLend user:list) {
            if (user.getUsername().equals(lendreq.getUsername())
                    && user.getBookname().equals(lendreq.getBookname()) && user.getNum()>=lendreq.getNum()) {
                userLend=new UserLend(user.getId(),user.getUsername(),
                        user.getBookname(),user.getNum()-lendreq.getNum(),null,backdate);
            }
        }
        try {
            if (userLend==null) return Result.fail(RECORD_NOT_EXISTS);
            code+=lendMapper.backbook(userLend);
            System.out.println("归还成功");
            Book book=bookMapper.selectBookByname(userLend.getBookname());
            book.setNum(book.getNum()+lendreq.getNum());
            code+=bookMapper.updateBook(book);
        }catch (RuntimeException exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动设置事务回滚
        }
        return code==2 ? Result.OK:Result.fail(SERVER_ERROR);
    }

    //@Cacheable(value = "lends")
    @Override
    public Result lend_info(String username) {
        System.out.println("username:"+username);
        List<UserLend> list=lendMapper.lendinfo(username);
        for (UserLend userlend:list) {
            System.out.println(userlend);
        }
        return Result.ok(list);
    }
}
