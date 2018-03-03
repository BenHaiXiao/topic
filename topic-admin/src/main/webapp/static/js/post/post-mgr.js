var postAddDialog = null;
var aId=getUrlParams("appId");
var arcId=getUrlParams("articleId");
$(function() {
    if (aId){$('#query-appId').val(aId)};
    if(arcId){$('#query-articleId').val(arcId)};
var xx=$('#query-articleId').val(arcId);
    $('#post-grid').tinygrid({
        url: "",
        dataType: 'json',
        colModel:[
            {header:'', dataIndex:'ck', cellRenderer2:function(cells, trid) {
                return '<input type="checkbox" align=center name="ids" id="'+cells.id +'"/>';
            }},
            {header:'',dataIndex:'showDetail',details: true,tdCss:'width:25px;'},
            {header:'评论记录ID',dataIndex:'id',field:'id',cellRenderer2:function(cells){
                return cells['id'].replace(/\"/g,"").trim();
            }},
            {header:'被评论对象id',dataIndex:'targetId',field:'targetId',cellRenderer2:function(cells){
                return cells['targetId'].replace(/\"/g,"").trim();
            }},
            {header:'创建者',dataIndex:'creator',field:'creator.uid',cellRenderer2:function(cells){
                return cells['creator'].replace(/\"/g,"").trim();
            }},
            {header:'回复replyId',dataIndex:'replyId',field:'replyId'},
            {header:'点赞数量',dataIndex:'acclaimCount',field:'acclaimCount'},
            {header:'创建时间',dataIndex:'createTime',field:'createTime'},
            {header:'更新时间',dataIndex:'updateTime',field:'updateTime'},
            {header:'内容',dataIndex:'content',field:'content',hide:true},
            {header:'评论内容',dataIndex:'subContent',field:'content',cellRenderer2:function(cells, trid){
                var content = cells.content;
                if(content.length>=50){
                    content = content.substr(0,50)+'...'
                }
                return '<p title="'+cells.content+'">'+content+'</p>';
            }},
            {header:'有效',dataIndex:'valid',field:'valid'},
            {header:'操作', dataIndex:'op', tdCss:'width:50px;',cellRenderer2:function(cells, trid) {
                return '<a href="javascript:void(0);" onclick="updateThis(\'' + trid + '\')">'
                    + '<i class="icon icon-edit" title="修改/详细">修改</i></a>'
                    + '<a href="javascript:void(0);" onclick="listAcclaim(\'' + trid + '\')">'
                    + '<i class="icon icon-edit" title="查询点赞">查询点赞</i></a>'
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
                var postSectionsData=JSON.parse(data);
                var size=postSectionsData.size;
                var postSections=postSectionsData.sections;
                if( postSections instanceof Array){
                    var postSectionStr="";
                    for (var i=postSections.length-1;i>=0;i--) {
                        var postSection = postSections[i];
                        if (postSection.type === "IMAGE") {
                            var image = postSection.image;
                            var temp = '<img width="150px" style="height:150px;" src=' + image.url + ' class="img-responsive" />&nbsp&nbsp&nbsp&nbsp';
                            postSectionStr = temp + postSectionStr;
                        }else if(postSection.type === "TEXT"){
                            var text = postSection.text;
                            var temp = '<textarea style="height:150px;width: 150px" aria-readonly="true">"'+text+'"</textarea>&nbsp&nbsp&nbsp&nbsp';
                            postSectionStr = temp + postSectionStr;
                        }
                    }
                    var result='<p><strong>图片数量:'+size+'</strong><br>图片详情：'+postSectionStr +'<div></p>';
                    return result;
                }else return 'images不是数组';
            }
        }

    });

    $("#searchData").click(function(){
        $('#post-grid').gridOptions({
            url: "/sloth/post/list",
            newp : 1,
            params: [
                {name: 'appId',value: $("#query-appId").val()},
                {name: 'articleId',value: $("#query-articleId").val()},
                {name: 'postId',value: $("#query-postId").val()}
            ]
        }).gridReload();
    });
    $("#resetbtn").click(function(){
    });
    //初始化默认查询
    $("#searchData").click();
    //新增事件
    $('#add-btn').click(function() {
        postAddDialog = $.dialog({
            title: '新增评论语',
            content: $('#post-add-dialog')[0],
            lock: false,
            id: 'add-dialog-model'
        });
        setDefault();
    });

    $('#delete-selected').click(function() {
        var datas = $('#post-grid')[0].tinygrid.getSelectedCellValues(['id']);
        var  arr = [];
        $.each(datas,function(n,value) {
            arr.push(value.id);
        });
        if(arr.length<1){
            alert('请选择需要删除的记录');
            return false;
        }
        if (!window.confirm('是否确定删除：共'+arr.length+'个评论语?')) {
            return false;
        }

        requestDelete(arr.join(','));
    });

});

function updateThis(trid) {
    var cellValues = $('#post-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [   'id',
            'targetId',
            'creator',
            'replyId',
            'acclaimCount',
            'createTime',
            'content',
            'valid'
        ])[0];
    postAddDialog = $.dialog({
        title: '修改评论语['+cellValues.id+']',
        content: $('#post-add-dialog')[0],
        lock: false,
        id: 'add-dialog-model'
    });
    setDefault();
    $("#id").val(cellValues.id);
    $("#targetId").val(cellValues.targetId);
    $("#userId").val(cellValues.creator);
    if(cellValues.validFlag=="无效"){
        $("#validFlag").val(0);
    }else{
        $("#validFlag").val(1);
    }
    setPostContents(cellValues.content);
}
function listAcclaim(trid) {
    var cellValues = $('#post-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [  'id'
        ])[0];
    var targetId=cellValues.id;
    var appId= $("#query-appId").val();
    var typeId="Post";
    window.location.href='http://localhost:8081/sloth/acclaim/mgr2?appId='+appId+'&targetId='+''+targetId+'&typeId='+''+typeId+'';

}
function requestDelete(idStr){
    var params={
        appId:$('#query-appId').val(),
        postIds:idStr
    }
    $.ajax({
        url: '/sloth/post/delete',
        type: 'GET',
        data: params,
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
function setDefault(){
//    $("#cover").attr("src","http://empfs.bs2cdn.yy.com/MjlmY2JiMTEtY2I2ZS00MGFmLWI1NDktYjZjMDg4YjA2OGQ5.png");
    //默认值 生效时间：当天
   // var now = new Date().Format("yyyy-MM-dd hh:mm");
     postSections=[];
     postSectionSize=0;
    $("#id").val("");
    $("#targetId").val("");
    $("#userId").val("");
    $("#postImageToContent").attr("src","");
    $("#postTextContent").val("");
    $('#postContentsDiv ol ').children().remove();
}
function setPostContents(contents){

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
                        var Imagesection = new Object();
                        var image=section.image;
                        Imagesection.id = postSectionSize;
                        var imageO=new Object();
                        imageO.url = image.url;
                        imageO.width = image.width;
                        imageO.height = image.height;
                        imageO.altText = image.altText;
                        Imagesection.image=imageO;
                        postSections.push(Imagesection);
                        $('#postContentsDiv ol').append('<li class="contentIndex" id="contentIndex' + postSectionSize + '"><img src="' +image.url+ '" style="width:300px;height: 250px"> </img></li>');
                        postSectionSize++;
                    }
                        break;
                    case "TEXT":
                    {
                        var textSection = new Object();
                        textSection.id = postSectionSize;
                        textSection.type = "TEXT";
                        textSection.attr = {"font-color": "#FFFFF", "font-size": 12};
                        textSection.text = section.text;
                        postSections.push(textSection);
                        $('#postContentsDiv ol').append('<li class="contentIndex"  id="contentIndex' + postSectionSize + '" ><textarea style="height: auto ;width: 300px" aria-readonly="true">' + section.text + ' </textarea></li>');
                        postSectionSize++;
                    }
                        break;
                }
            }
        }else alert("更新操作sections不是数组")
    }else alert("content内容为空")
}