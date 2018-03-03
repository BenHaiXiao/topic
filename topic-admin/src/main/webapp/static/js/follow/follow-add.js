
$(function () {
    //保存动作
    $('#follow-save').click(function () {
        save();
    });
    $('#follow-cancel').click(function () {
        followAddDialog && followAddDialog.close();
        followAddDialog = null;
    });
/**
 * 保存点赞信息
 *
 */
function save() {
    //var result = validateForm();
  //  if(!result) return false;
   // showTips('系统正在处理, 请稍等...','info');
    var followFormData = {
        appId: $('#appid').val(),
        targetId: $('#targetId').val(),
        ticket:$('#uid').val()
       };
    if (!window.confirm('是否确定保存评论?')) {
        return false;
    }
    var  typeId=$('#typeId').val();
    switch (typeId){
        case "Folder":
            saveFolderFollow(followFormData);
            break;
        case "Article":
            saveArticleFollow(followFormData);
            break;
        default :
            alert("没有选择点赞范围");
    }

}

    function saveFolderFollow(followFormData ){
        $.ajax({
            url: '/sloth/follow/saveFolderFollow',
            type: 'post',
            data: followFormData,
            success: function (json) {
                var code = json.code;
                if(code==200){
                    alert('操作成功,当前文章点赞数：'+json.followCount);
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
    function saveArticleFollow(followFormData ){
        $.ajax({
            url: '/sloth/follow/saveArticleFollow',
            type: 'post',
            data: followFormData,
            success: function (json) {
                var code = json.code;
                if(code==200){
                    alert('操作成功,当前评论点赞数：'+json.followCount);
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
})