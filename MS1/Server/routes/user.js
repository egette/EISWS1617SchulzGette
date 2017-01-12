var devicesFunction = require('../functions/devices');
var sendFunction = require('../functions/send-message');
var constants = require('../constants/constants.json');

//Funktion um die Daten des Benutzers zu aktuallisieren
exports.updateUserdata = function(db){
	return function(req, res){
		console.log("BODY:", req.body);
		var typ = req.body.typ;
		var uid = req.body.uid;
		var biografie = req.body.Biografie;
		var wahlprogramm = req.body.Wahlprogramm;
		var wahlkreis = req.body.wahlkreis;
		var email = req.body.email;
		var passwort = req.body.passwort;
		
		if ( typeof typ  == 'undefined' && typeof uid  == 'undefined' || typeof passwort  == 'undefined' ) {
			console.log(constants.error.msg_invalid_param.message);
			res.status(400).end();
			
        } else if ( !typ.trim()  && !uid.trim() || !passwort.trim()) {
			console.log(constants.error.msg_empty_param.message);
			res.status(400).end();
 
        } else if (typ == 'kandidat'){
			db.get(uid, function (err, reply) {
				if (err){
					throw err;
					res.status(400).end();
				}else{
					var kandidat = JSON.parse(reply);
					if(passwort == kandidat.password){
						if(biografie) kandidat.Biografie = biografie;
						if(wahlprogramm) kandidat.Wahlprogramm = wahlprogramm;
						if(wahlkreis) kandidat.wahlkreis = wahlkreis;
						//if(email) kandidat.email = email;
						db.set(uid, JSON.stringify(kandidat));
						res.status(200).end();
						if(!wahlkreis) wahlkreis = kandidat.wahlkreis;
						devicesFunction.listDevices(wahlkreis, db,  function(result) {
							sendFunction.sendMessage(uid, result, function (callback){
								console.log(callback);
							});
						});
					}else{
					//falsches passwort
					res.status(403).end();
					}
				}
			});
		}else if(typ == 'waehler'){
		//TODO
		}else{
		res.status(400).end();
		}
	}
}

exports.deleteUserdata = function(db){
	return function(req, res){
	//TODO
	
	}
}