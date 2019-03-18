var sys_header = {currentUserId: "", Authorization: ""};

var ax = {
    post: function (c) {
        loginFilter();
        ax_ex_layui_jquery_ajax();
        this.urlRandom(c);
        var config = {
            type: "POST",
            cache: false,
            contentType: "application/json",
            dataType: "json",
            autoPrefix: true,
            timeout:3 * 60 * 1000,
            //调用执行后调用的函数
            complete: function (XMLHttpRequest, textStatus) {
                if(textStatus == 'timeout'){
                    showMsg('请求超时','请求超时',5);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 401) {
                    //alert("无权限，请联系管理员,地址：" + this.url);
                    showMsg('请重新登录');
                    setTimeout(function () {
                        loginFilterNoAuth();
                    }, 400);
                } else {
                    console.log(XMLHttpRequest.status + ":" + XMLHttpRequest.readyState + ":" + textStatus);
                }
            }
        };
        this.copyPro(config, c);
        $.ajax(config);
    },
    get: function (c) {
        loginFilter();
        ax_ex_layui_jquery_ajax();
        this.urlRandom(c);
        var config = {
            type: "GET",
            cache: false,
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            autoPrefix: true,
            timeout:3 * 60 * 1000,
            //调用执行后调用的函数
            complete: function (XMLHttpRequest, textStatus) {
                if(textStatus == 'timeout'){
                    showMsg('请求超时','请求超时',5);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 401) {
                    //alert("无权限，请联系管理员,地址：" + this.url);
                    showMsg('请重新登录');
                    setTimeout(function () {
                        loginFilterNoAuth();
                    }, 400);
                } else {
                    console.log(XMLHttpRequest.status + ":" + XMLHttpRequest.readyState + ":" + textStatus);
                }
            }
        };
        this.copyPro(config, c);

        $.ajax(config);
    },
    VTable: function (c) {
        loginFilter();
        ax_ex_layui_jquery_ajax();
        this.urlRandom(c);

        var config = {
            method: "GET",
            autoPrefix: true,
            cache: false,
            headers: this.getHeaders(),
            request: {
                pageName: 'pageIndex'
                , limitName: 'pageSize'
            },
            timeout:3 * 60 * 1000,
            //调用执行后调用的函数
            complete: function (XMLHttpRequest, textStatus) {
                if(textStatus == 'timeout'){
                    showMsg('请求超时','请求超时',5);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 401) {
                    //alert("无权限，请联系管理员,地址：" + this.url);
                    showMsg('请重新登录');
                    setTimeout(function () {
                        loginFilterNoAuth();
                    }, 400);
                } else {
                    console.log(XMLHttpRequest.status + ":" + XMLHttpRequest.readyState + ":" + textStatus);
                }
            },
            parseData: this.parseTableData,
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                , groups: 5 //只显示 1 个连续页码
                , first: false  //不显示首页
                , last: false //不显示尾页
                , limit: 10  //每页显示的条数
                , page: 1   //第几页
                , limits: [5, 10, 20, 30, 50]
            }
        };
        this.copyPro(config, c);
        return layui.table.render(config);
    },
    upload: function (c) {
        var config = {
            headers: this.getHeaders(),
            data: {appId: SystemId}
        };
        this.copyPro(config, c);

        if (config.url.indexOf("?") > 0) {
            config.url = config.url + "&" + ax.getParamStr()
        } else {
            config.url = config.url + "?" + ax.getParamStr()
        }

        return layui.upload.render(config);
    },
    urlRandom: function (c) {
        if (c.url) {
            if (c.url.indexOf("?") > 0) {
                c.url = c.url + "&_r=" + this.getTimeLong();
            } else {
                c.url = c.url + "?_r=" + this.getTimeLong();
            }
        }
    },
    getHeaders: function () {
        var tokenBean = ax.getTokenBean();
        if (tokenBean && tokenBean.access_token) {
            sys_header.Authorization = tokenBean.token_type + " " + tokenBean.access_token;
            sys_header.currentUserId = $.cookie(currentUserId)
        }

        if (sys_header.Authorization == "") {
            sys_header.Authorization = "Bearer " + $.cookie(gatewayAuthorization);
        }
        if (sys_header.currentUserId == "") {
            sys_header.currentUserId = $.cookie(currentUserId);
        }
        // return { currentUserId: getCookie(currentUserId) ,Authorization: "Bearer "+getCookie(gatewayAuthorization)};
        return sys_header;
    },
    getParamStr: function () {
        return "currentUserId=" + $.cookie(currentUserId) + "&appId=" + SystemId;
    },
    copyPro: function (target, from) {
        if (target && from) {
            var fn;
            for (fn in from) {
                target[fn] = from[fn];
            }
        }
    },
    parseTableData: function (res) {
        return {
            "code": res.code,
            "msg": res.message,
            "count": res.data.total,
            "data": res.data.records
        };
    },
    dateInit: function (c) {
        $(c).val($(c).attr("df"));
        $(c).attr("placeholder", "yyyy-MM-dd");
        layui.laydate.render({elem: c, type: 'date'});
    },
    dateTimeInit: function (c) {
        $(c).val($(c).attr("df"));
        $(c).attr("placeholder", "yyyy-MM-dd HH:mm:ss");
        layui.laydate.render({elem: c, type: 'datetime'});
    },
    getTimeLong: function () {
        return new Date().getTime();
    },
    updateCacheHeader: function () {
        var tokenBean = ax.getTokenBean();
        if (tokenBean && tokenBean.access_token) {
            sys_header.Authorization = tokenBean.token_type + " " + tokenBean.access_token;
        }
        sys_header.currentUserId = $.cookie(currentUserId);
    },
    getTokenBean: function () {
        var tokenStr = $.cookie(currentTokenStr);
        if (tokenStr) {
            var tokenBean = JSON.parse(tokenStr);
            return tokenBean;
        } else {
            return null;
        }
    },
    saveTokenBean: function (tokenBean) {
        // tokenBean.expires_in = 30;
        var hm = tokenBean.expires_in * 1000;
        tokenBean.endTime = ax.getTimeLong() + hm;
        tokenBean.tryTime = tokenBean.endTime - (hm / 2);
        var tokenStr = JSON.stringify(tokenBean);
        $.cookie(currentTokenStr, tokenStr);
        $.cookie(gatewayAuthorization, tokenBean.token_type + " " + tokenBean.access_token);
    },
    keepLive: function () {
        var timer = setInterval(function () {
            try {
                var tokenBean = ax.getTokenBean();
                //需要刷新
                if (tokenBean != null && tokenBean.tryTime && tokenBean.tryTime <= ax.getTimeLong()) {
                    ax.post({
                        url: backendWebUrl + "/api/token/refreshToken",
                        type: "POST",
                        data: {"token": tokenBean.access_token, "refreshToken": tokenBean.refresh_token},
                        contentType: "application/x-www-form-urlencoded",
                        success: function (res) {
                            console.log(res);
                            if (res.code == 0) {
                                ax.saveTokenBean(res.data);
                                ax.updateCacheHeader();
                            } else if (res.code == -1) {
                                top.layer.confirm('页面已过期请重新登录？', {
                                    btn : [ '确定', '取消' ]//按钮
                                }, function(index) {
                                    layer.close(index);
                                    var index = top.layer.load(0,{shade: [0.7, '#393D49']}, {shadeClose: true}); //0代表加载的风格，支持0-2
                                    loginFilterNoAuth();
                                });
                            }
                        }
                    });
                } else {
                    //console.log("刷新倒计时："+(tokenBean.tryTime-ax.getTimeLong())/(1000));
                }
            } catch (ex) {
                console.log(ex);
            }
        }, 1000 * 10);//10秒尝试刷新一次
    }
}


