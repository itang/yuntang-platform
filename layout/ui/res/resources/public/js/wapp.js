/**
 * wapp.js
 * 
 * require(moment.js)
 */

var _DATE_FORMAT = 'YYYY-MM-DD HH:mm:ss';
String.prototype.hackFormatDate = function() {
  return this.replace(/null/ig, '-').replace('Y', ' ').replace('undefined', ' ');
};
var wappjs = {};
wappjs.momentFormat = function() {
  return moment().format(_DATE_FORMAT).hackFormatDate();
};
