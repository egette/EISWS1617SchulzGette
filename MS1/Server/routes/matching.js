exports.match = function(db, Promise){
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
			console.log("RESULT2", result);
			res.status(200).send(result).end();
		});	
	}
	
	function vergleichKandidaten(ThesenIDS, USERPOS){
		var _ = require('lodash');
		
		var result = {
			Kandidaten: [],
			Anzahl: 0
		};
	
		return Promise.map(ThesenIDS, function(ID){
			return Promise.resolve()
				.then(function(){
					return db.getAsync(ID);
				})
				.then(function(JSONSTRING) {
					var JSONOBJECT = JSON.parse(JSONSTRING);
					return JSONOBJECT;
				})
		})
		.then(function(ThesenJSONArray){
			var KandidatenIDS = [];
		    var KandidatenZaehler = [];
			var KategorieLokal = [];
			var KategorieUmwelt = [];
			var KategorieAussenpolitik = [];
			var KategorieSatire = [];
			for (i = 0; i < ThesenJSONArray.length; i++) {
				var thesenJSONOBJECT = ThesenJSONArray[i];
				var kategorie = thesenJSONOBJECT.kategorie;
				var tid = thesenJSONOBJECT.TID;
				var UPOS = USERPOS[i];
				for(j = 0; j < thesenJSONOBJECT.K_POSITION.length; j++){
					var KID = thesenJSONOBJECT.K_POSITION[j].UID;
					var KPOS = thesenJSONOBJECT.K_POSITION[j].POS;
					
					if (_.indexOf(KandidatenIDS, KID) == -1){
						KandidatenIDS.push(KID);
						var index = _.indexOf(KandidatenIDS, KID);
						if(KandidatenZaehler[index] == null){
							KandidatenZaehler[index] = 0;
							KategorieLokal[index]= 0;
							KategorieUmwelt[index]= 0;
							KategorieAussenpolitik[index]= 0;
							KategorieSatire[index]= 0;
						}
					} 
					var index = _.indexOf(KandidatenIDS, KID);
					if( UPOS == "PRO" && KPOS == "NEUTRAL"){
						KandidatenZaehler[index] += 1;
						if(kategorie=="Lokal") KategorieLokal[index] +=1;
						if(kategorie=="Umwelt") KategorieUmwelt[index] +=1;
						if(kategorie=="Aussenpolitik") KategorieAussenpolitik[index] +=1;
						if(kategorie=="Satire") KategorieSatire[index] +=1;
					}
					if( UPOS == "PRO" && KPOS == "CONTRA"){
						KandidatenZaehler[index] += 2;
						if(kategorie=="Lokal") KategorieLokal[index] +=2;
						if(kategorie=="Umwelt") KategorieUmwelt[index] +=2;
						if(kategorie=="Aussenpolitik") KategorieAussenpolitik[index] +=2;
						if(kategorie=="Satire") KategorieSatire[index] +=2;
					}
					if( UPOS == "NEUTRAL" && KPOS == "PRO"){
						KandidatenZaehler[index] += 1;
						if(kategorie=="Lokal") KategorieLokal[index] +=1;
						if(kategorie=="Umwelt") KategorieUmwelt[index] +=1;
						if(kategorie=="Aussenpolitik") KategorieAussenpolitik[index] +=1;
						if(kategorie=="Satire") KategorieSatire[index] +=1;
					}
					if( UPOS == "NEUTRAL" && KPOS == "CONTRA"){
						KandidatenZaehler[index] += 1;
						if(kategorie=="Lokal") KategorieLokal[index] +=1;
						if(kategorie=="Umwelt") KategorieUmwelt[index] +=1;
						if(kategorie=="Aussenpolitik") KategorieAussenpolitik[index] +=1;
						if(kategorie=="Satire") KategorieSatire[index] +=1;
					}
					if( UPOS == "CONTRA" && KPOS == "NEUTRAL"){
						KandidatenZaehler[index] += 1;
						if(kategorie=="Lokal") KategorieLokal[index] +=1;
						if(kategorie=="Umwelt") KategorieUmwelt[index] +=1;
						if(kategorie=="Aussenpolitik") KategorieAussenpolitik[index] +=1;
						if(kategorie=="Satire") KategorieSatire[index] +=1;
					}
					if( UPOS == "CONTRA" && KPOS == "PRO"){
						KandidatenZaehler[index] += 2;
						if(kategorie=="Lokal") KategorieLokal[index] +=2;
						if(kategorie=="Umwelt") KategorieUmwelt[index] +=2;
						if(kategorie=="Aussenpolitik") KategorieAussenpolitik[index] +=2;
						if(kategorie=="Satire") KategorieSatire[index] +=2;
					}
				}
			}
			
			for (k = 0; k < KandidatenIDS.length; k++){
				var KandidatenErgebnis = {
					KID: KandidatenIDS[k],
					Zaehler: KandidatenZaehler[k],
					Lokal: KategorieLokal[k],
					Umwelt: KategorieUmwelt[k],
					Aussenpolitik: KategorieAussenpolitik[k],
					Satire: KategorieSatire[k]
				};
				result.Kandidaten.push(KandidatenErgebnis);
			}
			return result;
		}).catch(function(err) {
          console.log("ERROR:", err);
        });
	};
};