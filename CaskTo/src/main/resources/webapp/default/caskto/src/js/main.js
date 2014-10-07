/**
 * Js for cask.to main page.
 */

var Homepage = function () {
  this.init(arguments);
};

Homepage.prototype.init  = function () {
  var self = this;

  // Check if this is a redirect request
  var queryValue = getUrlQuery();
  if (queryValue != null) {
    // Redirect request
    this.performRedirect(queryValue);
    return;
  }

  $("#wrapper").show();
  $("#url-submit-form").submit(function(e) {
    e.preventDefault();
    self.submitUrlToShorten();
  });

  // Not a redirect, render page based on hash value
  this.renderPage();
  var self = this;
  window.onhashchange = function () {
    var queryValue = getUrlQuery();
    if (queryValue != null) {
      // Redirect request
      self.performRedirect(queryValue);
      return;
    }
    self.renderPage();
  };
};

Homepage.prototype.renderPage = function () {
  var hashValue = getHashValue();
  if (hashValue == 'shorten') {
    this.loadShorten();
  } else if (hashValue == 'list') {
    this.loadList();
  } else {
    alert("Unknown page '" + hashValue + "'");
  }
};

Homepage.prototype.performRedirect = function (shortname) {
  // Redirect request
  $("#page-wrapper-shorten").hide();
  this.resolveUrl(shortname);
};

Homepage.prototype.loadShorten = function () {
  $(".page-wrapper").hide();
  this.highlightTab("shorten");
  $("#page-wrapper-shorten").show();
  this.enableIntervals();
}

Homepage.prototype.loadList = function () {
  $(".page-wrapper").hide();
  this.highlightTab("list");
  $("#page-wrapper-list").show();
  this.disableIntervals();
  this.loadShortenedUrls();
};

Homepage.prototype.highlightTab = function (hashValue) {
  $(".top-link").parent().removeClass('active');
  $('#' + hashValue + '-url-link').parent().addClass('active');
};

Homepage.prototype.resolveUrl = function(queryParam) {
  $.ajax({
    url: '/v2/apps/caskto/procedures/url-redirector/methods/resolve',
    type: 'POST',
    dataType: 'json',
    contentType: 'application/json',
    data: JSON.stringify({'shortname':queryParam}),
    success: function(data) {
      if (typeof data.error === 'undefined') {
        alert('Unknown error occurred: ' + data)
      } else if (data.error) {
        alert('Error has occurred: ' + data.msg)
      } else {
        window.location = data.url;
      }
    }
  });
};

function validateUrl(url) {
  var expression = /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/g
  var regex = new RegExp(expression);
  return url.match(regex);
};

Homepage.prototype.submitUrlToShorten = function() {
  var injectText = $("#url-textarea").val();
  if (!validateUrl(injectText)) {
    alert("Invalid URL (" + injectText + ") [" + validateUrl(injectText) + "]");
    return;
  }
  $.ajax({
    url: '/v2/apps/caskto/procedures/url-manager/methods/create',
    type: 'POST',
    dataType: 'json',
    contentType: 'application/json',
    data: JSON.stringify({'url':injectText}),
    success: function(data) {
      if (typeof data.error === 'undefined') {
        alert('Unknown error occurred: ' + data)
      } else if (data.error) {
        alert('Error has occurred: ' + data.msg)
      } else {
        $("#shorten-heading").text(data.msg);
        var shortUrl = getUrlWithoutHashValue() + '?' + data.shortname;
        $("#shorten-text").html('<a href="' + shortUrl + '">' + shortUrl + '</a>')
      }
      $("#url-textarea").val('');
    }
  });
};

Homepage.prototype.loadShortenedUrls = function() {
  $.ajax({
    url: '/v2/apps/caskto/procedures/url-manager/methods/list',
    type: 'POST',
    dataType: 'json',
    contentType: 'application/json',
    success: function(data) {
      if (typeof data.error === 'undefined') {
        alert('Unknown error occurred: ' + data)
      } else if (data.error) {
        alert('Error has occurred: ' + data.msg)
      } else {
        var urlHtml = "";
        var shortUrlPrefix = "" + getUrlWithoutHashValue();
        for (var i=0; i<data.urls.length; i++) {
          var urlInfo = data.urls[i];
          shortUrl = shortUrlPrefix + '?' + urlInfo.shortname;
          urlHtml += urlInfo.url + ' shortened to <a href="' + shortUrl + '">' + shortUrl + '</a><br />';
        }
        $("#url-list").html(urlHtml);
      }
    }
  });
};

Homepage.prototype.disableIntervals = function () {
  clearInterval(this.interval);
};

Homepage.prototype.enableIntervals = function () {
  var self = this;
  this.interval = setInterval(function() {
    $.ajax({
      url: '/v2/apps/caskto/procedures/analytics/methods/stats',
      type: 'POST',
      dataType: 'json',
      contentType: "application/json",
      cache: false,
      data: JSON.stringify({}),
      success: function(data) {
        if (!data.shortened) {
            data.shortened = 0;
        }
        if (!data.redirected) {
            data.redirected= 0;
        }
        $("#urls-shortened").text(data.shortened);
        $("#urls-redirected").text(data.redirected);
      }
    });
  }, 1000);
};


function getUrlQuery() {
  var fullUrl = document.location.href;
  var queryStringSplit = fullUrl.split('?');
  if (queryStringSplit.length == 1) {
    return null;
  }
  var queryString = queryStringSplit[1];
  if (queryString.charAt(queryString.length - 1) == '/') {
    queryString = queryString.substring(0, queryString.length - 1);
  }
  return queryString;
};

function getHashValue() {
  var fullUrl = document.location.href;
  var hashValue = document.location.hash;
  if (!hashValue) {
    document.location.href = fullUrl + '#shorten';
    return "shorten";
  }
  return hashValue.slice(1);
}

function getUrlWithoutHashValue() {
  var fullUrl = document.location.href;
  return fullUrl.split("#")[0];
}
  

$(document).ready(function() {
  new Homepage();
});

