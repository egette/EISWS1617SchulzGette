var devicesFunction = require('../functions/devices');
var sendFunction = require('../functions/send-message');
var constants = require('../constants/constants.json');
//Funktion zum Veröffentlichen einer These
exports.publish = function(db){
	return function(req, res){
		console.log('Thesen JSON BODY:  ', req.body);
		var md5 = require('md5');
		var These = {
			thesentext: req.body.thesentext,
			kategorie: req.body.kategorie,
			wahlkreis: req.body.wahlkreis,
			Anzahl_Zustimmung: 0,
			Anzahl_Ablehnung: 0,
			Anzahl_Neutral: 0,
			Likes: 0,
			TID: "",
			time: Math.floor(Date.now()),
			K_PRO: [], 
			K_NEUTRAL: [],
			K_CONTRA: [],
			W_PRO: [], 
			W_NEUTRAL: [],
			W_CONTRA: [],
			K_POSITION: []
		};
		
		var wahlkreis = req.body.wahlkreis;
		var kategorie = req.body.kategorie; 
		var wahlkreis_kategorie = wahlkreis + "_" + kategorie;
		
		if ( typeof wahlkreis  == 'undefined' || typeof kategorie  == 'undefined' ) {
			console.log(constants.error.msg_invalid_param.message);
			res.status(400).json(constants.error.msg_invalid_param).end();
 
        } else if ( !wahlkreis.trim()  || !kategorie.trim() ) {
			console.log(constants.error.msg_empty_param.message);
			res.status(400).json(constants.error.msg_empty_param).end();
 
        } else{
			//Überprüfung ob der Hash des Thesentextes schon im Wahlkreis vorhanden ist
			var thesentexthash = md5(req.body.thesentext);	
			console.log("HASH", thesentexthash);
			checkSET(wahlkreis+"-hashlist", thesentexthash).then(function (check) {
				if (check == 1) {
					console.log("Diese These ist schon im Wahlkreis vorhanden", req.body.thesentext);
					res.status(400).end();
				}else {
					db.SADD(wahlkreis+"-hashlist", thesentexthash);
					//Überprüfung welche Thesen-ID die letzte war
					db.get('last_Thesen_ID', function (err, reply) { 
						if(err) throw err;
						var last_Thesen_ID;
						if (!reply || reply == "TID_NaN") {
							last_Thesen_ID = "TID_0";
						} else {
							last_Thesen_ID = reply.toString();
						}
								
						var old_TID = last_Thesen_ID.substring(4);
						var old_TID_INT = parseInt(old_TID);
						var new_TID = old_TID_INT + 1;
						var new_Thesen_ID = "TID_" + new_TID.toString();
						These.TID = new_Thesen_ID;
						console.log("neue Thesenid :  ", new_Thesen_ID);
						//These wird in Redis gespeichert
						db.set(new_Thesen_ID, JSON.stringify(These));
						//Dem Set des Wahlkreises die Thesen_ID hinzufügen
						db.SADD(wahlkreis, new_Thesen_ID);
						//Dem Set der Kategorie die Thesen_ID hinzufügen
						db.SADD(kategorie, new_Thesen_ID);
						//Dem Set der Kategorie des Wahlkreises die Thesen_ID hinzufügen
						db.SADD(wahlkreis_kategorie, new_Thesen_ID);
						
						//last_Thesen_ID wird aktuallierst
						db.set('last_Thesen_ID', new_Thesen_ID);
						//Alle angemeldeten Geräte des Wahlkreises benachrichtigen das eine neue These veröffentlicht wurde
						devicesFunction.listDevices(wahlkreis, db,  function(result) {
							var registrationIds = result;
							sendFunction.sendMessage(new_Thesen_ID, registrationIds, function(callback){
							console.log(callback);
							});
						});
						
						console.log("These:  ", These);
						res.send(These).status(201).end();	
					});
				}
			});
		}
	}
	
	function checkSET(set, data) {
		var promises = [];
		promises.push(db.SISMEMBERAsync(set, data));

		return Promise.all(promises).then(function (arrayOfResults) {

			return arrayOfResults[0];
		});
	}	
}
		
