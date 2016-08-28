
var key = "forTrackingCookieKey";
var trackingID = $.cookie(key);

if(!trackingID){
    var randomNumber = Math.random();
    $.cookie(key, randomNumber, { expires: 7 });
    trackingID = randomNumber;
}

var url = "http://localhost:8080/tracking?trackingID=" + trackingID;

$("body").append('<img src=' + url + '>');
