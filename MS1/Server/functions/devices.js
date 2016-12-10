exports.listDevices = function(wahlkreis, db, callback){
	db.smembers(wahlkreis + "regids", function(err, replies){
		if( !replies || replies.length==0){
			console.log("KEINE REGIDS im Wahlkreis");
		}else{
			callback(replies);
		}	
	});
}