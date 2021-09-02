$(document).ready(function (){
    $("#btn").click(function (){
        $.ajax({
            url:"/select",
            type:"POST",
            data:{},
            dataType:"json",
            success:function (xhr){
                $(".book_id").html(xhr.data.id);
                $(".book_name").html(xhr.data.bookname);
                $(".book_author").html(xhr.data.author);
                $(".book_num").html(xhr.data.num);
            }
        })
    })
    $("#selectbtn").click(function (){
        let selectvalue=document.getElementById("selectbk").value;
        let url="/selectall";
        let cond=$("#opt")[0].value;
        if (cond==="all") url="/selectall";//获取select标签的值
        else url="/selectbycondition";
        //alert(selectvalue+""+cond);
        $.ajax({
            url: url,
            type: "POST",
            data: {"selectval": selectvalue, "condition": cond},
            dataType: "json",
            success:function (xhr){
                document.getElementById("t_body").innerHTML="";//清空原数据
                let book=xhr.data;
                let i;
                //alert(book.length);
                for(i=0;i<book.length;i++) {
                    $("<tr> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+book[i].id+"</font></font>"+
                        "</td> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+book[i].bookname+"</font></font>"+
                        "</td> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+book[i].author+"</font></font>"+
                        "</td> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+book[i].num+"</font></font>"+
                        "</td> </tr>").appendTo($("#t_body"));
                }
            }
        })
    })
    $("#sub").click(function (){
        let bookname=document.getElementById("new_bookname").value;
        let author=document.getElementById("new_author").value;
        let num=document.getElementById("new_num").value;
        $.ajax({
            url: "/addbook",
            type: "POST",
            data: {"num": num, "bookname": bookname, "author":author},
            dataType: "json",
            success: function (xhr){
                if (xhr.code==0) {
                    alert("书籍添加成功");
                }
                else {
                    alert("书籍添加失败:"+xhr.message);
                }
            }
        })
    })
    $("#up").click(function (){
        let num=document.getElementById("up_num").value;
        let bookname=document.getElementById("up_bookname").value;
        let author=document.getElementById("up_author").value;
        $.ajax({
            url: "/update",
            type: "POST",
            data: {"num": num, "bookname": bookname, "author": author},
            dataType: "json",
            success: function (xhr) {
                if (xhr.code==0) {
                    alert("书籍修改成功");
                }
                else {
                    alert("书籍修改失败:"+xhr.message);
                }
            }
        })
    })
    $("#del").click(function (){
        let bookname=document.getElementById("del_bookname").value;
        $.ajax({
            url: "/delete",
            type: "POST",
            data: {"bookname": bookname},
            dataType: "json",
            success: function (xhr) {
                if (xhr.code==0) {
                    alert("书籍删除成功");
                }
                else {
                    alert("书籍删除失败:"+xhr.message);
                }
            }
        })
    })
})