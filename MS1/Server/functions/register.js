var constants = require('../constants/constants.json');

 //Speichert die ID in der Liste des Wahlkreises
exports.register = function(uid, registrationId, wahlkreis, db, callback){
 
    var newDevice = { 
        UID : uid,
        registrationId : registrationId,
		Wahlkreis: wahlkreis
    };
 
	checkSET(wahlkreis + "regids", registrationId).then(function (check) {
		if (check == 1) {
			console.log("Die registrationId ist schon im set", registrationId);
			callback(constants.error.msg_reg_exists);
		} else {
			db.SADD(wahlkreis + "regids", registrationId);
			db.set(registrationId, JSON.stringify(newDevice));
			callback(constants.success.msg_reg_success);		
		}
    });
	
	function checkSET(set, data) {
		var promises = [];
		promises.push(db.SISMEMBERAsync(set, data));

		return Promise.all(promises).then(function (arrayOfResults) {

			return arrayOfResults[0];
		});
	}
}