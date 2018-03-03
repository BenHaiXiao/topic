//查询URL参数中的值
function getUrlParams(name) {
    var reg = new RegExp('(^|\\?|&)' + name + '=([^&]*)(\\s|&|$)', 'i');
    if (reg.test(location.href)) return unescape(RegExp.$2.replace(/\+/g, ' '));
    return '';
}
/**
 * 判断是否null
 * *
 * @param data
 *
 * */
function isNull(data)
{  return (data == "" || data == undefined || data == null) ? true: false;  }
