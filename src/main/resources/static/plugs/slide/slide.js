/**
 * Created by Alone on 2017/11/6.
 */
var box=document.getElementById('verify_code_box');
var xbox=document.getElementById('verify_code_xbox');
var element=document.getElementById('verify_code_btn');
var b=box.offsetWidth;
var o=element.offsetWidth;
let verifyStatus =false;
element.ondragstart = function() {
    return false;
};
element.onselectstart = function() {
    return false;
};
element.onmousedown = function(e) {
    var disX = e.clientX - element.offsetLeft;
    document.onmousemove = function (e) {
        var l = e.clientX - disX +o;
        if(l<o){
            l=o
        }
        if(l>b){
            l=b
        }
        xbox.style.width = l + 'px';
    };
    document.onmouseup = function (e){
        var l = e.clientX - disX +o;
        if(l<b){
            l=o
        }else{
            l=b;
            xbox.innerHTML='验证通过<div id="verify_code_btn"><i class="rt-icon icon-yes"></i></div>';
            verifyStatus = true;
        }
        xbox.style.width = l + 'px';
        document.onmousemove = null;
        document.onmouseup = null;
    };
}