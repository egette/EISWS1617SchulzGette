 //Funktion zum registerien von Wähler und Kandidaten
 exports.register = function(db){
	return function(req, res){
		console.log(req.body);
		
		var typ = req.body.typ;
		var email = req.body.email;	

			
		//EMAIL CHECK
		if (email != null ||email != undefined ){
			//Überprüfung ob im SET "email" die geschickte EMAIL dabei ist
			if ( db.ISMEMBER("email", email) == 1) {
				res.status(409).end();
			}
		}
		

		//TYP = Waehler
		if (typ = "waehler"){

			var Waehler = {
					username: req.body.username,
					password: req.body.password,
					wahlkreis: req.body.wahlkreis,
					email: req.body.email
					
			};
			//LETZTE ID WÄHLER			
			var last_Waehler_ID;
			db.get('last_Waehler_ID', function (err, reply) { 
				if(err) throw err;
				
				last_Waehler_ID = reply.toString();
				if (last_Waehler_ID == undefined) last_Waehler_ID = "WID_1";
				var old_WID = last_Waehler_ID.substring(5);
				var old_WID_INT = parseInt(old_WID);
				var new_WID = old_WID_INT + 1;
				var new_Waehler_ID = "WID_" + new_WID.toString();
				//Wähler in Redis speichern
				db.set(new_Weahler_ID, JSON.stringify(Waehler), redis.print);
				//Email Adresse zum Set "email" hinzufügen
				db.SADD("email", email, redis.print);
				//last_Wahler_ID wird aktuallierst
				db.set('last_Waehler_ID', new_Waehler_ID, redis.print);	
			});

				res.status(201).end();
		}
		
		
		// TYP = KANDIDAT
		if (typ = "kandidat"){
			//TODO
		} else {
			//TYP des USERS wurde nicht richtig angegeben
			res.status(500).end();
		}
	}
 }
