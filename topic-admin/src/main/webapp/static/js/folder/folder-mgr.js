
$(function() {
    $('#folder-grid').tinygrid({
        url: "",
        dataType: 'json',
        colModel: [
            {
                header: '', dataIndex: 'ck', cellRenderer2: function (cells, trid) {
                    return '<input type="checkbox" align=center name="ids" id="' + cells.nodeId + '"/>';
                },tdCss: 'width:5px'
            },
            {header:'',dataIndex:'showDetail',details: true,tdCss:'width:25px;'},
            {header: 'nodeId', dataIndex: 'nodeId', field: 'nodeId', tdCss: 'width:15px;',cellRenderer2:function(cells){
                return cells['nodeId'].replace(/\"/g,"").trim();
            }},
            {header: '路径', dataIndex: 'path', field: 'path', tdCss: 'width:15px;',cellRenderer2:function(cells){
                return cells['path'].replace(/\"/g,"").trim();
            }},
            {header: '目录名称', dataIndex: 'name', field: 'name', tdCss: 'width:15px;',cellRenderer2:function(cells){
                return cells['name'].replace(/\"/g,"").trim();
            }},
            {header: '图标', dataIndex: 'icon', field: 'icon', tdCss: 'width:150px;',cellRenderer2:function(cells){
                var icon = cells['icon'];
                  if(!isNull(icon)){
                               return '<img width="100px" style="height:100px;" src='+icon+' class="img-responsive" />';
                  }else{
                              return '暂无图片';
                        }
            }},
            {header: '有效', dataIndex: 'valid', field: 'valid', tdCss: 'width:15px;',cellRenderer2:validFlagRenderer},
            {header: '创建时间', dataIndex: 'ctime', field: 'createTime', tdCss: 'width:15px;'},
            {header: '更新时间', dataIndex: 'utime', field: 'updateTime', tdCss: 'width:15px;',hide:true},
            {header: '创建者', dataIndex: 'creator', field: 'creator.uid', tdCss: 'width:15px;',cellRenderer2:function(cells){
                return cells['creator'].replace(/\"/g,"").trim();
            }},
            {header: '标签', dataIndex: 'tags', field: 'marks',cellRenderer:tags},
            {header: 'exts', dataIndex: 'exts', field: 'exts', tdCss: 'width:50px;',hide:true},
            {header: 'stats', dataIndex: 'stats', field: 'stats',hide:true},
            {header: 'exts', dataIndex: 'exts', field: 'exts',hide:true},
            {header: 'desc', dataIndex: 'desc', field: 'description'},
            {header: 'thumbs', dataIndex: 'thumbs', field: 'thumbs',hide:true},
            {header: '操作', dataIndex: 'op', tdCss: 'width:100px;',cellRenderer2:function(cells, trid) {
              //  var cellValues = cells['path'];
               // var appId=$("#query-appId").val();
             //   var folderPath=cellValues;
                    return '<a href="javascript:void(0);" onclick="openFolderModifyDialog(\''+trid+'\')">'
                        + '<i class="icon icon-edit" title="修改/详细">修改</i></a>'
                        + '<a href="javascript:void(0);" onclick="listSubFolder(\''+trid+'\')">'
                        + '<i class="icon icon-edit" title="子目录">子目录</i></a>'
                        + '<a href="javascript:void(0);" onclick="listFolderArticle(\''+trid+'\')">'
                        + '<i class="icon icon-edit" title="文章">文章</i></a>'
                        + '<a href="javascript:void(0);" onclick="listFolderFollow(\''+trid+'\')">'
                        + '<i class="icon icon-edit" title="关注">查看关注</i></a>'
                        + '<a href="javascript:void(0);" onclick="deleteThis(\''+trid+'\')">'
                        + '<i class="icon icon-edit" title="删除">删除</i></a>';

                }
            }
        ],
        rowClickToDoTick: true,
        autoload: true,
        usepager: true,
        showDetail: function(tr, placeholder) {

            var cellValues = placeholder.tinygrid.getCellValuesInSpecifiedRows([tr], ['thumbs'])[0];
            var data=cellValues['thumbs'];
       if (isNull(data)) return "无"
       else {
            var thumbsDatas=JSON.parse(data);
            var size=thumbsDatas.size;
            var images=thumbsDatas.images;
           if( images instanceof Array){
             var arr=[];
               var imageStr=""
            for (var i=images.length-1;i>=0;i--)
            {
                  arr.push(images[i].url);
                var temp='<img width="150px" style="height:150px;" src='+images[i].url+' class="img-responsive" />&nbsp&nbsp&nbsp&nbsp';
                imageStr =temp+imageStr;
             }
            var result='<p><strong>图片数量:'+size+'</strong><br>图片详情：'+imageStr +'<div></p>';
            return result;
           }else return 'images不是数组';
            }
        }
    });

    $("#searchData").click(function () {
        $('#folder-grid').gridOptions({
            url: "/sloth/folder/list",
            newp: 1,
            params: [
                {name: 'appId', value: $("#query-appId").val()},
                {name: 'folderPath', value: $("#query-folderPath").val()}
            ]
        }).gridReload();
    });
    $("#searchDataSubFolder").click(function () {
        $('#folder-grid').gridOptions({
            url: "/sloth/folder/listSubFolder",
            newp: 1,
            params: [
                {name: 'appId', value: $("#query-appId").val()},
                {name: 'parentfolderPath', value: $("#query-parentFolderPath").val()}
            ]
        }).gridReload();
    });


    //初始点击查询触发
    $("#searchData").click();

    $("#resetbtn").click(function () {
    });

    //新增事件
    $('#add-btn').click(function () {
         setDefault();
        folderAddDialog = $.dialog({
            title: '新增目录',
            content: $('#folder-add-dialog')[0],
            lock: false,
            id: 'add-dialog-model'
        });
    });
    $('#delete-selected').click(function() {
        var datas = $('#folder-grid')[0].tinygrid.getSelectedCellValues(['path']);

        var  arrs = [];
        $.each(datas,function(n,value) {
            arrs.push(value.path);
        });
        if (!window.confirm('是否确定删除：\n'+arrs.join('\n')+'\n共'+arrs.length+'首歌单数据?')) {
            return false;
        }
        requestDelete(arrs.join(','));
    });


});


function openFolderModifyDialog(trid) {
    var cells = $('#folder-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [   'path',
            'name',
            'icon',
            'desc',
            'tags',
            'creator',
            'valid',
            'ctime',
            'utime',
            'thumbs'
        ])[0];

    folderAddDialog = $.dialog({
        title: '修改目录['+cells.name+']',
        content: $('#folder-add-dialog')[0],
        lock: false,
        id: 'add-dialog-model'
    });
    resetForm();
    setDefault();
    $("#id").val(cells.id);
    $("#folderPath").val(cells.path);
    $("#name").val(cells.name);
    $("#icon").attr("src",cells.icon);
    $("#desc").val(cells.desc);
    $("#tags").val(cells.tags);
    $("#userId").val(cells.creator);
    setThumbs(cells.thumbs);
}
function  resetForm(){
    $("#folderPath").val("");
    $("#name").val("");
    $("#desc").val("");
    $("#tags").val("");
    $("#icon").attr("src","");
    $("#thumbs").attr("src","");
    $("#validFlag").val("");
    $("#userId").val("");
}
function listSubFolder(trid) {
    var cellValues = $('#folder-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [   'path'
        ])[0];
    $("#query-parentFolderPath").val(cellValues.path);
    //触发子目录查询事件
    $("#searchDataSubFolder").click();

}
function listFolderArticle(trid) {
    var cellValues = $('#folder-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [  'path'
        ])[0];
    var fpath=cellValues.path;
    var appId= $("#query-appId").val();
    window.location.href='http://localhost:8081/sloth/article/mgr2?appId='+''+appId+''+'&folderPath='+''+fpath+'';
}
function listFolderFollow(trid) {
    var cellValues = $('#folder-grid')[0].tinygrid.getCellValuesInSpecifiedRows($('#' + trid),
        [  'nodeId'
        ])[0];
    var targetId=cellValues.nodeId;
    var appId= $("#query-appId").val();
    var typeId="Folder";
    window.location.href='http://localhost:8081/sloth/follow/mgr2?appId='+appId+'&targetId='+''+targetId+'&typeId='+''+typeId+'';
}
function setThumbs(thumbs){

    if (!isNull(thumbs)) {
        var thumbsDatas=JSON.parse(thumbs);
        var size=thumbsDatas.size;
        var images=thumbsDatas.images;
        if( images instanceof Array) {
            for (var i = 0; i < size; i++) {
                var Imagesection=new Object();
                Imagesection.id = folderThumbsSectionsSize;
                Imagesection.url=images[i].url;
                Imagesection.width=images[i].width;
                Imagesection.height=images[i].height;
                Imagesection.altText=images[i].altText;
                folderThumbsSections.push(Imagesection);
                $('#folderContentsDiv ol').append('<li class="folderContentIndex" id="folderContentIndex' + i + '"><i class="contentClose"id="index' + i + '"></i><label class="contentClass" id="contentIndex'+i+'">Xxxxxxxxxxxx</label><img src="' + images[i].url + '" style="width:250px;height: 250px"> </img></li>');
                folderThumbsSectionsSize++;
            }
        }
    }
};
function setDefault(){
    folderThumbsSectionsSize=0;
    folderThumbsSections=[];
    $('#parentFolderPath').val('');
    $('#name').val('');
    $('#iocn').attr("src",'');
    $('#userId').val('');
    $('#desc').val('');
    $('#tags').val('');
   // $('#thumbs').attr("src",'');
    $('#validFlag').val('');
    $('#folderContentsDiv ol ').children().remove();
}
function requestDelete(folderPaths){
    var appId=$("#query-appId").val();
    var Deletedata={
        appId:appId,
        folderPaths:folderPaths
    }
    $.ajax({
        url: '/sloth/folder/delete',
        type: 'get',
        data:Deletedata ,
        success: function (json) {
            var code = json.code;
            if(code===200){
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

//时间选择器
$(".form_datetime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    language: "zh-CN",
    todayBtn: true,
    autoclose: true,
    clearBtn: true,
    pickerPosition: "bottom-left"
});

/**
 * 是否有效
 * @param cells
 * @returns {string}
 */
function validFlagRenderer(cells) {
    var flag = cells['validFlag'], flagName;
    flag = parseInt(flag);
    if (flag == 0) {
        flagName = "无效";
        return '<span class="label label-warning">' + flagName + '</span>';
    } else {
        flagName = "有效";
        return '<span class="label">' + flagName + '</span>';
    }
}
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