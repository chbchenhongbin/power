
var adminUrl = "http://127.0.0.1:8888";


var systemhl="/erp/hl";


//文件上传参数
var appId = "1";
var type = 1;

var SystemId = "backend-web";
var gatewayAuthorization = SystemId + "-Authorization"; //将废弃
var currentUserId = SystemId + "-currentUserId";
var currentUserName = SystemId + "-currentUserName";
var currentTokenStr = SystemId + "-currentTokenStr";

// 顶级Iframe
var topFrame = top.frames["pageiframe"];


window.console = window.console || (function () {
    var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile
        = c.clear = c.exception = c.trace = c.assert = function () { };
    return c;
})();

function loginFilterNoAuth() {
    var tp = window;
    while ((!tp.autoForwardLoginPage) && tp.parent != tp) {
        tp = tp.parent
    }
    if(tp.autoForwardLoginPage){
        tp.autoForwardLoginPage();
    }
}

function loginFilter() {
    if (!$.cookie(gatewayAuthorization)) {
        var tp = window;
        while ((!tp.autoForwardLoginPage) && tp.parent != tp) {
            tp = tp.parent
        } 
        if(tp.autoForwardLoginPage){
        tp.autoForwardLoginPage();
    }
    }
}

//正则表达式，获取地址中的参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires + "; path=/";//path=/是根路径
}
//获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
//清除cookie
function clearCookie(name) {
    setCookie(name, "", -1);
}

function getRootPath() {
    var scripts = document.getElementsByTagName("script")
    var script = scripts[scripts.length - 1];
    strJsPath = document.querySelector ? script.src : script.getAttribute("src")//IE8直接.src
    return strJsPath.substr(0, strJsPath.lastIndexOf("/static"));
}


var sysRootPath = getRootPath();


function dynamicLoadJs(url, callback) {
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');

    script.type = 'text/javascript';
    script.src = url;
    if (typeof (callback) == 'function') {
        script.onload = script.onreadystatechange = function () {
            if (!this.readyState || this.readyState === "loaded" || this.readyState === "complete") {
                callback();
                script.onload = script.onreadystatechange = null;
            }
        };
    }
    head.appendChild(script);
}


function GetHttpRequest() {
    if (window.XMLHttpRequest) // Gecko
        return new XMLHttpRequest();
    else if (window.ActiveXObject) // IE
        return new ActiveXObject("MsXml2.XmlHttp");
}


function AjaxPage(sId, url) {
    var oXmlHttp = GetHttpRequest();
    oXmlHttp.onload = function () {
        if (oXmlHttp.readyState == 4) {
            if (oXmlHttp.status == 200 || oXmlHttp.status == 304) {
                IncludeJS(sId, url, oXmlHttp.responseText);
            }
            else {
                alert('XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')');
            }
        }
    }
    oXmlHttp.open('GET', url, false);
    oXmlHttp.send(null);
}


function IncludeJS(sId, fileUrl, source) {
    if ((source != null) && (!document.getElementById(sId))) {
        var oHead = document.getElementsByTagName('HEAD').item(0);
        var oScript = document.createElement("script");
        oScript.language = "javascript";
        oScript.type = "text/javascript";
        oScript.id = sId;
        oScript.defer = true;
        oScript.text = source;
        oHead.appendChild(oScript);
    }
}

function loadBaseJS() {
    AjaxPage("jquery.min", sysRootPath + "/static/js/jquery.min.js");
    AjaxPage("jquery.cookie", sysRootPath + "/static/js/jquery.cookie.js");
    AjaxPage("ax", sysRootPath + "/static/js/ax.js");
}

// 弹框提示
function showMsg(msg,defaultMsg,icon) {
    if(icon){
        top.layer.msg((msg)?msg:defaultMsg,{icon:icon});
    }else {
        top.layer.msg((msg)?msg:defaultMsg);
    }

}


