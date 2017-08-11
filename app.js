'use strict';
var express = require('express');
var cfenv = require('cfenv');
var appEnv = cfenv.getAppEnv();
var app = express();

// enable https
//app.enable('trust proxy');
/*app.use(function (req, res, next) {
  if (req.secure) {
    next();
  } else {
    res.redirect('https://' + req.headers.host + req.url);
  }
});*/
app.use(express.static(__dirname + '/www'));
app.listen(appEnv.port, function () {
  console.log('listening on: %s', appEnv.url);
})