//Funktion um die Position der Kandidaten und alle Begründungen zur These abzuspeichern		
exports.putPosition = function(db){
	return function (req, res){
		console.log("BODY", req.body);
		var tid = req.body.tid;
		var typ = req.body.typ;
		var uid = req.body.uid;
		var richtung = req.body.richtung;
		var textdata = req.body.textdata;
		var username = req.body.username;
		if ( typeof tid  == 'undefined' || typeof typ  == 'undefined' || typeof uid  == 'undefined'|| typeof richtung  == 'undefined') {
			console.log(constants.error.msg_invalid_param.message);
			res.status(400).json(constants.error.msg_invalid_param).end();
 
        } else if ( !tid.trim()  || !typ.trim() || !uid.trim() || !richtung.trim() ) {
			console.log(constants.error.msg_empty_param.message);
			res.status(400).json(constants.error.msg_empty_param).end();
 
        } else if (tid.substring(0, 4) == "TID_"){
			//Kandidat positioniert sich zur These
			if(!textdata && typ == "kandidat"){
				db.get(tid, function(err, reply){
					if(err) throw err;
					if (!reply ) {
						res.status(500).end();
					} else {
						var These = JSON.parse(reply);
						var kategorie = These.kategorie;
						var wahlkreis = These.wahlkreis;
						var Position = {
							UID: uid,
							POS: richtung
						};
						var neu = 1;
						var laenge = These.K_POSITION.length;
						for(i = 0; i < laenge; i++){
							if(These.K_POSITION[i].UID == uid){
								These.K_POSITION[i].POS = richtung;
								neu = 0;
							}
						}
						console.log("Position hinzufuegen ohne Text");
						if(neu == 1) These.K_POSITION.push(Position);
						db.set(tid, JSON.stringify(These));
						//Dem JSONOBJEKT des Kandidaten wird die neue Position hinzugefügt
						updateBeantworteteThesen(tid, uid, richtung, db, kategorie);
						//Alle angemeldeten Geräte des Wahlkreises benachrichtigen
						devicesFunction.listDevices(wahlkreis, db,  function(result) {
							sendFunction.sendMessage(tid, result, function (callback){
							console.log(callback);
							});
						});
						res.status(201).send(These).end();
					}
					
				});
			}else {
				//Eine neue Begruendung wird hinzugefügt
				db.get(tid, function(err, reply){
					if(err) throw err;
					if (!reply ) {
						return 0;
					} else {
						
						var These = JSON.parse(reply);
						var wahlkreis = These.wahlkreis;
						var typ2;
						if( typ == 'waehler') typ2 = "W";
						if( typ == 'kandidat') typ2 = "K";
						var richtungsarrayname = typ2 + "_" + richtung;
						console.log("richtungsarrayname:  ", richtungsarrayname);
						
						var position_json = {
							UID: uid,
							Username: username,
							Text: textdata,
							likes: 0,
							Kommentare: []
						};
						var neu = 1;
						
						if(richtungsarrayname == "K_PRO"){
							for(i = 0; i < These.K_PRO.length; i++){
								if(These.K_PRO[i].UID == uid){
									These.K_PRO[i].Text = position_json.Text;
									neu = 0;
								}
							}
							if (neu == 1) These.K_PRO.push(position_json);
						}
						if(richtungsarrayname == "W_PRO"){
							for(i = 0; i < These.W_PRO.length; i++){
								if(These.W_PRO[i].UID == uid){
									These.W_PRO[i].Text = position_json.Text;
									neu = 0;
								}
							}
							if (neu == 1) These.W_PRO.push(position_json);
						}
						if(richtungsarrayname == "K_NEUTRAL"){
							for(i = 0; i < These.K_NEUTRAL.length; i++){
								if(These.K_NEUTRAL[i].UID == uid){
									These.K_NEUTRAL[i].Text = position_json.Text;
									neu = 0;
								}
							}
							if (neu == 1) These.K_NEUTRAL.push(position_json);
						}
						if(richtungsarrayname == "W_NEUTRAL"){
							for(i = 0; i < These.W_PRO.length; i++){
								if(These.W_NEUTRAL[i].UID == uid){
									These.W_NEUTRAL[i].Text = position_json.Text;
									neu = 0;
								}
							}
							if (neu == 1) These.W_NEUTRAL.push(position_json);
						}
						if(richtungsarrayname == "K_CONTRA"){
							for(i = 0; i < These.K_CONTRA.length; i++){
								if(These.K_CONTRA[i].UID == uid){
									These.K_CONTRA[i].Text = position_json.Text;
									neu = 0;
								}
							}
							if (neu == 1) These.K_CONTRA.push(position_json);
						}
						if(richtungsarrayname == "W_CONTRA"){
							for(i = 0; i < These.W_CONTRA.length; i++){
								if(These.W_CONTRA[i].UID == uid){
									These.W_CONTRA[i].Text = position_json.Text;
									neu = 0;
								}
							}
							if (neu == 1) These.W_CONTRA.push(position_json);
						}
						if(typ == 'kandidat') updateKandidatBegruendungen(position_json, richtung, tid, neu, db);
						db.set(tid, JSON.stringify(These));
						//Alle Geräte des Wahlkreises benachrichtigen
						devicesFunction.listDevices(wahlkreis, db,  function(result) {
							sendFunction.sendMessage(tid, result, function (callback){
								console.log(callback);
							});
						});
						res.status(201).send(These).end();
					}			
				});
			}
		}
	}
	
};

