
	var Waehler = {
		username: "MaxMustermann",
		password: "12345",
		wahlkreis: "Oberbergischer Kreis",
		email: "max@mustermann.de",
		WID: "WID_23"
	};

	var Kandidat = {
		username: "Kandidat_der_Herzen",
		vorname: "Peter",
		nachname: "Müller",
		password: "12345",
		wahlkreis: "Oberbergischer Kreis",
		email: "peter.müller@email.de",
		KID: "KID_34",
		Partei: "Unabhängig",
		Thesen_beantwortet: [ 
			{
				TID: "TID_2",
				POS: "PRO"
			},
			{
				TID: "TID_543",
				POS: "CONTRA"
			}
		]
	};	


	var These = {
		thesentext: "Es sollte für Bildung mehr Geld ausgegeben werden als für Krieg!",
		kategorie: "Wirtschaft",
		wahlkreis: "Oberbergischer Kreis",
		Anzahl_Zustimmung: 23454,
		Anzahl_Ablehnung: 34,
		Anzahl_Neutral: 347,
		Likes: 3785,
		TID: "TID_2",
		K_PRO: [
			{
				UID: "KID_2",
				Text: "Geld ist genung da!",
				likes: 5,
				Kommentare: [
					{
						Kommentar: "Endlich sagt es jemand"
					}
				]
		    },
			{
				UID: "KID_5",
				Text: "Bildung ist wichtig!",
				likes: 6,
				Kommentare: []
			}
		], 
		K_NEUTRAL: [],
		K_CONTRA: [],
		W_PRO: [], 
		W_NEUTRAL: [],
		W_CONTRA: [],
		K_POSITION: [
			{
				UID: "KID_2",
				POS: "PRO"
			},
			{
				UID: "KID_5",
				POS: "PRO"
			},
		]
	};

	var KandidatErgebnis = {
		KID: "KID_3",
		Zaehler: 27,
		Lokal: 12,
		Umwelt: 3,
		Aussenpolitik: 10,
		Satire: 2
	};

