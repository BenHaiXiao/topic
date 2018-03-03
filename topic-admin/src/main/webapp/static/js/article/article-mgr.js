var articleAddDialog = null;
var thumbUploadDialog = null;//qupeng 图片内容上传单独窗口
var app=getUrlParams("appId");
var fPath=getUrlParams("folderPath");
$(function() {
    if (app){$('#query-appId').val(app)};
    if(fPath)($('#query-folderPath').val(fPath));
	$('#article-grid').tinygrid({
        url: "",
        dataType: 'json',
        colModel:[
            {header:'', dataIndex:'ck', cellRenderer2:function(cells, trid) {
                return '<input type="checkbox" align=center name="ids" id="'+cells.nodeId+'"/>';
            }},
            {header:'',dataIndex:'showDetail',details: true,tdCss:'width:25px;'},
            {header:'nodeId',dataIndex:'nodeId',field:'nodeId',cellRenderer2:function(cells){
                return cells['nodeId'].replace(/\"/g,"").trim();
            }},
            {header:'folderPath',dataIndex:'folderPath',field:'folderPath',cellRenderer2:function(cells){
                return cells['folderPath'].replace(/\"/g,"").trim();
            }},
            {header:'title',dataIndex:'title',field:'title',cellRenderer2:function(cells){
                return cells['title'].replace(/\"/g,"").trim();
            }},
            {header:'brief',dataIndex:'brief',field:'brief',cellRenderer2:function(cells){
                return cells['brief'].replace(/\"/g,"").trim();
            }},
            {header:'标签',dataIndex:'tags',field:'marks',cellRenderer:tags},
            {header:'内容content',dataIndex:'content',field:'content',hide:true},
            {header:'subContent',dataIndex:'subContent',cellRenderer2: function (cells) {
                var arr = cells['content'];
                var sections=arr[0].sections;
                var subContent="";
                $(sections).each(function(i,v)
                {
                   if(v.type==="text")
                    {
                        subContent= JSON.stringify(v.data);
                        if (subContent.length>=50){
                          subContent=subContent.substring(0,50)+'....';
                        }
                    }

                })
                return '<p title="'+cells['content']+'">'+subContent+'</p>';
            }},
            {header:'creator',dataIndex:'creator',field:'creator'},
            {header:'createTime',dataIndex:'createTime',field:'createTime'},
            {header: 'updateTime', dataIndex: 'updateTime',field:'updateTime',hide:true},
            {header:'valid',dataIndex:'valid',field:'valid'},
            {header:'操作', dataIndex:'op', tdCss:'width:50px;',cellRenderer2:function(cells, trid) {
                return '<a href="javascript:void(0);" onclick="updateThis(\'' + trid + '\')">'
                    + '<i class="icon icon-edit" title="修改/详细">修改</i></a>'
                    + '<a href="javascript:void(0);" onclick="listPost(\''+trid+'\')">'
                    + '<i class="icon icon-edit" title="查询评论">查询评论</i></a>'
                    + '<a href="javascript:void(0);" onclick="listAcclaim(\'' + trid + '\')">'
                    + '<i class="icon icon-edit" title="查询点赞">查询点赞</i></a>'
                    + '<a href="javascript:void(0);" onclick="listFollow(\'' + trid + '\')">'
                    + '<i class="icon icon-edit" title="查询关注">查询关注</i></a>'
                    + '<a href="javascript:void(0);" onclick="deleteThis(\''+trid+'\')">'
                    + '<i class="icon icon-edit" title="删除">删除</i></a>';

            }
            }
        ],
        rowClickToDoTick:true,
        autoload : true,
        usepager: true,
        showDetail: function(tr, placeholder) {

            var cellValues = placeholder.tinygrid.getCellValuesInSpecifiedRows([tr], ['content'])[0];
            var data=cellValues['content'];
            if (isNull(data)) return "无"
            else {
                var articleSectionsData=JSON.parse(data);
                var size=articleSectionsData.size;
                var articleSections=articleSectionsData.sections;
                if( articleSections instanceof Array){
                    var articelSectionStr="";
                    for (var i=articleSections.length-1;i>=0;i--) {
                        var articlesection = articleSections[i];
                        if (articlesection.type === "IMAGE") {
                            var image = articlesection.image;
                            var temp = '<img width="150px" style="height:150px;" src=' + image.url + ' class="img-responsive" />&nbsp&nbsp&nbsp&nbsp';
                            articelSectionStr = temp + articelSectionStr;
                        }else if(articlesection.type === "TEXT"){
                            var text = articlesection.text;
                            var temp = '<textarea style="height:150px;width: 150px" aria-readonly="true">"'+text+'"</textarea>&nbsp&nbsp&nbsp&nbsp';
                            articelSectionStr = temp + articelSectionStr;
                        }
                    }
                    var result='<p><strong>图片数量:'+size+'</strong><br>图片详情：'+articelSectionStr +'<div></p>';
                    return result;
                }else return 'images不是数组';
            }
        }
    });

    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        language: "zh-CN",
        todayBtn: true,
        autoclose: true,
        clearBtn: true,
        pickerPosition: "bottom-left"
    });
    $("#searchData").click(function(){
        $('#article-grid').gridOptions({
            url: "/sloth/article/listArticle",
            newp : 1,
            params: [
                {name: 'appId',value: $("#query-appId").val()},
                {name: 'folderPath',value: $("#query-folderPath").val()},
                {name: 'articleId',value: $("#query-articleId").val()}
            ]
        }).gridReload();
    });
    $("#resetbtn").click(function(){
        $('#query-appId').select2('data', 'erd_app');
        $('#query-folderPath').select2('data', null);
        $('#query-articleId').select2('data', null);
    });

    //初始化默认查询
   $("#searchData").click();
    //新增事件
    $('#add-btn').click(function() {
      //  resetForm();
      //  setDefault();
      articleAddDialog = $.dialog({
            title: '新增文章歌单',
            content: $('#add-dialog')[0],
            lock: false,
            id: 'add-dialog-model'
        });
    });


    $('#delete-selected').click(function() {
        var datas = $('#article-grid')[0].tinygrid.getSelectedCellValues(['nodeId','folderPath']);
        var appId=$('#qurey-appId').val();
        var  arr = [];
        var size=0;
            $.each(datas,function(n,value) {
                    arr.push(value.nodeId);
                     size++;
            });
        if (!window.confirm('是否确定删除：\n共'+size+'篇文章?')) {
            return false;
        }
        requestDelete(arr.join(','));
    });

    $('#cancel-add').click(function() {
        articleAddDialog && articleAddDialog.close();
        articleAddDialog = null;
    });
});

function updateThis(trid) {
    var cellValues = $('#article-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [   'nodeId',
            'folderPath',
            'title',
            'brief',
            'tags',
            'content',
            'creator',
            'createTime',
            'updateTime',
            'valid'
        ])[0];
    articleAddDialog = $.dialog({
        title: '修改目录['+cellValues.nodeId+']',
        content: $('#article-add-dialog')[0],
        lock: false,
        id: 'add-dialog-model'
    });
    resetForm();
    setDefault();
    $('#folderPath').val(cellValues.folderPath);
    $('#title').val(cellValues.title);
    $('#brief').val(cellValues.brief);
    $("#tags").val(cellValues.tags);
    $("#uid").val(cellValues.creator);
    $("#validFlag").select2('data', cellValues.valid);

    //图片、内容 qupeng
    resetThumbContent();
    //thumbUploadDialog = $.dialog({
    //    title: '文章图片与内容',
    //    //content: $('#add-dialog')[0],
    //    content: $('#thumb-upload-dialog')[0],
    //    lock: false,
    //    id: 'thumb-content-btn'
    //});
    setContents(cellValues.content);
}

function requestDelete(idStr){
    var appId=$('#query-appId').val();
    $.ajax({
        url: '/sloth/article/delete',
        type: 'get',
        data: {
            appId:appId,
            articleIds: idStr
        },
        success: function (json) {
            var code = json.code;
            if(code==200 ){
                alert('删除操作成功');
                $("#searchData").click();
            }else{
                alert('删除操作失败，请联系管理员');
            }
        },
        error: function () {
            alert("服务器忙，请稍后再试");
        }
    });
}

function deleteThis(trid) {
    var cellValues = $('#article-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [  'nodeId'
        ])[0];
    var id=cellValues.nodeId;
    console.log("delete id:"+id);
    if (!window.confirm('是否确定删除文章?'+id+'')) {
        return false;
    }
    requestDelete(id);
}
function listAcclaim(trid) {
    var cellValues = $('#article-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [  'nodeId'
        ])[0];
    var targetId=cellValues.nodeId;
    var appId= $("#query-appId").val();
    var typeId="Article";
    window.location.href='http://localhost:8081/sloth/acclaim/mgr2?appId='+appId+'&targetId='+''+targetId+'&typeId='+''+typeId+'';

}
function listFollow(trid) {
    var cellValues = $('#article-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [  'nodeId'
        ])[0];
    var targetId=cellValues.nodeId;
    var appId= $("#query-appId").val();
    var typeId="Article";
    window.location.href='http://localhost:8081/sloth/follow/mgr2?appId='+appId+'&targetId='+''+targetId+'&typeId='+''+typeId+'';

}

