/*!
 * app util js v0.0.1
 * https://github.com/Hinsteny/SSM
 * auther: Hinsteny
 * Date: 2016-07-07
 */

var app = app || {};

// --------- AJAX Wrapper --------- //
// Very simple AJAX wrapper that allow us to simply normalize request/response, and eventually put some hooks such as
// performance and error reporting.
(function(){
    app.doGet = function(path, data){
        return ajax(false,path,data);
    };

    app.doPost = function(path, data){
        return ajax(true,path,data);
    };

    app.ajax = function(path, data, isPost){
        return ajax(isPost,path,data);
    };

    app.ajaxFile = function(path, data){
        return ajax("POST",path,data, true);
    };

    function ajax(isPost, path, data, file){
        var dfd = $.Deferred();
        var method = (isPost)?"POST":"GET";
        data = data || {};

        // for current tenantId
        // data.currentTenantId = app.getCurrentTenantId();

        var opts = {
            url: path,
            type: method,
            dataType: "json",
            data: data
        };

        if(file){
            opts.contentType = false;
            opts.processData = false;
            opts.type = "POST";
            var formData = new FormData();
            data = data || {};
            for(var key in data){
                formData.append(key, data[key]);
            }
            opts.data = formData;
        }

        var jqXHR = $.ajax(opts);

        jqXHR.done(function(response){
            // TODO: need test reponse.result
            if (response.success){
                dfd.resolve(response.result);
            }else{
                dfd.reject(response);
            }
        });

        jqXHR.fail(function(jqx, textStatus, error){
            dfd.reject(jqx, textStatus, error);
            // TODO: need to normalize error
        });

        return dfd.promise();
    }

})();
// --------- /AJAX Wrapper --------- //

(function($){

    app.exportFile = function(url, params){
        params = params || {};
        var config = {method: 'post', url: url, params: params};
        var $iframe = $('<iframe id="down-file-iframe" />');
        var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
        $form.attr('action', config.url);
        for (var key in config.params) {
            $form.append('<input type="hidden" name="' + key + '" value=\'' + config.params[key] + '\' />');
        }
        $form.append('<input type="hidden" name="currentTenantId" value=\'' + app.getCurrentTenantId() + '\' />');

        $iframe.append($form);
        $(document.body).append($iframe);
        $form[0].submit();
        $iframe.remove();
    };

})(jQuery);

// --------- Key codes --------- //
(function(){
    app.keyCodes = {
        LEFT : 37,
        RIGHT : 39,
        UP : 38,
        DOWN : 40,
        TAB : 9,
        ESC : 27,
        ENTER : 13
    };
})();
// --------- /Key codes --------- //

(function ($){
    var check=false, isRelative=true;

    $.elementFromPoint = function(x,y){
        if(!document.elementFromPoint) return null;

        if(!check){
            var sl;
            if((sl = $(document).scrollTop()) >0){
                isRelative = (document.elementFromPoint(0, sl + $(window).height() -1) == null);
            }else if((sl = $(document).scrollLeft()) >0){
                isRelative = (document.elementFromPoint(sl + $(window).width() -1, 0) == null);
            }
            check = (sl>0);
        }

        if(!isRelative){
            x += $(document).scrollLeft();
            y += $(document).scrollTop();
        }

        return document.elementFromPoint(x,y);
    }

})(jQuery);

// example
