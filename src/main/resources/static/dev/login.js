$(document).ready(function (){
    $("#btn").click(function (){
        let username=$("#u").val();
        let password=$("#p").val();
        $.ajax({
            url:"/login",
            type:"POST",
            data:{"username": username,"password": password},
            dataType: "json",
            success: function (xhr){
                if (xhr.code==0) {
                    alert("登录成功"+",hello:"+xhr.data);
                    window.location.href="index.html";
                }
                if (xhr.code!=0) alert("code="+xhr.code+"登录失败,传入参数不合法");
            },
            error: function (xhr) {
                alert("你还未登录,请重新登录");
            }
        })
    })
})