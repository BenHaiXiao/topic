
var postSections=[]; //评论内容sections块存储,包括文字和图片
var postSectionSize=0;//内容块数量
$(function () {
    //渲染组件
    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        language: "zh-CN",
        todayBtn: true,
        autoclose: true,
        clearBtn: true,
        pickerPosition: "bottom-left"
    });

    //保存动作
    $('#post-save').click(function () {
        save();
    });

    $('#post-cancel').click(function () {
        postAddDialog && postAddDialog.close();
        postAddDialog = null;
    });
    //添加文章内容文本信息
    $('#postTextContent_upload').click(function(){
        var textCn=$('#postTextContent').val();
        if(isNull(textCn))
        {
            alert("添加的文章内容文本为空");
            $('#postTextContent').focus();
        }
        else
        {
            var section=new Object();
            section.id = postSectionSize;
            section.type="TEXT";
            section.attr={"font-color":"#FFFFF","font-size":12};
            section.text=textCn;
            postSections.push(section);
            $('#postContentsDiv ol').append('<li class="contentIndex"  id="contentIndex'+postSectionSize+'" ><textarea style="height: auto ;width: 300px" aria-readonly="true">'+textCn+' </textarea></li>');
            postSectionSize++;
            //清理数据代码
            textCn="";
            $('#postTextContent').val("");
        }
    })
});
//删除图文
$("#postContentsDiv").on("click", "li", function(event) {
    var li = $(event.currentTarget);
    var id = li.attr("id");
    console.log(id)
    li.remove();
    for(var i = 0;i <  postSectionSize;i++) {
        if (  'contentIndex'+postSections[i].id == id) {
            return postSections.splice(i,1);
        }
    }
})
function validateForm() {
    var targetId = $("#targetId").val();
    if ($.trim(targetId) == "") {
        alert("评论对象ID不能为空");
        $("#targetId").focus();
        return false;
    }
    var appid = $('#appid').val();
    if ($.trim(appid) == "") {
        alert("appid不能为空");
        $("#appid").focus();
        return false;
    }
    var folderPath = $('#folderPath').val();
    if ($.trim(folderPath) == "") {
        alert("路径不能为空");
        $("#folderPath").focus();
        return false;
    }
    var userId = $('#userId').val();
    if ($.trim(userId) == "") {
        alert("评论者不能为空");
        $("#search-sysuser").focus();
        return false;
    }

    var content = $("#content").val();
    if ($.trim(content) == "") {
        alert("评论内容不能为空");
        $("#content").focus();
        return false;
    }else if(content.length>500){
        alert("评论内容字数："+content.length+" 超过500字");
        return false;
    }
    return true;
}
function getContents(){
    var sect=[];
    $(postSections).each(function(i,v){
        var temp=new Object();
        temp.type= v.type;
        temp.attr= v.attr;
        switch(temp.type)
        {
            case "IMAGE":
            {
                var imageData= v.image;
                var image= new Object();
                image.url= imageData.url;
                image.width=imageData.width;
                image.height=imageData.height;
                image.altText=imageData.altText;
                temp.image=image;
            }
                break;
            case "TEXT":
                temp.text= v.text;
                break;
        }
        sect.push(temp);
    })
    var result=new Object();
    result.size=sect.length;
    result.sections=sect;
    return result;

}
function getPostJson(){
     var postJson={

         content: getContents()
     }
    return JSON.stringify(postJson);
}
/**
 * 保存小组信息
 * @returns {boolean}
 */
function save() {
    //var result = validateForm();
  //  if(!result) return false;
   // showTips('系统正在处理, 请稍等...','info');
    var post = {
        appId: $('#appid').val(),
        articleId: $.trim($('#targetId').val()),
        ticket:$('#userId').val(),
        postJson:getPostJson()
       };
    if (!window.confirm('是否确定保存评论?')) {
        return false;
    }
    requstMerge(post);

}

function requstMerge(jsonData) {
    $.ajax({
        url: '/sloth/post/save',
        type: 'post',
      //  dataType: 'json',
        data: jsonData,
        success: function (json) {
            var code = json.code;
            if(code==200 ){
                alert('操作成功');
                $("#searchData").click();
            }else{
                alert('操作失败，请联系管理员');
            }
        },
        error: function () {
            alert("服务器忙，请稍后再试");
        }
    });
}
$(function () {
    function showTips(tips, type) {
        $(".alert").alert('close');
        $('#folder-add-dialog').append('<div id="add-alert-' + type + '" class="alert alert-' + type + '">' +
            '<button class="close" data-dismiss="alert"></button><strong>' + tips + '</strong></div>');
    }

    $("#postImageToContent_upload").uploadify({
        fileObjName: 'file',
        height: 25,
        swf: '/lib/uploadify/uploadify.swf',
        uploader: '/sloth/fsbs2',
        buttonText: '上传内容图片',
        multi: false,
        fileSizeLimit: '4MB',
        fileTypeDesc: 'Image Files',
        fileTypeExts: '*.jpg; *.jpng; *.png;',
        onUploadSuccess: function (file, data, response) {
            data = $.parseJSON(data);
            if (data.code == 200) {
                if (data.filePath) {
                    $('#postTextContent').attr("src", data.filePath);
                    var section = new Object();
                    section.id = postSectionSize;
                    section.type = "IMAGE";
                    section.attr = {width: 250, height: 250, altText: "ceshiceshi测试"};
                    var image = new Object();
                    //image.url="http://pic.sc.chinaz.com/files/pic/pic9/201509/apic15147.jpg";
                    image.url = data.filePath;
                    image.width = 250;
                    image.height = 250;
                    section.image = image;
                    postSections.push(section);
                    $('#postContentsDiv ol').append('<li class="contentIndex" id="contentIndex' + postSectionSize + '"><img src="' + image.url + '" style="width:300px;height: 250px"> </img></li>');
                    postSectionSize++;
                    //清理数据代码
                } else {
                    alert("获取图片路径失败,请重试");
                }
            } else {
                window.alert(data.message);
            }
        }
    });
})