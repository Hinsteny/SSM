/*!
 * app util js v0.0.1
 * https://github.com/Hinsteny/SSM
 * auther: Hinsteny
 * Date: 2016-07-07
 */

var app = app || {};

// --------- logoff ---------//
function logoff(){
    var path = "/logoff";
    $.get(path).done(function(response){
        if (response.success){
            window.location.reload(true);
        }
    });
}
// --------- /logoff ---------//

// --------- Tenant ---------//
var _tenant = null;
app.getCurrentTenantId = function(){
    var tenant = _tenant || {};
    return tenant.id;
}

app.getCurrentTenantHash = function(){
    return app.ctx.pathAt(0);
}

app.getCurrentTenant = function(){
    return $.extend({}, _tenant);
}

app.saveCurrentTenant = function(tenant){
    // should check if change first
    var isChange = false;
    if(!_tenant){
        if(tenant){
            isChange = true;
        }
    }else{
        if(!tenant){
            isChange = true;
        }else{
            if(Object.keys(_tenant).length != Object.keys(tenant).length){
                isChange = true;
            }else{
                for(var key in tenant){
                    if(tenant[key] != _tenant[key]){
                        isChange = true;
                        break;
                    }
                }

                //as so far, is still not change, should reverse check
                if(!isChange){
                    for(var key in _tenant){
                        if(tenant[key] != _tenant[key]){
                            isChange = true;
                            break;
                        }
                    }
                }
            }

        }
    }

    // then save tenant
    _tenant = tenant;

    app.getUserTenants().done(function(tenants){
        tenants = tenants || [];
        tenant = tenant || {};
        for(var i = 0; i < tenants.length; i++){
            if(tenant.id == tenants[i].id){
                tenants[i] = tenant;
                break;
            }
        }
        app.saveUserTenants(tenants);
        if(isChange){
            $(document).trigger("CURRENT_TENANT_CHANGE", tenant);
        }
    });
}

var _userTenants = null;
app.saveUserTenants = function(tenants){
    _userTenants = tenants;
}

app.getUserTenants = function(){
    var dfd = $.Deferred();
    dfd.resolve($.extend([], _userTenants));
    return dfd.promise();
}

app.getCurrentBaseTenantId = function(){
    var tenant = _tenant || {};
    return tenant.parentTenantId || tenant.id;
}

// --------- /Tenant ---------//

// --------- /timezone ---------//
var _timezoneConfigs = null;
app.saveTimezoneConfigs = function(timezoneConfigs){
    _timezoneConfigs = timezoneConfigs;
}

app.getTimezoneConfigs = function(){
    return $.extend({}, _timezoneConfigs);
}
// --------- /timezone ---------//

app.mixin = function(mixinObj, component, options) {
    var merge, obj = {};
    var eventNames = ['events', 'docEvents', 'winEvents', 'parentEvents', 'daoEvent'];
    $.extend(obj, mixinObj);
    $.each(component, function(key, val) {
        if ($.inArray(key, eventNames) >= 0) {
            if (key === "parentEvents") {
                merge = obj[key] = obj[key] || {};
                $.each(val, function(k, v) {
                    merge[k] = $.extend(merge[k] || {}, v);
                });

            } else {
                obj[key] = $.extend(obj[key] || {}, val);
            }
        } else {
            if(val && $.isFunction(val)){
                brite.inherit(val,mixinObj[key]);
            }
            obj[key] = val;
        }
    });
    return obj;
};

app.getDisplayName = function(user, style){
    var displayName = null;
    if(!user){
        return "";
    }
    if(user.firstName && user.lastName){
        if(user.mi){
            if(style == "noComma"){
                return displayName = user.firstName + " " + user.mi + " " + user.lastName;
            }else{
                return displayName = user.lastName + ", " + user.firstName + " " + user.mi + ".";
            }
        }else{
            if(style == "noComma"){
                return displayName = user.firstName + " " + user.lastName;
            }else{
                return displayName = user.lastName + ", " + user.firstName;
            }
        }
    }else if(user.lastName){
        return displayName = user.lastName;
    } else{
        return displayName = user.username;
    }
};