//Aktuallisiert das Kandidaten JsonObjekt
function updateKandidatBegruendungen(position, richtung, tid, neu, db){
	var uid = position.UID;
	var begruendung = {
		Text: position.Text,
		TID: tid,
		likes: position.likes,
		Kommentare: position.Kommentare,
		Richtung: richtung
	};
	db.get(uid, function(err, reply){
		if(err) throw err;
		var kandidat = JSON.parse(reply);
		if(neu == 0){
			for(i=0; i<kandidat.Begruendungen.length; i++){
				if(kandidat.Begruendungen[i].TID == tid && kandidat.Begruendungen[i].Richtung == richtung ){
					kandidat.Begruendungen[i].Text = position.Text;
				}
			}
		}else {
			kandidat.Begruendungen.push(begruendung);
		}	
		db.set(uid, JSON.stringify(kandidat));
	});
}

//Aktuallisiert das Kandidaten JsonObjekt 
function updateKandidatBegruendungenKommentare(uid, kommentar, tid, richtung, db){
	console.log("Kandidat " + uid + "hat den Kommentar bekommen" + kommentar);
	db.get(uid, function(err, reply){
		if(err) throw err;
		var kandidat = JSON.parse(reply);
		for(i=0; i<kandidat.Begruendungen.length; i++){
			if(kandidat.Begruendungen[i].TID == tid && kandidat.Begruendungen[i].Richtung == richtung ){
				kandidat.Begruendungen[i].Kommentare.push(kommentar);
			}
		}	
		db.set(uid, JSON.stringify(kandidat));
	});
}

