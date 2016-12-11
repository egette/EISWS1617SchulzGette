var constants = require('../constants/constants.json');

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

function getDB(data, db) {
	return db.getAsync(data).then(function (reply){
			if(!reply) return 0;
			return reply;
		});
 };
 
function makeToken(userID, db, reqPassword, app, res, jwt){
	getDB(userID, db).then(function(reply){ 
		var userDataJSON = JSON.parse(reply);
		console.log("user data json:  ", userDataJSON); 
		if(userDataJSON.password == reqPassword){
			var token = jwt.sign(userID, app.get('superSecret'));
			
			res.json({
			wahlkreis: userDataJSON.wahlkreis,
			userID: userID,
			sucess: true,
			token: token,
			username: userDataJSON.username
			}).status(200).end();
		}else{
			res.status(403).end();
		};
	});
};