function resetForm(){
    //清除所有数据，包括sections和sectionsSize数据。
}
function setDefault(){
    var now = new Date().Format("yyyy-MM-dd hh:mm");
    $("#createTime").val(now);
    $("#addTime").val(now);
    $("#remarkTime").val(now);

}
//新增事件
$('#add-btn').click(function () {
    folderAddDialog = $.dialog({
        title: '新增目录',
        content: $('#article-add-dialog')[0],
        lock: false,
        id: 'add-dialog-model'
    });
});

function tags(cells) {

    if(isNull(cells))return 'cells为空';
    else {
        var data = cells;
        var marks=JSON.parse(data);
        var tgs=marks.tags;
        var arr=[];
        if ( tgs instanceof Array){
            $.each(tgs,function(i,v)
            {
                arr.push(v.name);
            })
            return arr.join(',');
        }else return '无效标签';
    }
}
function setDefault(){
    articleSectionSize=0;
    articleSections=[];
    $('#folderPath').val('');
    $('#title').val('');
    $('#brief').val('');
    $('#tags').val('');
   $('#imageToContent').attr("src",'');
    $('#textContent').val('');
    $("#validFlag").val(1);
    $('#uid').val('');
    $('#articleContentsDiv ol ').children().remove();
}
function listPost(trid){
    var cells = $('#article-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [  'nodeId'
        ])[0];
    var articleId=cells.nodeId;
    var appId= $("#query-appId").val();
    window.location.href='http://localhost:8081/sloth/post/mgr2?appId='+appId+'&articleId='+articleId+'';
}
function setContents(contents){

    if (!isNull(contents)) {
        var contentsDatas=JSON.parse(contents);
        var size=contentsDatas.size;
        var sections=contentsDatas.sections;
        if( sections instanceof Array) {

            for (var i = 0; i <sections.length ; i++) {
                var section = sections[i];
                switch (section.type) {
                    case "IMAGE":
                    {
                        var image=section.image;
                        var Imagesection = new Object();
                        Imagesection.id = articleSectionSize;
                        Imagesection.type="IMAGE";
                        var imageO=new Object();
                        imageO.url = image.url;
                        imageO.width = image.width;
                        imageO.height = image.height;
                        imageO.altText = image.altText;
                        Imagesection.image=imageO;
                        articleSections.push(Imagesection);
                        //$('#articleContentsDiv').append('<li class="contentIndex" id="contentIndex' + articleSectionSize + '"><img src="' +imageO.url + '" style="width:300px;height: 250px"> </img></li>');
                        $('#articleContentsDiv').append('<img id="contentIndex'+articleSectionSize+'"  src="' +imageO.url + '" style="width:220px;height: 200px"> </img><a id="'+ articleSectionSize+'" class="content-delete" onclick="contentRemove('+articleSectionSize+')"><i class="icon-trash"></i></a>');
                        articleSectionSize++;
                    }
                        break;
                    case "TEXT":
                    {
                        var textSection = new Object();
                        textSection.id = articleSectionSize;
                        textSection.type = "TEXT";
                        textSection.attr = {"font-color": "#FFFFF", "font-size": 12};
                        textSection.text = section.text;
                        articleSections.push(textSection);
                        //$('#articleContentsDiv').append('<li class="contentIndex"  id="contentIndex' + articleSectionSize + '" ><textarea style="height: auto ;width: 300px" aria-readonly="true">' + textSection.text + ' </textarea></li>');
                        $('#articleContentsDiv').append('<textarea id="contentIndex'+articleSectionSize+'" aria-readonly="true">' + textSection.text + ' </textarea><a id="'+ articleSectionSize+'" class="content-delete" onclick="contentRemove('+articleSectionSize+')"><i class="icon-trash"></i></a>');
                        articleSectionSize++;
                    }
                        break;
                }
            }
        }else alert("更新操作sections不是数组")
    }else alert("content内容为空")
}