//Ein Kommentar zur einer Begründung hinzugefügt
exports.postKommentar = function(db){
	return function (req, res){
		console.log("BODY", req.body);
		var tid = req.body.tid;
		var begruendungsid = req.body.beguid;
		var uid = req.body.uid ;
		var kommentartext = req.body.kommentar;
		var username = req.body.username;
		var typ = req.body.typ;
		var position = req.body.richtung;
		
		if ( typeof tid == 'undefined' || typeof typ == 'undefined' || typeof uid == 'undefined'|| typeof position == 'undefined' 
		|| typeof begruendungsid == 'undefined' || typeof kommentartext == 'undefined' || typeof username  == 'undefined') {
			console.log(constants.error.msg_invalid_param.message);
			res.status(400).json(constants.error.msg_invalid_param).end();
 
        } else if ( !tid.trim()  || !typ.trim() || !uid.trim() || !position.trim() || !begruendungsid.trim()  || !kommentartext.trim()  || !username.trim() ) {
			console.log(constants.error.msg_empty_param.message);
			res.status(400).json(constants.error.msg_empty_param).end();
 
        } else{
			db.get(tid, function (err, reply) { 
				if(err) throw err;
				var These = JSON.parse(reply);
				var wahlkreis = These.wahlkreis;
				var Kommentarobject = {
					UID: uid,
					USERNAME: username,
					Kommentar: kommentartext
				};
				//Eine Begruendung eines Wählers wird kommentiert
				if(typ == 'w'){
					if(position == "PRO"){
						for(i = 0; i < These.W_PRO.length; i++){
							if(These.W_PRO[i].UID == begruendungsid){
								These.W_PRO[i].Kommentare.push(Kommentarobject);
							}
						}	
					}
					if(position == "NEUTRAL"){
						for(i = 0; i < These.W_NEUTRAL.length; i++){
							if(These.W_NEUTRAL[i].UID == begruendungsid){
								These.W_NEUTRAL[i].Kommentare.push(Kommentarobject);
							}
						}	
					}
					if(position == "CONTRA"){
						for(i = 0; i < These.W_CONTRA.length; i++){
							if(These.W_CONTRA[i].UID == begruendungsid){
								These.W_CONTRA[i].Kommentare.push(Kommentarobject);
							}
						}	
					}
				//Eine Begründung eines Kandidaten wird kommentiert
				}else if(typ == 'k'){
					if(position == "PRO"){
						for(i = 0; i < These.K_PRO.length; i++){
							if(These.K_PRO[i].UID == begruendungsid){
								These.K_PRO[i].Kommentare.push(Kommentarobject);
							}
						}	
					}
					if(position == "NEUTRAL"){
						for(i = 0; i < These.K_NEUTRAL.length; i++){
							if(These.K_NEUTRAL[i].UID == begruendungsid){
								These.K_NEUTRAL[i].Kommentare.push(Kommentarobject);
							}
						}	
					}
					if(position == "CONTRA"){
						for(i = 0; i < These.K_CONTRA.length; i++){
							if(These.K_CONTRA[i].UID == begruendungsid){
								These.K_CONTRA[i].Kommentare.push(Kommentarobject);
							}
						}	
					}
					
				}
				if(begruendungsid.substring(0,1) == "K") updateKandidatBegruendungenKommentare(begruendungsid, Kommentarobject, tid, position, db);
				db.set(tid, JSON.stringify(These));
				//Alle Geräte des Wahlkreies benachrichtigen
				devicesFunction.listDevices(wahlkreis, db,  function(result) {
					sendFunction.sendMessage(tid, result, function (callback){
						console.log(callback);
					});
				});
				console.log("THESE :", These);
				res.status(201).send(These).end();
			});
		}
	}
}

//Aktuallisiert das Kandidaten JsonObjekt
function updateBeantworteteThesen(tid, kid, position, db, kategorie){
	db.get(kid, function (err, reply) {
		if (err) throw err;
		var kandidat = JSON.parse(reply);
		var thesebeantwortet = {
			TID: tid,
			POS: position,
			KATEGORIE: kategorie
		};
		var neu = 1;
		for(p = 0; p < kandidat.Thesen_beantwortet.length; p++){
			if(kandidat.Thesen_beantwortet[p].TID == tid){
			kandidat.Thesen_beantwortet[p] = thesebeantwortet;
			neu = 0;
			}
		}
		if(neu == 1) kandidat.Thesen_beantwortet.push(thesebeantwortet);
		db.set(kid, JSON.stringify(kandidat));
	});
}

//Funktion um genau eine These zu bekommen
exports.getThese = function(db){
	return function (req, res){
		var tid = req.params.tid;
		db.get(tid, function (err, reply) { 
			if(err){
				throw err;
				res.status(400).end();
			} else{
			var These = JSON.parse(reply);
			res.send(These).status(200).end();
			}
		});
	}
}

