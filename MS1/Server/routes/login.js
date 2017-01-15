var constants = require('../constants/constants.json');

//Funktion zum authentifiezieren eines Benutzers
exports.auth = function(app, db, redis, jwt){
	return function(req, res){
		console.log('REQ BODY:  ', req.body);
		
		var emaildata = req.body.email;
		var username = req.body.username;
		var reqPassword = req.body.password;
		var userID = "";

		
		if ( typeof emaildata  == 'undefined' && typeof username  == 'undefined' || typeof reqPassword  == 'undefined' ) {
			console.log(constants.error.msg_invalid_param.message);
			res.status(400).end();
			
        } else if ( !emaildata.trim()  && !username.trim() || !reqPassword.trim()) {
			console.log(constants.error.msg_empty_param.message);
			res.status(400).end();
 
        } else if (username) {
			getDB(username, db).then(function(reply){
				userID = reply;
				if(userID == 0) res.status(400).end();
				return userID;
			}).then(function(userID){
			 makeToken(userID, db, reqPassword, app, res, jwt);
			});
		}else if (emaildata) {
			getDB(emaildata, db).then(function(reply){
				userID = reply;
				if(userID == 0) res.status(400).end();
				return userID;
			}).then(function(userID){
			 makeToken(userID, db, reqPassword, app, res, jwt);
			});
		}	
	}
};
//Funktion um asynchron etwas aus der Datenbank zu holen
function getDB(data, db) {
	return db.getAsync(data).then(function (reply){
			if(!reply) return 0;
			return reply;
		});
 };
//Funktion zum Erzeugen eines Tokens  
function makeToken(userID, db, reqPassword, app, res, jwt){
	var argon2 = require('argon2');
	getDB(userID, db).then(function(reply){ 
		var userDataJSON = JSON.parse(reply);
		argon2.verify(userDataJSON.password, reqPassword).then(() => { 
			console.log('Successful password supplied!');
			var token = jwt.sign(userID, app.get('superSecret'));
			
			res.json({
			wahlkreis: userDataJSON.wahlkreis,
			userID: userID,
			sucess: true,
			token: token,
			username: userDataJSON.username
			}).status(200).end();
		}).catch(() => {
		  console.log('Invalid password supplied!');
		  res.status(403).end();
		});
	});
};