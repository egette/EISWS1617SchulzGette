
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
		webseite: "peter.müller-kandidat.de"
		KID: "KID_34",
		Partei: "Unabhängig",
		Thesen_positioniert: [ 
			{
				TID: "TID_2",
				POS: "PRO",
				KATEGORIE: "Umwelt"
			},
			{
				TID: "TID_543",
				POS: "CONTRA"
				KATEGORIE: "Lokal"
			}
		],
		Begruendungen: [
			{ 
				TID: "TID_3",
				POS: "PRO",
				TEXT: "Diese These ist toll."
			}
		],
		Biographie: [
			{
			 Geburtsdatum: "23.1.1975",
			 Bildungsweg: "Abitur, Studium",
			 Berufe: "Lehrer",
			 Mitgliedschaften: "",
			}
		],
		Wahlprogramm: [
			Text: "Deswegen sollte ich gewählt werden.",
			Link: "link.zum.wahlprogramm.de"
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
				Text: "Geld ist genug da!",
				likes: 5,
				Kommentare: [
					{
						UID: "WID_2",
						USERNAME: "Wähler123"
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

