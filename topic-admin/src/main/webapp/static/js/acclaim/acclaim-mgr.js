var acclaimAddDialog = null;
var app=getUrlParams("appId");
var targetId=getUrlParams("targetId");
var typeId=getUrlParams("typeId");
$(function() {
    if (app){$('#query-appId').val(app)};
    if(targetId)($('#query-targetId').val(targetId));
    if(typeId)($('#query-typeId')).val(typeId);
    $('#acclaim-grid').tinygrid({
        url: "",
        dataType: 'json',
        colModel:[
            {header:'', dataIndex:'ck', cellRenderer2:function(cells, trid) {
                return '<input type="checkbox" align=center name="ids" id="'+cells._Id+'"/>';
            }},
            {header:'_Id',dataIndex:'_Id',field:'_Id',cellRenderer2:function(cells){
                return cells['_Id'].replace(/\"/g,"").trim();
            }},
            {header:'uid',dataIndex:'uid',field:'uid',cellRenderer2:function(cells){
                return cells['uid'].replace(/\"/g,"").trim();
            }},
            {header:'targetId',dataIndex:'targetId',field:'targetId',cellRenderer2:function(cells){
                return cells['targetId'].replace(/\"/g,"").trim();
            }},
            {header:'createTime',dataIndex:'createTime',field:'createTime',cellRenderer2:function(cells){
                return cells['createTime'].replace(/\"/g,"").trim();
            }},
            {header:'操作', dataIndex:'op', tdCss:'width:50px;',cellRenderer2:function(cells, trid) {
                return'<a href="javascript:void(0);" onclick="deleteThis(\''+trid+'\')">'
                    + '<i class="icon icon-edit" title="删除">删除</i></a>';
            }
            }
        ],
        rowClickToDoTick:true,
        autoload : true,
        usepager: true,
    });
    $("#searchData").click(function(){
        var typeId=$("#query-typeId").val();
        switch (typeId){
            case "Article":
                $('#acclaim-grid').gridOptions({
                    url: "/sloth/acclaim/listArticleAcclaim",
                    newp : 1,
                    params: [
                        {name: 'appId',value: $("#query-appId").val()},
                        {name: 'targetId',value: $("#query-targetId").val()}
                    ]
                }).gridReload();
                break;
            case "Post":
                $('#acclaim-grid').gridOptions({
                    url: "/sloth/acclaim/listPostAcclaim",
                    newp : 1,
                    params: [
                        {name: 'appId',value: $("#query-appId").val()},
                        {name: 'targetId',value: $("#query-targetId").val()}
                    ]
                }).gridReload();
                break;
            default :alert('点赞范围匹配有误');
        }
    });
    $("#resetbtn").click(function(){
        $('#query-appId').select2('data', 'erd_app');
        $('#query-typeId').select2('data', "Article");
        $('#query-targetId').select2('data', null);
    });

    //初始化默认查询
    $("#searchData").click();
    //新增事件
    $("#add-btn").click(function() {
        //  resetForm();
        //  setDefault();
        acclaimAddDialog = $.dialog({
            title: '新增点赞',
            content: $('#add-dialog')[0],
            lock: false,
            id: 'add-dialog-model'
        });
    });

    $('#delete-selected').click(function() {
        var datas = $('#acclaim-grid')[0].tinygrid.getSelectedCellValues(['uid','targetId']);
        var appId=$('#qurey-appId').val();
        var  arr = [];
        var size=0;
        $.each(datas,function(n,v) {
            var acclaim=new Object();
            acclaim.uid= v.uid;
            acclaim.tid= v.targetId;
            arr.push(acclaim);
            size++;
        });
        if (!window.confirm('是否确定删除：\n共'+size+'篇点赞?')) {
            return false;
        }
        var acclaimJson=JSON.stringify(arr);
        var typeId=$('#query-typeId').val();
        switch (typeId){
            case "Article":
                requestDeleteArticleAcclaim(acclaimJson);
                break;
            case "Post":
                requestDeletePostAcclaim(acclaimJson);
                break;
            default :alert("点赞范围参数有误");
        }
    });

    $('#cancel-add').click(function() {
        acclaimAddDialog && acclaimAddDialog.close();
        acclaimAddDialog = null;
    });
});

function updateThis(trid) {
    var cellValues = $('#article-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [   '_Id',
            'uid',
            'targetId',
            'createTime'
        ])[0];
    acclaimAddDialog = $.dialog({
        title: '修改点赞['+cellValues._Id+']',
        content: $('#acclaim-add-dialog')[0],
        lock: false,
        id: 'add-dialog-model'
    });
    resetForm();
    setDefault();
}

function requestDeleteArticleAcclaim(acclaimJson){
    var appId=$('#query-appId').val();
    $.ajax({
        url: '/sloth/acclaim/deleteArticleAcclaim',
        type: 'get',
        data: {
            appId:appId,
            acclaimJson: acclaimJson,
        },
        success: function (json) {
            var code = json.code;
            if(code==200 ){
                alert('删除操作成功,当前文章点赞数:'+json.acclaimCount);
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
function requestDeletePostAcclaim(acclaimJson){
    var appId=$('#query-appId').val();
    $.ajax({
        url: '/sloth/acclaim/deletePostAcclaim',
        type: 'get',
        data: {
            appId:appId,
            acclaimJson: acclaimJson,
        },
        success: function (json) {
            var code = json.code;
            if(code==200 ){
                alert('删除操作成功,当前评论点赞数:'+json.acclaimCount);
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
    var cellValues = $('#acclaim-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [  'uid',
            'targetId'
        ])[0];
    var id=cellValues._Id;
    console.log("delete id:"+id);
    if (!window.confirm('是否确定删除文章?'+id+'')) {
        return false;
    }
    var arr=[];
    var acclaim=new Object();
    acclaim.uid=cellValues.uid;
    acclaim.tid=cellValues.targetId;
    arr.push(acclaim);
    var acclaimJson=JSON.stringify(arr);
    var typeId=$('#query-typeId').val();
    switch (typeId){
        case "Article":
            requestDeleteArticleAcclaim(acclaimJson);
            break;
        case "Post":
            requestDeletePostAcclaim(acclaimJson);
            break;
        default :alert("点赞范围参数有误");
    }
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
        content: $('#acclaim-add-dialog')[0],
        lock: false,
        id: 'add-dialog-model'
    });
});

