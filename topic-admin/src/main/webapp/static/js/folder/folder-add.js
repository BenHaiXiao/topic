var folderAddDialog=null;
var folderThumbsSections=[];
var folderThumbsSectionsSize=0;
//保存动作
$('#folder-save-btn').click(function () {
    saveFolder();
});
$('#folder-cancel-btn').click(function () {
    folderAddDialog && folderAddDialog.close();
    folderAddDialog = null;
    //清除Thumbs内容值
    folderThumbsSections=[];
    folderThumbsSectionsSize=0
});
//thumbs图片上传
/*
$('#thumbs_upload_btn').click(function(){
    var url=$('#thumbs').attr('src');
    var width=250;
    var height=250;
    var altText="测试测试测试测试测试测试";
    var Imagesection=new Object();
    Imagesection.id = folderThumbsSectionsSize;
    Imagesection.url=url;
    Imagesection.width=width;
    Imagesection.height=height;
    Imagesection.altText=altText;
    folderThumbsSections.push(Imagesection);
    $('#folderContentsDiv ol').append('<li class="folderContentIndex" id="folderContentIndex'+folderThumbsSectionsSize+'"><img src="'+url.toString()+'" style="width:250px;height: 250px"> </img></li>');
    folderThumbsSectionsSize++;
})
*/
//此处没修改完
$("#folderContentsDiv").on("click", "i", function(event) {
    var li = $(event.currentTarget);
    var id = li.attr("id");
    console.log(id);
    $('#folderContent'+id).remove();
    li.remove();
    for(var i = 0;i < folderThumbsSections.length;i++) {
        if (  'folderContentIndex'+folderThumbsSections[i].id == 'folderContent'+id) {
            return folderThumbsSections.splice(i,1);
        }
    }
})

//获取图片信息
function getThumbs(){
    var result=new Object();
    var images=[];
    $.each(folderThumbsSections,function(i,v){
        var image=new Object();
        image.url=v.url;
        image.width=v.width;
        image.height=v.height;
        image.altText=v.altText;
        images.push(image);
    })

    result.size=folderThumbsSections.length;
    result.images=images;
    return result;
}
//获取exts
function getExts(){
   var ext={"key1":"value1"};
   var result=[];
    for(var i=0;i<2;i++)
    {
        result.push(ext);
    }
    return result;
}
function getTags(){
    var tagsStr=$('#tags').val().trim();
    var arr=tagsStr.split(',');
    var result=[];
    $.each(arr,function(n,value) {
        result.push({name:value});
    });
    return result;
    }
/**
 * 保存信息
 * @returns {boolean}
 */
function getFolderJson(){
    var folder = {
        path: $('#parentFolderPath').val(),
        name:$('#name').val(),
        icon:$('#icon').attr("src"),
        desc: $('#desc').val(),
        tags: getTags(),
        thumbs:getThumbs(),
        exts:getExts()
    };
    var folderJson= JSON.stringify(folder);
    return folderJson;

}
function saveFolder() {
  //  if(!validateForm()) return false;
  //  showTips('系统正在处理, 请稍等...','info');
  //  var tagType = $('#tagType .checker span.checked').length;
    var folder={
        appId:$('#appId').val(),
        parentFolderPath:$('#parentFolderPath').val(),
        folderJson:getFolderJson(),
        ticket:$('#userId').val()
    }
    if (!window.confirm('是否确定保存目录?')) {
        return false;
    }
    requstMerge(folder);

}

function requstMerge(jsonData) {
    $.ajax({
        url: '/sloth/folder/save',
        type: 'post',
        data:jsonData,//
        success: function (res) {
            if (res.code==200) {
                alert('目录保存操作成功');
                $("#searchData").click();
                showTips('目录保存成功.',"success");

            } else {
                alert('目录保存操作失败');
                showTips('保存失败',"error");
            }
        },
        error: function (res) {
         alert('服务器出错,code:'+res.code+"error");
        }
    });
}
function showTips(tips,type) {
    $(".alert").alert('close');
    $('#folder-add-dialog').append('<div id="add-alert-'+type+'" class="alert alert-'+type+'">' +
        '<button class="close" data-dismiss="alert"></button><strong>' + tips + '</strong></div>');
}

//icon图标上传

$("#icon_upload").uploadify({
    fileObjName: 'file',
    height: 25,
    swf: '/lib/uploadify/uploadify.swf',
    uploader: '/sloth/fsbs2',
    buttonText: '上传封面',
    multi: false,
    fileSizeLimit: '4MB',
    fileTypeDesc: 'Image Files',
    fileTypeExts: '*.jpg; *.jpng; *.png;',
    onUploadSuccess: function (file, data, response) {
        data = $.parseJSON(data);
        if (data.code == 200) {
            if(data.filePath){
                $('#icon').attr("src", data.filePath);

            }else{
                alert("获取图片路径失败,请重试");
            }
        } else {
            window.alert(data.message);
        }
    }
});

//thumbs图标上传
$("#thumbs_upload").uploadify({
    fileObjName: 'file',
    height: 25,
    swf: '/lib/uploadify/uploadify.swf',
    uploader: '/sloth/fsbs2',
    buttonText: '上传thumbs图片',
    multi: false,
    fileSizeLimit: '4MB',
    fileTypeDesc: 'Image Files',
    fileTypeExts: '*.jpg; *.jpng; *.png;',
    onUploadSuccess: function (file, data, response) {
        data = $.parseJSON(data);
        if (data.code == 200) {
            if(data.filePath){
                $('#thumbs').attr("src", data.filePath);
                var url=data.filePath;
                var width=250;
                var height=250;
                var altText="测试测试测试测试测试测试";
                var Imagesection=new Object();
                Imagesection.id = folderThumbsSectionsSize;
                Imagesection.url=url;
                Imagesection.width=width;
                Imagesection.height=height;
                Imagesection.altText=altText;
                folderThumbsSections.push(Imagesection);
                $('#folderContentsDiv ol').append('<li class="folderContentIndex" id="folderContentIndex'+folderThumbsSectionsSize+'"><i class="contentClose"id="Index' + folderThumbsSectionsSize + '"></i><img src="'+url+'" style="width:250px;height: 250px"> </img></li>');
                folderThumbsSectionsSize++;
            }else{
                alert("获取图片路径失败,请重试");
            }
        } else {
            window.alert(data.message);
        }
    }
});