function ax_ex_jquery_ajax(jq) {
    if (jq.exAjax) {
        return;
    }
    jq.exAjax = true;

    //备份jquery的ajax方法
    var _ajax = jq.ajax;
    //重写jquery的ajax方法
    jq.ajax = function (opt) {
        //备份opt中error和success方法
        var fn = {
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            },
            success: function (data, textStatus) {
            },
            complete: function (XMLHttpRequest, textStatus) {
            },
            beforeSend: function (XMLHttpRequest) {
            }
        };
        if (opt.error) {
            fn.error = opt.error;
        }
        if (opt.success) {
            fn.success = opt.success;
        }
        if (opt.beforeSend) {
            fn.beforeSend = opt.beforeSend;
        }
        if (opt.complete) {
            fn.complete = opt.complete;
        }
        // //扩展增强处理
        var _opt = jq.extend(opt, {
            error: function (xhr, textStatus, errorThrown) {
                fn.error(xhr, textStatus, errorThrown);
            },
            success: function (data, textStatus) {
                fn.success(data, textStatus);
            },
            beforeSend: function (XHR, obj) {
                var tokenBean = ax.getTokenBean();
                if (tokenBean && tokenBean.access_token) {
                    XHR.setRequestHeader("Authorization", tokenBean.token_type + " " + tokenBean.access_token);
                    XHR.setRequestHeader("currentUserId", jq.cookie(currentUserId));
                    console.log("set header with Authorization");
                }
                fn.beforeSend(XHR, obj);
            },
            complete: function (XHR, TS) {
            }
        });
        console.log(opt);
        return _ajax(_opt);
    };
    console.log("ajax extends");
}

function ax_ex_layui_jquery_ajax() {
    if (layui.$) {
        if (layui.$.cookie) {
            //已经有了，不再处理
        } else {
            //没有尝试赋予
            if (jQuery.cookie) {
                console.log("设置了cooke utils");
                layui.$.cookie = jQuery.cookie;
            }
        }
        ax_ex_jquery_ajax(layui.$);
        console.log("ax_ex_layui_jquery_ajax extends");
    }
}

if (window.jQuery && jQuery.fn.on) {
    console.log("jquery ajax 尝试扩展");
    ax_ex_jquery_ajax(jQuery);
}

function getUploadServerInfo(surl) {
    var info = {ip: "", port: "80", url: ""};
    if (surl.indexOf("http://") >= 0) {
        var arrUrl = surl.split("//");
        var start = arrUrl[1].indexOf("/");
        var t_url = arrUrl[1].substring(0, start);

        var index1 = t_url.indexOf(":");
        if (index1 < 0) {
            info.ip = t_url;
        } else {
            info.ip = t_url.substring(0, index1);
            info.port = t_url.substring(index1 + 1);
        }
        info.url = arrUrl[1].substring(start);
    } else {
        var rooturl = window.location.host;
        var index1 = rooturl.indexOf(":");
        if (index1 < 0) {
            info.ip = rooturl;
        } else {
            info.ip = rooturl.substring(0, index1);
            info.port = rooturl.substring(index1 + 1);
        }
        info.url = surl;
    }
    return info;
}

