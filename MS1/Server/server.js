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

//Setting up Routes
var register	= require('./routes/register');
var thesen  	= require('./routes/thesen');
var login       = require('./routes/login');


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

app.post('/register', register.register(db, redis));
app.post('/thesen', thesen.publish(db));
app.get('/thesen', thesen.getThesen(db));
app.put('/thesen', thesen.putBegruendung(db));
app.post('/login', login.auth(app, db, redis, jwt));




app.listen(port,  function() {
  console.log(`Server running at http://${host}:${port}/`);
});
