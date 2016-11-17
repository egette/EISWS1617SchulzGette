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

//Setting up Routes
var register	= require('./routes/register');
var thesen  	= require('./routes/thesen');
var login       = require('./routes/login');
var userroute   = require('./routes/user');
var kandidatenroute = require('./routes/kandidaten');
var apiRoutes   =  express.Router();

//Routen ohne Login
app.post('/login', login.auth(app, db, redis, jwt));
app.post('/register', register.register(db, redis));
app.get('/thesen', thesen.getThesen(db));
app.get('/kandidaten', kandidatenroute.getKandidaten(db));

apiRoutes.use(function(req, res, next) {
  var token = req.body.token || req.query.token || req.headers['x-access-token'];
  if (token) {
    jwt.verify(token, app.get('superSecret'), function(err, decoded) {      
      if (err) {
        return res.json({ success: false, message: 'Failed to authenticate token.' });    
      } else {
        req.decoded = decoded;    
        next();
      }
    });
  } else {
	//kein Token
    return res.status(403).send({ 
        success: false, 
        message: 'No token provided.' 
    });
  }
});

//Routen mit Login
app.use('/', apiRoutes); 
app.post('/thesen', thesen.publish(db));
app.put('/thesen', thesen.putPosition(db));
app.put('/user', userroute.updateUserdata(db));
app.delete('/user', userroute.deleteUserdata(db));


app.listen(port,  function() {
  console.log(`Server running at http://${host}:${port}/`);
});
