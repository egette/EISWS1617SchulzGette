exports.getStatistik = function (db, redis) {
 	return function (req, res) {
		var wahlkreis = req.params.wahlkreis;
		if ( typeof wahlkreis  == 'undefined' ) {
			console.log(constants.error.msg_invalid_param.message);
			res.status(400).end();
			
        } else if ( !wahlkreis.trim()) {
			console.log(constants.error.msg_empty_param.message);
			res.status(400).end();
		}
		
		db.get(wahlkreis+"_anzahlwaehler", function (err, reply) {
			if (err) throw err;
			if(!reply){
				var anzahlwaehler = 0;
			}else{
				var anzahlwaehler = reply;
			}
			
			db.get(wahlkreis+"_anzahlkandidaten", function (err, reply2) {
				if (err) throw err;
				if(!reply2){
					var anzahlkandidaten = 0;
				}else{
					var anzahlkandidaten = reply2;
				}
				db.get(wahlkreis+"_Prognose", function (err, reply3) {
					if (err) throw err;
					if(!reply3){
						var prognose = [];
					}else{
						var prognose = JSON.parse(reply3);
					}
					var result = {
						AnzahlWaehler: anzahlwaehler,
						AnzahlKandidaten: anzahlkandidaten,
						Prognose: prognose
					};
					res.status(200).send(result).end();
					
				});
			});
		});
	};
};