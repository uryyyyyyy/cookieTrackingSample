
var key = "forTrackingCookieKey";
var trackingID = $.cookie(key);

if(!trackingID){
    var randomNumber = Math.random();
    $.cookie(key, randomNumber, { expires: 7 });
    trackingID = randomNumber;
}

var url = "http://192.168.11.2:8080/tracking?trackingID=" + trackingID;

$("body").append('<img src=' + url + '>');
