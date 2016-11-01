exports.auth = function(app, db, redis, session, RedisStore){
	return function(req, res){
		console.log('REQ BODY:  ', req.body);
		
		var emaildata = req.body.email;
		var username = req.body.username;
		var reqPassword = req.body.password;
		var userID = "";
		var userDataJSON;
		
		if(!emaildata && !username || !reqPassword ){
				res.status(400).end();
		}
			
		if (username != null || username != undefined) {
			getDB(username, db).then(function(reply){
				userID = reply;
				if(userID == 0) res.status(400).end();
				return userID;
			}).then(function(userID){
			 makeSession(userID, db, reqPassword, app, session, RedisStore, res);
			});
		}else if (emaildata != null || emaildata != undefined) {
			getDB(emaildata, db).then(function(reply){
				userID = reply;
				if(userID == 0) res.status(400).end();
				return userID;
			}).then(function(userID){
			 makeSession(userID, db, reqPassword, app, session, RedisStore, res);
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
 
function makeSession(userID, db, reqPassword, app, session, RedisStore, res){
	getDB(userID, db).then(function(reply){
		console.log("reply:  ", reply); 
		userDataJSON = JSON.parse(reply);
		console.log("user data json:  ", userDataJSON); 
		if(userDataJSON.password == reqPassword){
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
			res.status(200).end();
		}else{
			res.status(400).end();
		};
	});
};