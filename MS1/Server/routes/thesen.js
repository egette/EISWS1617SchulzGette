exports.publish = function(db){
	return function(req, res){
		console.log('Thesen JSON BODY:  ', req.body);
		
		var These = {
			thesentext: req.body.thesentext,
			kategorie: req.body.kategorie,
			wahlkreis: req.body.wahlkreis,
			Anzahl_Zustimmung: "0",
			Anzahl_Ablehnung: "0",
			Anzahl_Neutral: "0",
			TID: "",
			K_PRO: [], 
			K_NEUTRAL: [],
			K_CONTRA: [],
			W_PRO: [], 
			W_NEUTRAL: [],
			W_CONTRA: []
		};
		
		var wahlkreis = req.body.wahlkreis;
		var kategorie = req.body.kategorie; 
		var wahlkreis_kategorie = wahlkreis + "_" + kategorie;
			
		//Überprüfung welche Thesen-ID die letzte war
		db.get('last_Thesen_ID', function (err, reply) { 
			if(err) throw err;
			var last_Thesen_ID;
			if (!reply || reply == "TID_NaN") {
 				last_Thesen_ID = "TID_1";
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
			res.json({'TID': new_Thesen_ID}).status(201).end();	
		});
		
	}
}

function makeThesenJSON(anzahl_thesen, Thesen_IDS){
var promises = [];
var Thesen_JSONOBJECT = { "Thesen" : [], "Anzahl": "0" };
if(anzahl_thesen > Thesen_IDS.length) anzahl_thesen = Thesen_IDS.length;
	Thesen_JSONOBJECT.Anzahl = anzahl_thesen;


  for (var i = 0; i < anzahl_thesen; i++) {
    promises.push(db.getAsync(Thesen_IDS[i]))
  }

   return Promise.all(promises).then(function(arrayOfResults) {
      
      for (i = 0; i < arrayOfResults.length; i++) {
        var thesenjsondata = arrayOfResults[i];
        Thesen_JSONOBJECT.Thesen.push(thesenjsondata);
      }
      return Thesen_JSONOBJECT;
    });
}

function begruendung_hinzufuegen(tid, typ, uid, textdata, richtung){
	db.get(tid, function(err, reply){
		if(err) throw err;
		if (!reply ) {
			return 0;
		} else {
			
			var These = JSON.parse(reply);
			var typ2;
			if( typ == 'waehler') typ2 = "W";
			if( typ == 'kandidat') typ2 = "K";
			var richtungsarrayname = typ2 + "_" + richtung;
			console.log("richtungsarrayname:  ", richtungsarrayname);
			
			var begruendung_json = {
				UID: uid,
				Text: textdata,
				likes: "0",
				Kommentare: []
			};
			if(richtungsarrayname == "K_PRO"){
				var arraylength = These.K_PRO.length;
				if(arraylength > 0 ) arraylength + 1; 
				These.K_PRO[arraylength] = begruendung_json;
			}
			if(richtungsarrayname == "W_PRO"){
				var arraylength = These.W_PRO.length;
				if(arraylength > 0 ) arraylength + 1; 
				These.W_PRO[arraylength] = begruendung_json;
			}
			if(richtungsarrayname == "K_NEUTRAL"){
				var arraylength = These.K_NEUTRAL.length;
				if(arraylength > 0 ) arraylength + 1; 
				These.K_NEUTRAL[arraylength] = begruendung_json;
			}
			if(richtungsarrayname == "W_NEUTRAL"){
				var arraylength = These.W_NEUTRAL.length;
				if(arraylength > 0 ) arraylength + 1; 
				These.W_NEUTRAL[arraylength] = begruendung_json;
			}
			if(richtungsarrayname == "K_CONTRA"){
				var arraylength = These.K_CONTRA.length;
				if(arraylength > 0 ) arraylength + 1; 
				These.K_CONTRA[arraylength] = begruendung_json;
			}
			if(richtungsarrayname == "W_CONTRA"){
				var arraylength = These.W_CONTRA.length;
				if(arraylength > 0 ) arraylength + 1; 
				These.W_CONTRA[arraylength] = begruendung_json;
			}
			
			
			
			
			db.set(tid, JSON.stringify(These));
			return 1;
		}
					
	});
};
		
exports.putBegruendung = function(db){
	return function (req, res){
		var tid = req.query.tid;
		var typ = req.body.typ;
		var uid = req.body.uid;
		var richtung = req.body.richtung;
		var textdata = req.body.textdata;
		if(!tid || !typ || !uid || !richtung) res.status(409).end();
		if(!textdata) textdata = "";
		if (tid.substring(0, 4) == "TID_"){
			if(begruendung_hinzufuegen(tid, typ, uid, textdata, richtung) == 1){
				res.status(201).end();
			}else {
				//res.status(409).end();
			}
			
		}
}};

exports.getThesen = function(db){
	return function (req, res){
		var kategorie = req.query.kategorie;
		var wahlkreis = req.query.wahlkreis;
		var wahlkreis_kategorie = wahlkreis + "_" + kategorie;
		
		var anzahl_thesen = req.query.anzahl;
		if (!anzahl_thesen) anzahl_thesen = 20;
		console.log('anzahl gefragt:', anzahl_thesen);
		
		var thesenjsondata;
		var Thesen_IDS =[];
		
		//Thesen einer Kategorie eines Wahlkreies geben
		if(kategorie != undefined && wahlkreis !=undefined){
			db.smembers(wahlkreis_kategorie, function(err, replies){
				if( !replies || replies.length==0){
					//KEINE Thesen in der Kategorie des Wahlkreises
				   res.status(401).end();
				}else{
					Thesen_IDS = replies;
					console.log('Das Thesen_ID_ARRAY :', Thesen_IDS);
					makeThesenJSON(anzahl_thesen, Thesen_IDS).then(function(json){
						res.status(201).send(json).end();
					});	
				}	
			});	
		}
		
		//TODO Thesen nur von einem Wahlkreis, oder nur aus einer Kategorie
	}
}	



