
$(function () {

    window.$body = $('body');

    /*! 后台加密登录处理 */
    $body.find('[data-login-form]').map(function (that) {
        that = this;
        require(["md5"], function (md5) {
            $("form").vali(function (data) {
                $.form.load(location.href, data, "post", function (ret) {
                    if (parseInt(ret.code) !== 1) {
                        console.log($(that).find('[data-captcha]'));
                        $(that).find('.verify.layui-hide').removeClass('layui-hide');
                        $(that).find('[data-captcha]').trigger('click');
                    }
                }, null, null);
            });
        });
    });

    /*! 登录图形验证码刷新 */
    $body.on('click', '[data-captcha]', function () {
        var $that = $(this), $form = $that.parents('form');
        var action = this.getAttribute('data-captcha') || location.href;
        if (action.length < 5) return $.msg.tips('请设置验证码请求及验证地址');
        var type = this.getAttribute('data-captcha-type') || 'captcha-type';
        var token = this.getAttribute('data-captcha-token') || 'captcha-token';
        var uniqid = this.getAttribute('data-field-uniqid') || 'captcha-uniqid';
        var verify = this.getAttribute('data-field-verify') || 'captcha-verify';
        $.form.load(action, {type: type, token: token}, 'post', function (ret) {
            // if (ret.code) {
                $that.html('<img alt="img" src="' + ret.data.image + '"><input type="hidden">').find('input').attr('name', uniqid).val(ret.data.uniqid || '');
                $form.find('[name="' + verify + '"]').attr('value', ret.data.code || '').val(ret.data.code || '');
                return (ret.data.code || $form.find('.verify.layui-hide').removeClass('layui-hide')), false;
            // }
        }, false);
    });

    /*! 初始化登录图形 */
    $('[data-captcha]').map(function () {
        $(this).trigger('click')
    });

});