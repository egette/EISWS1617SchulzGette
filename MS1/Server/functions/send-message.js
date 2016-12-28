var gcm = require('node-gcm');
var constants = require('../constants/constants.json');
 
//Sendet für jede RegistrationsID aus dem Array RegistrationsIDs eine Benachrichtigung
//wobei in Message die ThesenID oder KandidatenID steht welche neue Informationen hat 
exports.sendMessage = function(message, registrationIds, callback){
 
    var message = new gcm.Message({data: {message: message}});
	for(i = 0; i<registrationIds.length; i++){
		var regTokens = [registrationIds[i]];
		console.log("reqtoken", regTokens);
		var sender = new gcm.Sender(constants.gcm_api_key);
		sender.send(message, { registrationTokens: regTokens }, function (err, response) {
	 
			if (err){
				console.error(err);
				callback(constants.error.msg_send_failure);
	 
			} else     {
				console.log(response);
				callback(constants.success.msg_send_success);
			}
	 
		});
	}
}