//兼容ie9提示
function platext() {
    function placeholderSupport() {
        return 'placeholder' in document.createElement('input');
    }
    if(!placeholderSupport()){   // 判断浏览器是否支持 placeholder
        $("[placeholder]").each(function(){
            var _this = $(this);
            var left = _this.css("padding-left");
            _this.parent().append('<span class="placeholder1" data-type="placeholder" style="left: ' + left + '">' + _this.attr("placeholder") + '</span>');
            if(_this.val() != ""){
                _this.parent().find("span.placeholder1").hide();
            }
            else{
                _this.parent().find("span.placeholder1").show();
            }
        }).on("focus", function(){
            $(this).parent().find("span.placeholder1").hide();
        }).on("blur", function(){
            var _this = $(this);
            if(_this.val() != ""){
                _this.parent().find("span.placeholder1").hide();
            }
            else{
                _this.parent().find("span.placeholder1").show();
            }
        });
        // 点击表示placeholder的标签相当于触发input
        $("span.placeholder1").on("click", function(){
            $(this).hide();
            $(this).siblings("[placeholder]").trigger("click");
            $(this).siblings("[placeholder]").trigger("focus");
        });
    }
}

//阻止backspace 回退
function banBackSpace(e){
    var ev = e || window.event;
    //各种浏览器下获取事件对象
    var obj = ev.relatedTarget || ev.srcElement || ev.target ||ev.currentTarget;
    //按下Backspace键
    if(ev.keyCode == 8){
        var tagName = obj.nodeName //标签名称
        //如果标签不是input或者textarea则阻止Backspace
        if(tagName!='INPUT' && tagName!='TEXTAREA'){
            return stopIt(ev);
        }
        var tagType = obj.type.toUpperCase();//标签类型
        //input标签除了下面几种类型，全部阻止Backspace
        if(tagName=='INPUT' && (tagType!='TEXT' && tagType!='TEXTAREA' && tagType!='PASSWORD' && tagType!='NUMBER')){
            return stopIt(ev);
        }
        //input或者textarea输入框如果不可编辑则阻止Backspace
        if((tagName=='INPUT' || tagName=='TEXTAREA') && (obj.readOnly==true || obj.disabled ==true)){
            return stopIt(ev);
        }
    }
}
function stopIt(ev){
    if(ev.preventDefault ){
        //preventDefault()方法阻止元素发生默认的行为
        ev.preventDefault();
    }
    if(ev.returnValue){
        //IE浏览器下用window.event.returnValue = false;实现阻止元素发生默认的行为
        ev.returnValue = false;
    }
    return false;
}



//调用阻止backspace 回退方法
$(function(){
    //实现对字符码的截获，keypress中屏蔽了这些功能按键
    document.onkeypress = banBackSpace;
    //对功能按键的获取
    document.onkeydown = banBackSpace;
});


//系统长时间无操作自动退出
$(function () {
    var lastTime = new Date().getTime();
    var currentTime = new Date().getTime();
    var timeOut = 40 * 60 * 1000; //设置超时时间： 40分

    $(function(){
        /* 鼠标移动事件 */
        $(document).mouseover(function(){
            lastTime = new Date().getTime(); //更新操作时间

        });
    });
    function getUrlname(url){ //假如传进来的url是 http://www.qq.com/index.html?name=joey 这里一共是有3个斜杠,如果我们想获取index.html
        url=url.split('?')[0] ;// 我们只要?号前的
        var urlSlashCount=url.split('/').length; // 统计有3斜杠
        return url.split('/')[urlSlashCount-1].toLowerCase(); //获取数组最后一个
    }

    var path=getUrlname(window.location.href);
    var urldata=["login.html","forgetPassword.html","register.html","registerEdit.html","about.html","audit.html","auditFailure.html","contactUs.html","service.html"];

    function testTime(){
        currentTime = new Date().getTime(); //更新当前时间
        if(currentTime - lastTime > timeOut){ //判断是否超时
            clearCookie(currentUserId);
            clearCookie(gatewayAuthorization);
            clearCookie("menuList");
            clearCookie('username');
            loginFilterNoAuth();
        }
    }

    if(jQuery.inArray(path, urldata)==-1){
        /* 定时器  间隔1秒检测是否长时间未操作页面  */
         window.setInterval(testTime, 1000);
    }else {
        console.log('====忽略检测界面是否操作====')
    }


})