//Funktion um mehrere Thesen aus einer Kategorie oder eines Wahlkreises zu bekommen
exports.getThesen = function(db){
	return function (req, res){
		var kategorie = req.query.kategorie;
		console.log("kategorie", kategorie);
		var wahlkreis = req.query.wahlkreis;
		console.log("wahlkreis", wahlkreis);
		var wahlkreis_kategorie = wahlkreis + "_" + kategorie;
		
		var anzahl_thesen = req.query.anzahl;
		if (!anzahl_thesen) anzahl_thesen = 100;
		console.log('anzahl gefragt:', anzahl_thesen);
		
		var thesenjsondata;
		var Thesen_IDS =[];
		
		//Thesen einer Kategorie eines Wahlkreies geben
		if(kategorie != undefined && wahlkreis != undefined){
			db.smembers(wahlkreis_kategorie, function(err, replies){
				if( !replies || replies.length==0){
					//KEINE Thesen in der Kategorie des Wahlkreises
					console.log("KEINE Thesen in der Kategorie des Wahlkreises");
				   res.status(404).end();
				}else{
					Thesen_IDS = replies;
					console.log('Das Thesen_ID_ARRAY :', Thesen_IDS);
					makeThesenJSON(anzahl_thesen, Thesen_IDS, db).then(function(json){
						res.status(201).send(json).end();
					});	
				}	
			});	
		//Thesen aus einem Wahlkreis ohne Kategorie
		}else if (wahlkreis){
			db.smembers(wahlkreis, function(err, replies){
				if( !replies || replies.length==0){
					//KEINE Thesen in dem Wahlkreis
					console.log("KEINE Thesen in dem Wahlkreis");
				   res.status(404).end();
				}else{
					Thesen_IDS = replies;
					console.log('Das Thesen_ID_ARRAY :', Thesen_IDS);
					makeThesenJSON(anzahl_thesen, Thesen_IDS, db).then(function(json){
						res.status(201).send(json).end();
					});	
				}	
			});	
		//Thesen aus einer Kategorie ohne Wahlkreis
		}else if (kategorie){
			db.smembers(kategorie, function(err, replies){
				if( !replies || replies.length==0){
					//KEINE Thesen in der Kategorie
					console.log("KEINE Thesen in der Kategorie");
				   res.status(404).end();
				}else{
					Thesen_IDS = replies;
					console.log('Das Thesen_ID_ARRAY :', Thesen_IDS);
					makeThesenJSON(anzahl_thesen, Thesen_IDS, db).then(function(json){
						res.status(201).send(json).end();
					});	
				}	
			});	
		}else{
		//KEINE KATEGORIE ODER WAHLKREIS
		 console.log("KEINE KATEGORIE ODER WAHLKREIS");
		 res.status(400).end();
		}
	}
}	

//Erzeugt aus einem Array von ThesenIDs ein JSONArray mit alles Thesen als JSONOBJEKT
function makeThesenJSON(anzahl_thesen, Thesen_IDS, db){
	var promises = [];
	var Thesen_JSONOBJECT = { "Thesen" : [], "Anzahl": "0" };
	if(anzahl_thesen > Thesen_IDS.length) anzahl_thesen = Thesen_IDS.length;
	Thesen_JSONOBJECT.Anzahl = anzahl_thesen;

  for (var i = 0; i < anzahl_thesen; i++) {
    promises.push(db.getAsync(Thesen_IDS[i]));
  }

   return Promise.all(promises).then(function(arrayOfResults) {
      
      for (i = 0; i < arrayOfResults.length; i++) {
        var thesenjsondata = arrayOfResults[i];
        Thesen_JSONOBJECT.Thesen.push(thesenjsondata);
      }
      return Thesen_JSONOBJECT;
    });
}

//Aktuallisiert das Kandidaten JsonObjekt
function updateKandidatBegruendungenLikes(uid, like, tid, richtung, db){
	db.get(uid, function(err, reply){
		if(err) throw err;
		var kandidat = JSON.parse(reply);
		for(i=0; i<kandidat.Begruendungen.length; i++){
			if(kandidat.Begruendungen[i].TID == tid && kandidat.Begruendungen[i].Richtung == richtung ){
				kandidat.Begruendungen[i].likes += like;
			}
		}	
		db.set(uid, JSON.stringify(kandidat));
	});
}

