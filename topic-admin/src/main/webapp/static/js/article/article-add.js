var articleAddDialog = null;
var articleSections=[]; //文章内容sections块存储,包括文字和图片
var articleSectionSize=0;//内容块数量
$(function () {
    //保存动作
    $('#save-btn').click(function () {
        saveArticle();
    });
   //取消动作
    $('#cancel-btn').click(function () {
        articleAddDialog && articleAddDialog.close();
        articleAddDialog = null;
        articleSectionSize=0;
        articleSections=[];
        //$('#contentsDiv ol ').children().remove();

        //图片、内容上传窗口关闭，恢复初始状态
        thumbUploadDialog && thumbUploadDialog.close();
        thumbUploadDialog = null;
        resetThumbContent();
    });
    //添加文章内容文本信息
    $('#textContent_upload').click(function(){
        var textCn=$('#textContent').val();
        if(isNull(textCn))
        {
            alert("添加的文章内容文本为空");
            $('#textContent').focus();
        }
        else
        {
        var section=new Object();
            section.id = articleSectionSize;
            section.type="TEXT";
            section.attr={"font-color":"#FFFFF","font-size":12};
            section.text=textCn;
            articleSections.push(section);
            //$('#articleContentsDiv ol').append('<li class="contentIndex"  id="contentIndex'+articleSectionSize+'" ><textarea style="height: auto ;width: 300px" aria-readonly="true">'+textCn+' </textarea></li>');
            $('#articleContentsDiv').append('<textarea id="contentIndex'+articleSectionSize+'" aria-readonly="true">'+textCn+' </textarea><a id="'+ articleSectionSize+'" class="content-delete" onclick="contentRemove('+articleSectionSize+')"><i class="icon-trash"></i></a>');
            articleSectionSize++;
            //清理数据代码
            textCn="";
            $('#textContent').val("");
        }

    })

   //添加文章内容图片信息
 /*   $('#image_upload').click(function(){
        var section=new Object();
        section.id = articleSectionSize;
        section.type="IMAGE";
        section.attr={width:250,height:250,altText:"ceshiceshi测试"};
        var image=new Object();
        //image.url="http://pic.sc.chinaz.com/files/pic/pic9/201509/apic15147.jpg";
        image.url=$('#imageToContent').attr("src");
        image.width=250;
        image.height=250;
        section.image=image;
        articleSections.push(section);
        $('#contentsDiv ol').append('<li class="contentIndex" id="contentIndex'+articleSectionSize+'"><img src="'+image.url+'" style="width:300px;height: 250px"> </img></li>');
        articleSectionSize++;
        //清理数据代码
    });
    */
  //删除图文
    $("#articleContentsDiv").on("click", "li", function(event) {
        var li = $(event.currentTarget);
        var id = li.attr("id");
        console.log(id);
        li.remove();
        for(var i = 0;i < articleSectionSize;i++) {
            if ( 'contentIndex'+articleSections[i].id == id) {
                return articleSections.splice(i,1);
            }
        }
    })
    //组装tags
    function getTags(){
        var tagsStr=$('#tags').val().trim();
        var arr=tagsStr.split(',');
        var result=[];
        $.each(arr,function(n,value) {
            result.push({name:value});
        });
        return result;
    }
    //组装内容
  function  getArticleContent(){
      var sect=[];
     $(articleSections).each(function(i,v){
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

    //组装articleJson
    function getarticleJson(){
        var article={
            title:$('#title').val(),
            brief:$('#brief').val(),
            tags:getTags(),
            content:getArticleContent(),
            valid:$('#validFlag').val()
        }
        return JSON.stringify(article);
    }

    /**
     * 保存信息
     * @returns {boolean}
     */
    function saveArticle() {
      //  if(!validateForm()) return false;
        showTips('系统正在处理, 请稍等...', 'info');
        var article = {
            appId: $('#appId').val(),
            folderPath: $('#folderPath').val(),
            articleJson:getarticleJson()
        }
        if (!window.confirm('确定保存文章文章?')) {
            return false;
        }
        requstMerge(article, function (res) {
            if ( res.code == 200) {
                alert("文章保存成功");
                showTips('文章保存成功.', "success");
                $("#searchData").click();
                } else {
                alert("文章保存失败");
                showTips('保存失败', "error");
            }
        });
    }

    function requstMerge(jsonData, successCallback) {
        $.ajax({
            url: '/sloth/article/save',
            type: 'post',
            data: jsonData, //
            success: function (res) {
                successCallback(res);
            },
            error: function (res) {
                showTips('服务器出错,msg:' + res.message, "error");
            }
        });
    }
})

function validateForm() {
    var appId = $("#appId").val();
    if ($.trim(appId) == "") {
        alert("appId不能为空");
        $("#appId").focus();
        return false;
    }
    var folderPath = $("#folderPath").val();
    if ($.trim(folderPath) == "") {
        alert("目录路径不能为空");
        $("#folderPath").focus();
        return false;
    }
    return true;
}
//上传至Thumbs图片
    $("#imageToThumbs_upload").uploadify({
        fileObjName: 'file',
        height: 25,
        swf: '/lib/uploadify/uploadify.swf',
        uploader: '/sloth/fsbs2',
        buttonText: '上传Thumbs图片',
        multi: false,
        fileSizeLimit: '4MB',
        fileTypeDesc: 'Image Files',
        fileTypeExts: '*.jpg; *.jpng; *.png;',
        onUploadSuccess: function (file, data, response) {
            data = $.parseJSON(data);
            if (data.code == 200) {
                if (data.data.filePath) {
                    $('#imageToContent').attr("src", data.data.filePath);

                } else {
                    alert("获取图片路径失败,请重试");
                }
            } else {
                window.alert(data.message);
            }
        }
    });
    function showTips(tips, type) {
        $(".alert").alert('close');
        $('#add-dialog').append('<div id="add-alert-' + type + '" class="alert alert-' + type + '">' +
            '<button class="close" data-dismiss="alert"></button><strong>' + tips + '</strong></div>');
    }
//icon图标上传

$("#imageToContent_upload").uploadify({
    fileObjName: 'file',
    height: 25,
    swf: '/lib/uploadify/uploadify.swf',
    uploader: '/sloth/fsbs2',
    buttonText: '上传内容-图片',
    multi: false,
    fileSizeLimit: '4MB',
    fileTypeDesc: 'Image Files',
    fileTypeExts: '*.jpg; *.jpng; *.png;',
    onUploadSuccess: function (file, data, response) {
        data = $.parseJSON(data);
        if (data.code == 200) {
            if(data.filePath){
                $('#imageToContent').attr("src", data.filePath);
                var section=new Object();
                section.id = articleSectionSize;
                section.type="IMAGE";
                section.attr={width:250,height:250,altText:"ceshiceshi测试"};
                var image=new Object();
                //image.url="http://pic.sc.chinaz.com/files/pic/pic9/201509/apic15147.jpg";
                image.url=data.filePath;
                image.width=250;
                image.height=250;
                section.image=image;
                articleSections.push(section);
                //$('#articleContentsDiv ol').append('<li class="contentIndex" id="contentIndex'+articleSectionSize+'"><img src="'+image.url+'" style="width:300px;height: 250px"> </img></li>');
                $('#articleContentsDiv').append('<img id="contentIndex'+articleSectionSize+'" src="'+image.url+'" style="width:220px;height: 200px"> </img><a id="'+ articleSectionSize+'" class="content-delete" onclick="contentRemove('+articleSectionSize+')"><i class="icon-trash"></i></a>');
                articleSectionSize++;
                //清理数据代码
            }else{
                alert("获取图片路径失败,请重试");
            }
        } else {
            window.alert(data.message);
        }
    }
});