//查看点赞总数 qupeng
function acclaimThis(trid) {
    var cellValues = $('#article-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [  'nodeId'
        ])[0];
    var id=cellValues.nodeId;
    requestAcclaim(id);
}

//向后台请求点赞总数 qupeng
function requestAcclaim(idStr) {
    var appId=$('#query-appId').val();
    $.ajax({
        url: '/sloth/article/getAcclaimNum',
        type: 'get',
        data: {
            appId:appId,
            articleId: idStr
        },
        success: function (json) {
            var code = json.code;
            var count = json.acclaimNum;
            if(code==200 ){
                alert(idStr+'，点赞总数为' + count);
                //$("#searchData").click();
            }else{
                alert('查询点赞失败，请联系管理员');
            }
        },
        error: function () {
            alert("服务器忙，请稍后再试");
        }
    });
}

//打开图片上传和内容配置的独立窗口 qupeng
$("#thumb-content-btn").click(function(){
    thumbUploadDialog = $.dialog({
        title: '文章图片与内容',
        //content: $('#add-dialog')[0],
        content: $('#thumb-upload-dialog')[0],
        lock: false,
        id: 'thumb-content-btn'
    });
});

function thumbContentDialog(_content,_id) {
    thumbUploadDialog = $.dialog({
        title: '文章图片与内容',
        //content: $('#add-dialog')[0],
        content: _content,
        lock: false,
        id: _id
    });
}

//保存并关闭图片上传和内容配置的独立窗口 qupeng
$("#thumb-save-btn").click(function () {
    thumbUploadDialog.close();
    thumbUploadDialog = null;
});

//关闭图片上传和内容配置的独立窗口 qupeng
$("#thumb-cancel-btn").click(function () {
    thumbUploadDialog.close();
    thumbUploadDialog = null;
});

//去除上传的图片、内容
function contentRemove(id) {
    $("#"+id+"").remove();
    $("#contentIndex"+id).remove();
}

//清空图片内容上传窗口
function resetThumbContent() {
    $("#articleContentsDiv").html("");
    $("#imageToContent").attr("src","");
}