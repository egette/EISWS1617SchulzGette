exports.match = function(db){
	return function(req, res){
		console.log('REQ BODY:  ', req.body);
		var ThesenIDS = [];
		var USERPOS = [];
		
		var Positionen = req.body.Positionen;
		for(i = 0; i < Positionen.length; i++){
			var tid = Positionen[i].TID;
			var userposition = Positionen[i].POSITION;
			ThesenIDS.push(tid);
			USERPOS.push(userposition);
		}
		vergleichKandidaten(ThesenIDS, USERPOS).then(function(result){
			var resultstring = result.toString();
			console.log("RESULT", resultstring);
			res.status(200).send(result).end();
		});	
	}
	
	function vergleichKandidaten(ThesenIDS, USERPOS){
		var _ = require('lodash');
		var KandidatenIDS = [];
		var KandidatenZaehler = [];
		var result = {
			"Kandidaten": [],
			Anzahl: 0
		};
		var promises = [];
		for (var i = 0; i < ThesenIDS.length; i++) {
			promises.push(db.getAsync(ThesenIDS[i]));
	    }
		return Promise.all(promises).then(function(arrayOfResults) {
			
			for (i = 0; i < arrayOfResults.length; i++) {
				//var thesenJSONOBJECT = JSON.parse(arrayOfResults[i]);
				var thesenJSONOBJECT = arrayOfResults[i];
				//console.log("THESE", thesenJSONOBJECT);
				var UPOS = USERPOS[i];
				for(i = 0; i < thesenJSONOBJECT.K_POSITION.length; i++){
					var KID = thesenJSONOBJECT.K_POSITION.UID;
					console.log("KID", KID);
					var KPOS = thesenJSONOBJECT.K_POSITION.POS;
					if (_.findIndex(KandidatenIDS, KID) == -1) KandidatenIDS.push(KID);
					if( UPOS == "PRO" && KPOS == "NEUTRAL"){
						var index = _.findIndex(KandidatenIDS, KID);
						KandidatenZaehler[index] += 1;
					}
					if( UPOS == "PRO" && KPOS == "CONTRA"){
						var index = _.findIndex(KandidatenIDS, KID);
						KandidatenZaehler[index] += 2;
					}
					if( UPOS == "NEUTRAL" && KPOS == "PRO"){
						var index = _.findIndex(KandidatenIDS, KID);
						KandidatenZaehler[index] += 1;
					}
					if( UPOS == "NEUTRAL" && KPOS == "CONTRA"){
						var index = _.findIndex(KandidatenIDS, KID);
						KandidatenZaehler[index] += 1;
					}
					if( UPOS == "CONTRA" && KPOS == "NEUTRAL"){
						var index = _.findIndex(KandidatenIDS, KID);
						KandidatenZaehler[index] += 1;
					}
					if( UPOS == "CONTRA" && KPOS == "PRO"){
						var index = _.findIndex(KandidatenIDS, KID);
						KandidatenZaehler[index] += 2;
					}
				}
			}
			
			for (i = 0; i < KandidatenIDS.length; i++){
				var KandidatenErgebnis = {
					KID: KandidatenIDS[i],
					Zaehler: KandidatenZaehler[i]
				};
				result.Kandidaten.push(KandidatenErgebnis);
			}
			return result;
		}).catch(function(err) {
          console.log("ERROR:", err);
        });
	};
};