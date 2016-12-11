var constants = require('../constants/constants.json');
 
exports.removeDevice = function(registrationId, wahlkreis, db, callback){
	console.log("DELETE DEVICE:  " + registrationId);
    var result = db.SREM(wahlkreis + "regids", registrationId);
	var result2 = db.DEL(registrationId);
        if (result== 1) {
            callback(constants.success.msg_del_success);
        } else {
            callback(constants.error.msg_del_failure);
        }
}