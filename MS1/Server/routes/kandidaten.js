exports.getKandidaten = function(db){
	return function(req, res){
		var KID = req.query.KID;
		var wahlkreis = req.query.wahlkreis;
		var Kandidaten_IDS = [];
		if(wahlkreis){
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
}

function makeKandidatenJSON(Kandidaten_IDS, db){
	var promises = [];
	var Kandidaten_JSONOBJECT = { "Kandidaten" : [], "Anzahl": "0" };
	Kandidaten_JSONOBJECT.Anzahl = Kandidaten_IDS.length;


  for (var i = 0; i < Kandidaten_IDS.length; i++) {
    promises.push(db.getAsync(Kandidaten_IDS[i]))
  }

   return Promise.all(promises).then(function(arrayOfResults) {
      
      for (i = 0; i < arrayOfResults.length; i++) {
        var kandidatenjsondata = arrayOfResults[i];
        Kandidaten_JSONOBJECT.Kandidaten.push(kandidatenjsondata);
      }
      return Kandidaten_JSONOBJECT;
    });
}