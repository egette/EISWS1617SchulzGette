var constants = require('../constants/constants.json');
var registerFunction = require('../functions/register');    
var devicesFunction = require('../functions/devices');
var deleteFunction = require('../functions/delete');
var sendFunction = require('../functions/send-message');

//Setting up Routes
var register	= require('./register');
var thesen  	= require('./thesen');
var login       = require('./login');
var userroute   = require('./user');
var kandidatenroute = require('./kandidaten');
var matching	= require('./matching');



module.exports = function(app,io, db, redis, jwt, Promise, apiRoutes) {
 
    io.on('connection', function(socket){
 
        console.log("Client Connected");
        socket.emit('update', { message: 'Hello Client',update:false });
 
          socket.on('update', function(msg){
 
            console.log(msg);
          });
    });
 
	//Routen ohne Login
	app.post('/login', login.auth(app, db, redis, jwt));
	app.post('/register', register.register(db, redis));
	app.get('/thesen', thesen.getThesen(db));
	app.get('/thesen/:tid', thesen.getThese(db));
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
	//app.use('/', apiRoutes);
	app.post('/matching', matching.match(db, Promise)); 
	app.post('/thesen', thesen.publish(db));
	app.put('/thesen', thesen.putPosition(db));
	app.put('/thesen/kommentar', thesen.postKommentar(db));
	app.put('/thesen/likes', thesen.putLikes(db));
	app.put('/user', userroute.updateUserdata(db));
	app.delete('/user', userroute.deleteUserdata(db));
 
    app.post('/devices',function(req,res) {
		console.log("REGISTERBODY:", req.body);
		var wahlkreis = req.body.wahlkreis;
        var uid = req.body.uid;
        var registrationId = req.body.registrationId;
 
        if ( typeof uid  == 'undefined' || typeof registrationId  == 'undefined' || typeof wahlkreis  == 'undefined' ) {
			console.log(constants.error.msg_invalid_param.message);
			res.json(constants.error.msg_invalid_param);
 
        } else if ( !uid.trim()  || !registrationId.trim() || !wahlkreis.trim()) {
			console.log(constants.error.msg_empty_param.message);
			res.json(constants.error.msg_empty_param);
 
        } else {
				registerFunction.register( uid, registrationId, wahlkreis, db, function(result) {
				res.json(result).end();
				if (result.result != 'error'){
					io.emit('update', { message: 'New Device Added',update:true});
				}
            });
        }
    });
 
 
    app.delete('/devices/:device',function(req,res) {
 
        var registrationId = req.params.device;
		var wahlkreis = req.body.wahlkreis;
		deleteFunction.removeDevice(registrationId, wahlkreis, db,function(result) {
		res.json(result).end();
 
        });
 
    });
}