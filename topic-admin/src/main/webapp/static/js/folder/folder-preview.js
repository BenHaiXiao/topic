/**
 * @author xiaobenhai on 2015/10/19.
 */
var folderPreViewDialog=null;
$('#folder-preview-btn').click(function () {
    folderPreViewDialog = $.dialog({
        title: '目录预览',
        content: $('#folder-addDetail-dialog')[0],
        lock: false,
        id: 'add-dialog-model'
    });
    //展示目录信息
    folderPreView();
});
function folderPreView(){
    //恢复初始状态
    setFolderPreViewDefault();
    //渲染数据
    View();
}
//恢复初始状态
function setFolderPreViewDefault(){

}
//渲染数据
function View(){

}




