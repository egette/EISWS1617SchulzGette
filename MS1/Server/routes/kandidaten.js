exports.getKandidaten = function(db){
	return function(req, res){
		var wahlkreis = req.query.wahlkreis;
		var Kandidaten_IDS = [];
		if(req.query.KID){
			var KID = req.query.KID;
			db.get(KID, function (err, reply) { 
				if(err) throw err;
				if (!reply) {
					res.status(500).end();
				} else {
					var Kandidat = JSON.parse(reply);
					var result = {
						username: Kandidat.username,
						vorname: Kandidat.vorname,
						nachname: Kandidat.nachname,
						wahlkreis: Kandidat.wahlkreis,
						email: Kandidat.email,
						KID: Kandidat.KID,
						Partei: Kandidat.Partei,
						Thesen_beantwortet: Kandidat.Thesen_beantwortet
					};
					res.status(200).send(result).end();
				}
			});
		} else if(wahlkreis){
			db.smembers("Kandidaten_"+wahlkreis, function(err, replies){
				if( !replies || replies.length==0){
					//KEINE Kandidaten im Wahlkreis
				   res.status(401).end();
				}else{
					Kandidaten_IDS = replies;
					console.log('Das Kandidaten_IDS_ARRAY :', Kandidaten_IDS);
					makeKandidatenJSON(Kandidaten_IDS, db).then(function(json){
						res.status(201).send(json).end();
					});	
				}	
			});	
		}
	}
};

function makeKandidatenJSON(Kandidaten_IDS, db){
	var promises = [];
	var Kandidaten_JSONOBJECT = { "Kandidaten" : [], "Anzahl": "0" };
	Kandidaten_JSONOBJECT.Anzahl = Kandidaten_IDS.length;

	for (var i = 0; i < Kandidaten_IDS.length; i++) {
	promises.push(db.getAsync(Kandidaten_IDS[i]))
	}

   return Promise.all(promises).then(function(arrayOfResults) {
      for (i = 0; i < arrayOfResults.length; i++) {
        var Kandidat = JSON.parse(arrayOfResults[i]);
		var result = {
						username: Kandidat.username,
						vorname: Kandidat.vorname,
						nachname: Kandidat.nachname,
						wahlkreis: Kandidat.wahlkreis,
						email: Kandidat.email,
						KID: Kandidat.KID,
						Partei: Kandidat.Partei,
						Thesen_beantwortet: Kandidat.Thesen_beantwortet
		};
        Kandidaten_JSONOBJECT.Kandidaten.push(result);
      }
      return Kandidaten_JSONOBJECT;
    });
};