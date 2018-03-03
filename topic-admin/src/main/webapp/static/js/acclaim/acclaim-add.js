
$(function () {
    //保存动作
    $('#acclaim-save').click(function () {
        save();
    });
    $('#acclaim-cancel').click(function () {
        acclaimAddDialog && acclaimAddDialog.close();
        acclaimAddDialog = null;
    });
/**
 * 保存点赞信息
 *
 */
function save() {
    //var result = validateForm();
  //  if(!result) return false;
   // showTips('系统正在处理, 请稍等...','info');
    var acclaimFormData = {
        appId: $('#appid').val(),
        targetId: $('#targetId').val(),
        ticket:$('#uid').val()
       };
    if (!window.confirm('是否确定保存评论?')) {
        return false;
    }
   var  typeId=$('#typeId').val();
    switch (typeId){
        case "Article":
            saveArticleAcclaim(acclaimFormData);
            break;
        case "Post":
            savePostAcclaim(acclaimFormData);
            break;
        default :
            alert("没有选择点赞范围");
    }
}

    function saveArticleAcclaim(acclaimFormData ){
        $.ajax({
            url: '/sloth/acclaim/saveArticleAcclaim',
            type: 'post',
            data: acclaimFormData,
            success: function (json) {
                var code = json.code;
                if(code==200){
                    alert('操作成功,当前文章点赞数：'+json.acclaimCount);
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
    function savePostAcclaim(acclaimFormData ){
        $.ajax({
            url: '/sloth/acclaim/savePostAcclaim',
            type: 'post',
            data: acclaimFormData,
            success: function (json) {
                var code = json.code;
                if(code==200){
                    alert('操作成功,当前评论点赞数：'+json.acclaimCount);
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