exports.match = function(db, Promise){
	return function(req, res){
		console.log('REQ BODY:  ', req.body);
		var ThesenIDS = [];
		var USERPOS = [];
		var GESENDET = [];
		var LASTUSERPOS = [];
		var UID = req.body.UID;
		var wahlkreis = req.body.wahlkreis;
		
		//Das JSONOBJECT einer Position wird auf vier Arrays aufgeteilt
		var Positionen = req.body.Positionen;
		for(i = 0; i < Positionen.length; i++){
			var tid = Positionen[i].TID;
			var userposition = Positionen[i].POSITION;
			var gesendet = Positionen[i].GESENDET;
			var lastuserpos = Positionen[i].LASTPOSITION;
			ThesenIDS.push(tid);
			USERPOS.push(userposition);
			GESENDET.push(gesendet);
			LASTUSERPOS.push(lastuserpos);
		}
		vergleichKandidaten(ThesenIDS, USERPOS, GESENDET, LASTUSERPOS, UID, wahlkreis).then(function(result){
			console.log("RESULT2", result);
			res.status(200).send(result).end();
		});	
	}
	
	function vergleichKandidaten(ThesenIDS, USERPOS, GESENDET, LASTUSERPOS, UID, wahlkreis){
		var _ = require('lodash');
		
		var result = {
			Kandidaten: [],
			Anzahl: 0
		};
	
		//Jede ThesenID wird Asynchron aus der Datenbank geholt
		return Promise.map(ThesenIDS, function(ID){
			return Promise.resolve()
				.then(function(){
					return db.getAsync(ID);
				})
				.then(function(JSONSTRING) {
					//und zurück zu einem JSONOBJECT geparsed
					var JSONOBJECT = JSON.parse(JSONSTRING);
					return JSONOBJECT;
				})
		})
		.then(function(ThesenJSONArray){
			var KandidatenIDS = [];
			//Die Arrays für die Zähler der Punkte
		    var KandidatenZaehler = [];
			var KategorieLokal = [];
			var KategorieUmwelt = [];
			var KategorieAussenpolitik = [];
			var KategorieSatire = [];
			var AnzahlPositionen = [];
			var AnzahlLokal = [];
			var AnzahlUmwelt = [];
			var AnzahlAussenpolitik= [];
			var AnzahlSatire = [];
			//Das ThesenJSONArray wird durchiteriert 
			for (i = 0; i < ThesenJSONArray.length; i++) {
				var thesenJSONOBJECT = ThesenJSONArray[i];
				var kategorie = thesenJSONOBJECT.kategorie;
				var tid = thesenJSONOBJECT.TID;
				var UPOS = USERPOS[i];
				var gesendet2 = GESENDET[i];
				var lastUSERPOS2 = LASTUSERPOS[i];
				//Überprüfung ob sich die Position geändert hat oder ob diese Position schon erfasst wurde
				if(UPOS != lastUSERPOS2 && gesendet2 == "true" || gesendet2 == "false" ){
					addUserPositionToThese(UPOS, tid, gesendet2, lastUSERPOS2);
				}
				//Es werden alle Positionen der Kandidaten zur jeweiligen These durchlaufen
				for(j = 0; j < thesenJSONOBJECT.K_POSITION.length; j++){
					var KID = thesenJSONOBJECT.K_POSITION[j].UID;
					var KPOS = thesenJSONOBJECT.K_POSITION[j].POS;
					
					//Überprüfung ob die KID schon im KandidatenIDS Array ist
					if (_.indexOf(KandidatenIDS, KID) == -1){
						//falls nicht wird die KID hinzugefügt
						KandidatenIDS.push(KID);
						var index = _.indexOf(KandidatenIDS, KID);
						//und die Zaehler an der Stelle des Index auf 0 gesetzt
						if(KandidatenZaehler[index] == null){
							KandidatenZaehler[index] = 0;
							KategorieLokal[index]= 0;
							KategorieUmwelt[index]= 0;
							KategorieAussenpolitik[index]= 0;
							KategorieSatire[index]= 0;
							AnzahlPositionen[index] = 0;
							AnzahlLokal[index] = 0;
							AnzahlUmwelt[index]  = 0;
							AnzahlAussenpolitik[index]  = 0;
							AnzahlSatire[index]  = 0;
						}
					} 
					var index = _.indexOf(KandidatenIDS, KID);
					if(KPOS == "NEUTRAL" || KPOS == "CONTRA" || KPOS == "PRO"){
						AnzahlPositionen[index] += 1;
						if(kategorie=="Lokal") AnzahlLokal[index] +=1;
						if(kategorie=="Umwelt") AnzahlUmwelt[index] +=1;
						if(kategorie=="Aussenpolitik") AnzahlAussenpolitik[index] +=1;
						if(kategorie=="Satire") AnzahlSatire[index] +=1;
					}
					//Vergleich zwischen der Position des Wählers und des Kandidaten
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
			
			//Für jeden Kandidaten wird ein JSONOBJECT mit seinen Zählern angelegt
			for (k = 0; k < KandidatenIDS.length; k++){
				var KandidatenErgebnis = {
					KID: KandidatenIDS[k],
					Zaehler: KandidatenZaehler[k],
					Lokal: KategorieLokal[k],
					Umwelt: KategorieUmwelt[k],
					Aussenpolitik: KategorieAussenpolitik[k],
					Satire: KategorieSatire[k],
					AnzahlPOS: AnzahlPositionen[k],
					AnzahlLokal: AnzahlLokal[k],
					AnzahlUmwelt: AnzahlUmwelt[k],
					AnzahlAussenpolitik: AnzahlAussenpolitik[k],
					AnzahlSatire: AnzahlSatire[k]
				};
				result.Kandidaten.push(KandidatenErgebnis);
				result.Anzahl = k+1;
			}
			var sortedResult = _.sortBy(result.Kandidaten, ['Zaehler', 'KID']);
			//Prognose wird überarbeitet, falls UID vorhanden
			if(UID != null){
				updatePrognose(wahlkreis, UID, sortedResult, _);
			}
			result.Kandidaten = sortedResult;
			return result;
		}).catch(function(err) {
          console.log("ERROR:", err);
        });
	};
	
	function updatePrognose(wahlkreis, UID, sortedResult, _){
		db.get(wahlkreis+"_Prognose", function(err, reply){
			var prognose = [];
			if(!reply){
				//Erste Prognose wird erstellt
				for (u=0; u<sortedResult.length; u++){
					var kandidatP={
						KID: sortedResult[u].KID,
						Platz: [0,0,0,0,0,0,0,0,0,0,0]
					};
					if(u<11) kandidatP.Platz[u+1] += 1;
					prognose.push(kandidatP);
				}
			}else{
				//Prognose aus der DB
				prognose = JSON.parse(reply);
			}
			console.log("PrognoseALT", prognose);
			//altes Matching Ergebnis des Nutzers
			db.get(UID+"_matchingResult", function(err, reply){
				if(err) throw err;
				if(!reply){
					var oldResult = null;
				}else{
					var oldResult = JSON.parse(reply);
				}
				if(oldResult !=null){
					//alte Ergebnisse werden aus der Prognose entfernt
					for(p=0; p<oldResult.length; p++){
						var KID = oldResult[p].KID;
						for(q=0; q<prognose.length; q++){
							if(prognose[q].KID == oldResult[p].KID){
								if(p<11) prognose[q].Platz[p+1] -= 1;
							}
						}
					}
				}
				//neue Ergebnisse werden zur Prognose hinzugefügt
				for (u=0; u<sortedResult.length; u++){
					var kandidatP={
						KID: sortedResult[u].KID,
						Platz: [0,0,0,0,0,0,0,0,0,0,0]
					};
					for(q=0; q<prognose.length; q++){
						if(prognose[q].KID == sortedResult[u].KID){
							if(u<11) prognose[q].Platz[u+1] += 1;
						//Kandidat wird zur Prognose hinzugefügt, falls er noch nicht da war 
						}else if(!_.some(prognose,  { KID: sortedResult[u].KID})){
							if(u<11){
								prognose.push(kandidatP);
							}
						}
					}
				}
				prognose = _.sortBy(prognose, ['Platz']);
				console.log("PrognoseNEU", prognose);
				db.set(wahlkreis+"_Prognose", JSON.stringify(prognose));
				db.set(UID+"_matchingResult", JSON.stringify(sortedResult));
			});
		});
	}
	
	function addUserPositionToThese(userposition, tid, gesendet, lastuserpos){
		db.get(tid, function (err, reply) { 
				if(err) throw err;
				var these = JSON.parse(reply);
				//Die Position des Wählers zur These wurde noch nicht erfasst
				if(gesendet == "false") {
					if(userposition=="PRO"){
						var pro = parseInt(these.Anzahl_Zustimmung);
						pro += 1;
						these.Anzahl_Zustimmung = pro;
					}
					if(userposition=="NEUTRAL"){
						var neutral= parseInt(these.Anzahl_Neutral);
						neutral += 1;
						these.Anzahl_Neutral = neutral;
					}
					if(userposition=="CONTRA"){
						var contra = parseInt(these.Anzahl_Ablehnung);
						contra += 1;
						these.Anzahl_Ablehnung = contra;
					}
					//Die Position des Wähler hat sich geändert
				}else{
					if(userposition == "PRO" && lastuserpos == "NEUTRAL"){
						var pro = parseInt(these.Anzahl_Zustimmung);
						var neutral= parseInt(these.Anzahl_Neutral);
						pro += 1;
						neutral -= 1;
						these.Anzahl_Zustimmung = pro;
						these.Anzahl_Neutral = neutral;
					}
					if(userposition == "PRO" && lastuserpos == "CONTRA"){
						var pro = parseInt(these.Anzahl_Zustimmung);
						var contra = parseInt(these.Anzahl_Ablehnung);
						pro += 1;
						contra -= 1;
						these.Anzahl_Zustimmung = pro;
						these.Anzahl_Ablehnung = contra;
					}
					if(userposition == "NEUTRAL" && lastuserpos == "CONTRA"){
						var contra = parseInt(these.Anzahl_Ablehnung);
						var neutral= parseInt(these.Anzahl_Neutral);
						neutral += 1;
						contra -= 1;
						these.Anzahl_Neutral = neutral;
						these.Anzahl_Ablehnung = contra;
					}
					if(userposition == "NEUTRAL" && lastuserpos == "PRO"){
						var pro = parseInt(these.Anzahl_Zustimmung);
						var neutral= parseInt(these.Anzahl_Neutral);
						pro -= 1;
						neutral += 1;
						these.Anzahl_Zustimmung = pro;
						these.Anzahl_Neutral = neutral;
					}
					if(userposition == "CONTRA" && lastuserpos == "PRO"){
						var pro = parseInt(these.Anzahl_Zustimmung);
						var contra = parseInt(these.Anzahl_Ablehnung);
						pro -= 1;
						contra += 1;
						these.Anzahl_Zustimmung = pro;
						these.Anzahl_Ablehnung = contra;
					}
					if(userposition == "CONTRA" && lastuserpos == "NEUTRAL"){
						var contra = parseInt(these.Anzahl_Ablehnung);
						var neutral= parseInt(these.Anzahl_Neutral);
						neutral -= 1;
						contra += 1;
						these.Anzahl_Neutral = neutral;
						these.Anzahl_Ablehnung = contra;
					}
				}
				//console.log("THESE   ", these);
				db.set(tid, JSON.stringify(these));
		});
	}
};