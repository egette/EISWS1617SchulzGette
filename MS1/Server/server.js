//Module
var http        = require('http'),
    https       = require('https'), 
    express 	= require('express'),
    bodyParser 	= require('body-parser'),
	jsonParser  = bodyParser.json(),
    redis 		= require('redis'),
	db          = redis.createClient(),
    app         = express(),
	session = require('express-session'),
	RedisStore  = require('connect-redis')(session),
    Promise    = require('bluebird');
	Promise.promisifyAll(redis.RedisClient.prototype);
    Promise.promisifyAll(redis.Multi.prototype);
	


var optionsStore = {
client: db,
};

app.use( session({
   store: new RedisStore(optionsStore),
   secret : 's3Cur3',
   name : 'sessionId',
   resave: false,
   saveUninitialized: true,
   cookie: { secure: false} // noch kein HTTPS implementiert
   
  })
);



const host = '127.0.0.1';
const port = 3000;
app.use(bodyParser.json());
app.disable('x-powered-by');

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

app.post('/register', register.register(db));
app.post('/thesen', thesen.publish(db));
app.get('/thesen', thesen.getThesen(db));
app.put('/thesen', thesen.putBegruendung(db));




app.listen(port,  function() {
  console.log(`Server running at http://${host}:${port}/`);
});
