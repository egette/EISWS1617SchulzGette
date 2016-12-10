//Module
var http        = require('http'),
	https       = require('https');
const   express 	= require('express');
const   bodyParser 	= require('body-parser');
const	jsonParser  = bodyParser.json();
const   redis 		= require('redis');
const	db          = redis.createClient();
const   jwt         = require('jsonwebtoken');
const	app         = express();
var io = require('socket.io');
var apiRoutes   =  express.Router();

const host = '127.0.0.1';
const port = 3000;

var	Promise    = require('bluebird');
	Promise.promisifyAll(redis.RedisClient.prototype);
    Promise.promisifyAll(redis.Multi.prototype);
	Promise.promisifyAll(redis);
	Promise.promisifyAll(db);
var config = require('./config');

app.use(bodyParser.json());
app.disable('x-powered-by');
app.set('superSecret', config.secret);


var env = process.env.NODE_ENV || 'development';
if ('development' == env) {
   app.use(express.static(__dirname + '/public'));

	app.use(function(err, req, res, next) {
		console.error(err.stack);
		res.end(err.status + ' ' + err.messages);
	});
}

//Connecting to redis Server
db.on('connect', function() {
    console.log('connected to redis');
});

db.on("error", function (err) {
    console.log('Error ' + err);
});

//Send server log with time stamp
app.use(function (req, res, next) {
	console.log('Time: %d ' + ' Request-Pfad: ' + req.path, Date.now());
	next();
});

var listen = app.listen(port,  function() {
  console.log(`Server running at http://${host}:${port}/`);
});
var socket = io.listen(listen);

require('./routes/routes')(app, socket, db, redis, jwt, Promise, apiRoutes);



