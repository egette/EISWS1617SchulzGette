exports.updateUserdata = function(db){
	return function(req, res){
		console.log("BODY:", req.body);
		var typ = req.body.typ;
		var uid = req.body.uid;
		var biografie = req.body.Biografie;
		var wahlprogramm = req.body.wahlprogramm;
		var wahlkreis = req.body.wahlkreis;
		var email = req.body.email;
		var passwort = req.body.passwort;
		
		if (!uid || !typ) res.status(402).end();
		
		if(typ == 'kandidat'){
			db.get(uid, function (err, reply) {
				if (err) throw err;
				var kandidat = JSON.parse(reply);
				if(biografie) kandidat.Biografie = biografie;
				if(wahlprogramm) kandidat.Wahlprogramm = wahlprogramm;
				if(wahlkreis) kandidat.wahlkreis = wahlkreis;
				if(email) kandidat.email = email;
				db.set(uid, JSON.stringify(kandidat));
				res.status(200).end();
			});
		}
	}
}

exports.deleteUserdata = function(db){
	return function(req, res){
	
	}
}