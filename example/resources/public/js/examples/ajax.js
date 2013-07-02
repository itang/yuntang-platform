/**
 * 
 * require("jquery", "lodash");
 */

function ajaxStart(options) {
  options.data = {
    age : $('#age').val()
  };
  $('#ajax_start').show().html("Request: " + JSON.stringify(options));
}

function showResult(html, ok) {
  $r = $('#ajax_result');
  $r.show().html("Response: " + html);
  if (!ok) {
    $r.removeClass('alert-success').addClass('alert-error');
  } else {
    $r.removeClass('alert-error').addClass('alert-success');
  }
}

function withResult(/* Object */result, /* String */textStatus, /* jqXHR */
jqXHR) {
  showResult(JSON.stringify(result), result.success);
  if (result.success) {
    $('#age').val(result.data.age);
  }
}

var options = {
  url : '/examples/ajax',
  type : 'get',
  dataType : 'json',
  success : withResult,
  error : function( /* jqXHR */jqXHR, /* String */textStatus, /* String */
  errorThrown) {
    try {
      var result = JSON.parse(jqXHR.responseText);
      alert(result.message + ": " + result.detailMessage);
    } catch (e) {
    }
    showResult(JSON.stringify({
      jqXHR : jqXHR,
      textStatus : textStatus,
      errorThrown : errorThrown
    }), false);
  }
};

function doAjax() {
  ajaxStart(options);
  $.ajax(options);
}

function doAjaxPOST() {
  var postOptions = _.clone(options);// jQuery.extend(false, {}, options)
  postOptions.type = "post";
  ajaxStart(postOptions);
  $.ajax(postOptions);
}

function doAjaxJSON() {
  ajaxStart(options);
  $.getJSON('/examples/ajax', {
    age : $('#age').val()
  }, withResult);
}

$(function() {
  var options = {
    dataType: "json",
    success: function(result){
      alert("来自服务器端:" + JSON.stringify(result));
    }
  };
  $('#ajaxform').ajaxForm(options);
  $('#btn-ajaxsubmit').click(function(){
    $('#ajaxsubmit').ajaxSubmit(options);
  });
  
  $('#ajaxfileupload').ajaxForm(options);
});
