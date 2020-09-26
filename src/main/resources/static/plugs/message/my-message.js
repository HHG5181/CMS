/*
* @Author: 小明
* @Date:   2019-12-30 16:16:41
* @Last Modified by:   根子科技
* @Last Modified time: 2020-01-12 17:10:18
*/
(function (define) {
    define(['jquery'], function ($) {
	return (function() {
		var messageContainer = null;
		var top = 10;
		/*默认参数*/
		var _DEFAULTS = {
			iconFontSize: "20px", //图标大小
			messageFontSize: "12px", //信息字体大小
			showTime:1500, //消失时间
			align: "center", //显示的位置
			positions: { //放置信息的范围
				top: "40px",
				bottom: "10px",
				right: 10,
				left: 10
			},
			message: "这是一条消息", //消息内容
			type: "normal", //消息的类型，还有success,error,warning等
		}

		var _msg = {
			add:add
		}
		var domStr = "<div class='m-message' style='top:" +
				_DEFAULTS.positions.top +
				";right:" +
				_DEFAULTS.positions.right +
				"px;left:" +
				_DEFAULTS.positions.left +
				"px;width:calc(100%-" +
				(_DEFAULTS.positions.right +
				_DEFAULTS.positions.left )+
				"px);bottom:" + _DEFAULTS.positions.bottom +
				"'></div>";
			messageContainer = $(domStr);
			messageContainer.appendTo($('body'))
		return _msg;
		function add(message, type,time,callback) {
            var type= type||'normal';
            var callback= callback||null;
            var time= time||0;
			var domStr = "";
			type = type || _DEFAULTS.type;
			domStr += "<div class='m-message-notice' style='" +
				"text-align:" +
				_DEFAULTS.align +
				";'>";
			switch(type) {
				case "normal":
					domStr += "<div class='message rt-text-blue'><div class='icon'><i class='rt-icon icon-tishi";
					break;
				case "success":
					domStr += "<div class='message rt-text-green'><div class='icon'><i class='rt-icon icon-success";
					break;
				case "error":
					domStr += "<div class='message rt-text-red'><div class='icon'><i class='rt-icon icon-error";
					break;
				case "warning":
					domStr += "<div class='message rt-text-blue'><div class='icon'><i class='fa fa-comments";
					break;
				default:
					throw "提升";
					break;
			}
			domStr += "' style='font-size:" +
				_DEFAULTS.iconFontSize +
				";'></i></div><div class='msg-content' style='font-size:" +
				_DEFAULTS.messageFontSize +
				";'>" + message + "</div></div></div>";
			var $domStr = $(domStr).appendTo(messageContainer);
			if(isString(callback) && callback != null && callback != ''){
				setTimeout(function(){
					switch(callback){
						case 'back':
							history.back(-1);
						break;
						case 'noback':

						break;
						default:
							window.location.href = callback;
						break;
					}
				},time);
			}else if(callback != null&& callback != ''){
				setTimeout(callback,1500);
            }
			_hide($domStr);
		}
		/**
		 * 隐藏消息
		 * $domStr：该消息的jq对象
		 * */
		function _hide($domStr) {
			setTimeout(function() {
				$domStr.fadeOut(1500);
   				$domStr.remove();
			},3000);
		}
		function isString(str){
			return (typeof str=='string')&&str.constructor==String;
		}
	})(); 
});
}(typeof define === 'function' && define.amd ? define : function (deps, factory) {
    if (typeof module !== 'undefined' && module.exports) { //Node
        module.exports = factory(require('jquery'));
    }
    else if (window.layui && layui.define){
        layui.define('jquery', function (exports) { //layui加载
            
            exports('_msg', factory(layui.jquery));
        });
    }
    else {
        window._msg = factory(window.jQuery);
    }
}));