//Funktion um eine Begründung zu liken
exports.putLikesbegruendungen = function(db){
	return function (req, res){
		console.log("BODY", req.body);
		var begruendungsid = req.body.beguid;
		var tid = req.body.tid;
		var position = req.body.richtung;
		var typ = req.body.typ;
		var like = req.body.like;
		if ( typeof tid == 'undefined' || typeof typ == 'undefined' || typeof position == 'undefined' || typeof begruendungsid == 'undefined' || typeof like == 'undefined' ) {
			console.log(constants.error.msg_invalid_param.message);
			res.status(400).json(constants.error.msg_invalid_param).end();
 
        } else if ( !tid.trim()  || !typ.trim()  || !position.trim() || !begruendungsid.trim() ) {
			console.log(constants.error.msg_empty_param.message);
			res.status(400).json(constants.error.msg_empty_param).end();

        } else{
			db.get(tid, function (err, reply) { 
				if(err) throw err;
				var These = JSON.parse(reply);
				//Begruendung eines Wählers wird geliked
				if(typ == 'w'){
					if(position == "PRO"){
						for(i = 0; i < These.W_PRO.length; i++){
							if(These.W_PRO[i].UID == begruendungsid){
								These.W_PRO[i].likes += like;
							}
						}	
					}
					if(position == "NEUTRAL"){
						for(i = 0; i < These.W_NEUTRAL.length; i++){
							if(These.W_NEUTRAL[i].UID == begruendungsid){
								These.W_NEUTRAL[i].likes += like;
							}
						}	
					}
					if(position == "CONTRA"){
						for(i = 0; i < These.W_CONTRA.length; i++){
							if(These.W_CONTRA[i].UID == begruendungsid){
								These.W_CONTRA[i].likes += like;
							}
						}	
					}
				//Begründung eines Kandidaten wird geliked
				}else if(typ == 'k'){
					if(position == "PRO"){
						for(i = 0; i < These.K_PRO.length; i++){
							if(These.K_PRO[i].UID == begruendungsid){
								These.K_PRO[i].likes += like;
							}
						}	
					}
					if(position == "NEUTRAL"){
						for(i = 0; i < These.K_NEUTRAL.length; i++){
							if(These.K_NEUTRAL[i].UID == begruendungsid){
								These.K_NEUTRAL[i].likes += like;
							}
						}	
					}
					if(position == "CONTRA"){
						for(i = 0; i < These.K_CONTRA.length; i++){
							if(These.K_CONTRA[i].UID == begruendungsid){
								These.K_CONTRA[i].likes += like;
							}
						}	
					}
					updateKandidatBegruendungenLikes(begruendungsid, like, tid, position, db);
				}
				db.set(tid, JSON.stringify(These));
				console.log("THESE :", These);
				res.status(201).send(These).end();
			});
		}
	}
}
exports.putLikes = function(db){
	return function (req, res){
		console.log("BODY", req.body);
		var tid = req.body.tid;
		var like = req.body.like;
		if ( typeof tid == 'undefined' || typeof like == 'undefined') {
			console.log(constants.error.msg_invalid_param.message);
			res.status(400).json(constants.error.msg_invalid_param).end();
 
        } else if ( !tid.trim() ) {
			console.log(constants.error.msg_empty_param.message);
			res.status(400).json(constants.error.msg_empty_param).end();

        } else{
			db.get(tid, function (err, reply) { 
				if(err) throw err;
				var These = JSON.parse(reply);
				These.Likes += like;
				var wahlkreis = These.wahlkreis;
				db.set(tid, JSON.stringify(These));
				devicesFunction.listDevices(wahlkreis, db,  function(result) {
							var registrationIds = result;
							sendFunction.sendMessage(tid, registrationIds, function(callback){
							console.log(callback);
							});
						});
				console.log("THESE :", These);
				res.status(201).send(These).end();
			});
		}
	}
}