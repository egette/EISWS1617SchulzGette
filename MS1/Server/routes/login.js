exports.auth = function(app, db, redis, jwt){
	return function(req, res){
		console.log('REQ BODY:  ', req.body);
		
		var emaildata = req.body.email;
		var username = req.body.username;
		var reqPassword = req.body.password;
		var userID = "";

		
		if(!emaildata && !username || !reqPassword ){
				res.status(400).end();
		}
			
		if (username != null || username != undefined) {
			getDB(username, db).then(function(reply){
				userID = reply;
				if(userID == 0) res.status(400).end();
				return userID;
			}).then(function(userID){
			 makeToken(userID, db, reqPassword, app, res, jwt);
			});
		}else if (emaildata != null || emaildata != undefined) {
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
		console.log("reply:  ", reply); 
		var userDataJSON = JSON.parse(reply);
		console.log("user data json:  ", userDataJSON); 
		if(userDataJSON.password == reqPassword){
			var token = jwt.sign(userID, app.get('superSecret'));
			
			res.json({
			userID : userID,
			sucess: true,
			token: token
			}).status(200).end();
		}else{
			res.status(400).end();
		};
	});
};