app.getFloorMapSize = function(ctnWidth, ctnHeight, width, height){
    var ratio = width / height;
    var conRatio = ctnWidth / ctnHeight;
    var tWidth = width;
    var tHeight = height;
    if(conRatio <= ratio){
        tWidth = ctnWidth;
        tHeight = ctnWidth / ratio;
    }else{
        tWidth = ctnHeight * ratio;
        tHeight = ctnHeight;
    }
    return {width: tWidth, height: tHeight};
};

app.fitFloorMap = function($imgCon, width, height){
    var $imgCtn = $imgCon.parent();
    var $img = $imgCon.find("img");
    var size = app.getFloorMapSize($imgCtn.width(), $imgCtn.height(), width, height);
    var marginTop = ($imgCtn.height() - size.height) / 2;
    $imgCon.width(size.width);
    $imgCon.height(size.height);
    if(marginTop > 0){
        $imgCon.css("marginTop", marginTop + "px");
    }else{
        $imgCon.css("marginTop", "0px");
    }
};

app.scaleImg = function($img){
    var $imgCtn = $img.parent();
    var preWidth = $imgCtn.outerWidth();
    var preHeight = $imgCtn.outerHeight();
    var oldImgWidth = $img.width();
    var oldImgHeight = $img.height();
    var towards = false;
    var length = null;
    var newImgWidth = null;
    var newImgHeight = null;

    if(oldImgWidth >= oldImgHeight){
        if(oldImgHeight > preHeight || oldImgWidth > preWidth){
            newImgHeight = preHeight;
            newImgWidth = oldImgWidth*(preHeight/oldImgHeight);
            length =  (newImgWidth - preWidth)/2;
            towards = true;
        }
    }else{
        if(oldImgWidth > preWidth || oldImgHeight > preHeight){
            newImgWidth = preWidth;
            newImgHeight = oldImgHeight*(preWidth/oldImgWidth);
            length =  (newImgHeight - preHeight)/2;
        }
    }

    $img.css({
        "width":newImgWidth,
        "height":newImgHeight,
        "position": "relative"
    });
    if(towards){
        $img.css({"right":length + "px"})
    }else{
        $img.css({"bottom":length + "px"})
    }
};


app.fitSearchInput = function($element){
    var $titleSearchCtn = $element.find(".title-search-ctn");
    if($titleSearchCtn.length == 0){
        return;
    }
    var $cacElement = $titleSearchCtn.find(".search-add-combine");
    if($cacElement.length == 0){
        $cacElement = $titleSearchCtn.find(".btn:last");
        if($cacElement.length == 0){
            $cacElement = $titleSearchCtn.find(".title");
        }
    }

    var height = $cacElement.outerHeight();
    $titleSearchCtn.height($cacElement.position().top + height);
    var top = parseInt($titleSearchCtn.css("top"));
    if(!top || isNaN(top)){
        top = 0;
    }
    $titleSearchCtn.next().css("top", (top + $titleSearchCtn.outerHeight()) + "px");
};

app.scrollToElement = function($onItem, $parent){
    if(!$parent){
        $parent = $("body");
    }
    if($onItem && $onItem.length > 0){
        $parent.scrollTop($onItem.offset().top - 32);
    }
}

app.scrollToErrorElement = function($container, $parent){
    var $item = null;
    if($container.find(".c-control.warning, .dropdown-field.warning").size() > 0){
        $item = $container.find(".c-control.warning, .dropdown-field.warning").first();
    }
    app.scrollToElement($item, $parent);
}

// check times
app.checkWithTimes = function(callback, totalTimes, times){
    if(!totalTimes){
        totalTimes = 5;
    }
    if(!times){
        times = 1;
    }

    var allDfd = $.Deferred();
    doEachTime(allDfd, callback, totalTimes, times);
    return allDfd.promise();
}

function doEachTime(allDfd, callback, totalTimes, times){
    setTimeout(function(){
        var returnVal = callback();
        times++;
        returnVal.fail(function(){
            if(times <= totalTimes){
                doEachTime(allDfd, callback, totalTimes, times);
            }else{
                allDfd.resolve(false);
            }
        }).done(function(){
            allDfd.resolve(true);
        });
    }, 1000);
}