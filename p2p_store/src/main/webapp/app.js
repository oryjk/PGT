/**
 * Created by supersoup on 16/1/10.
 */
var express = require('express');
var http = require('http');

var app = express();

app.set('port', 8888);

app.use(express.static(__dirname + '/src'));

http.createServer(app).listen(app.get('port'), function () {
    console.log('begin!');
});