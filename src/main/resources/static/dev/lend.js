$(document).ready(function (){
    $("#querybtn").click(function (){
        let selectus=document.getElementById("selectus").value;
        let url;
        let lendcond=$("#opt_lend")[0].value;
        if (lendcond==="all") url="/queryall";
        else url="/querybycondition";
        //alert(selectus+":"+lendcond);
        $.ajax({
            url: url,
            type: "POST",
            data: {"selectus": selectus},
            dataType: "json",
            success: function (xhr){
                alert("查询成功");
                document.getElementById("t_body2").innerHTML="";
                let i;
                let info=xhr.data;
                for(i=0;i<info.length;i++) {
                    $("<tr> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+info[i].id+"</font></font>"+
                        "</td> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+info[i].username+"</font></font>"+
                        "</td> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+info[i].bookname+"</font></font>"+
                        "</td> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+info[i].num+"</font></font>"+
                        "</td> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+info[i].lenddate+"</font></font>"+
                        "</td> <td>"+'<font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">'+info[i].backdate+"</font></font>"+
                        "</td> </tr>").appendTo($("#t_body2"));
                }
            },
            error: function (xhr){
                alert("查询失败,服务器出现错误");
            }
        })
    })
    $("#lend").click(function (){
        let username=document.getElementById("lend_username").value;
        let bookname=document.getElementById("lend_bookname").value;
        let num=document.getElementById("lend_num").value;
        $.ajax({
            url: "/lend",
            data: {"username": username, "bookname": bookname, "num": num},
            type: "POST",
            dataType: "json",
            success: function (xhr){
                if (xhr.code==0) alert("借书成功");
                else alert("借书失败:"+xhr.message);
            },
            error: function (xhr){
                alert("服务器出错"+xhr.message);//code=500
            }
        })
    })
    $("#back").click(function (){
        let username=document.getElementById("back_username").value;
        let bookname=document.getElementById("back_bookname").value;
        let num=document.getElementById("back_num").value;
        $.ajax({
            url: "/back",
            data: {"username": username, "bookname": bookname, "num": num},
            type: "POST",
            dataType: "json",
            success: function (xhr){
                if (xhr.code==0) alert("还书成功");
                else alert("还书失败:"+xhr.message);
            },
            error: function (xhr){
                alert("服务器出错"+xhr.message);//code=500
            }
        })
    })
})