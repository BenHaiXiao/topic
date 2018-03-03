var followAddDialog = null;
var app=getUrlParams("appId");
var targetId=getUrlParams("targetId");
var typeId=getUrlParams("typeId");
$(function() {
    if (app){$('#query-appId').val(app)};
    if(targetId)($('#query-targetId').val(targetId));
    if(typeId)($('#query-typeId')).val(typeId);

    $('#follow-grid').tinygrid({
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
            case "Folder":
                $('#follow-grid').gridOptions({
                    url: "/sloth/follow/listFolderFollow",
                    newp : 1,
                    params: [
                        {name: 'appId',value: $("#query-appId").val()},
                        {name: 'targetId',value: $("#query-targetId").val()}
                    ]
                }).gridReload();
                break;
            case "Article":
                $('#follow-grid').gridOptions({
                    url: "/sloth/follow/listArticleFollow",
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
        followAddDialog = $.dialog({
            title: '新增关注',
            content: $('#add-dialog')[0],
            lock: false,
            id: 'add-dialog-model'
        });
    });

    $('#delete-selected').click(function() {
        var datas = $('#follow-grid')[0].tinygrid.getSelectedCellValues(['uid','targetId']);
        var appId=$('#qurey-appId').val();
        var arr = [];
        var size=0;
        $.each(datas,function(n,v) {
            var follow=new Object();
            follow.uid= v.uid;
            follow.tid= v.targetId;
            arr.push(follow);
            size++;
        });
        if (!window.confirm('是否确定删除：\n共'+size+'篇点赞?')) {
            return false;
        }
        var followJson=JSON.stringify(arr);
        var typeId=$('#query-typeId').val();
        switch (typeId){
            case "Folder":
                requestDeleteFolderFollow(followJson);
                break;
            case "Article":
                requestDeleteArticleFollow(followJson);
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
    followAddDialog = $.dialog({
        title: '修改关注['+cellValues._Id+']',
        content: $('#follow-add-dialog')[0],
        lock: false,
        id: 'add-dialog-model'
    });
    resetForm();
    setDefault();
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
    var follow=new Object();
    follow.uid=cellValues.uid;
    follow.tid=cellValues.targetId;
    arr.push(follow);
    var followJson=JSON.stringify(arr);
    var typeId=$('#query-typeId').val();
    switch (typeId){
        case "Folder":
            requestDeleteFolderFollow(followJson);
            break;
        case "Article":
            requestDeleteArticleFollow(followJson);
            break;
        default :alert("点赞范围参数有误");
    }
}

function resetForm(){
    //清除所有数据，包括sections和sectionsSize数据。
}
function setDefault(){

}
//新增事件
$('#add-btn').click(function () {
    followAddDialog = $.dialog({
        title: '新增关注',
        content: $('#follow-add-dialog')[0],
        lock: false,
        id: 'add-dialog-model'
    });
});

function requestDeleteFolderFollow(followJson){
    var appId=$('#query-appId').val();
    $.ajax({
        url: '/sloth/follow/deleteFolderFollow',
        type: 'get',
        data: {
            appId:appId,
            followJson: followJson,
        },
        success: function (json) {
            var code = json.code;
            if(code==200 ){
                alert('删除操作成功,当前文章点赞数:'+json.followCount);
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
function requestDeleteArticleFollow(followJson){
    var appId=$('#query-appId').val();
    $.ajax({
        url: '/sloth/follow/deleteArticleFollow',
        type: 'get',
        data: {
            appId:appId,
            followJson: followJson,
        },
        success: function (json) {
            var code = json.code;
            if(code==200 ){
                alert('删除操作成功,当前评论点赞数:'+json.followCount);
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