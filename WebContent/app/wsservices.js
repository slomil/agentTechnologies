agentService.factory('messageFactory', function(){
	var factory = {};
	
	factory.sendMessage = function(msg, ws){
	    // Wait until the state of the socket is not ready and send the message when it is...
	    waitForSocketConnection(ws, function(){
	        console.log("message sent!!!");
	        ws.send(msg);
	    });
	}
	// Make the function wait until the connection is made...
	function waitForSocketConnection(socket, callback){
	    setTimeout(
	        function () {
	            if (socket.readyState === 1) {
	                console.log("Connection is made")
	                if(callback != null){
	                    callback();
	                }
	                return;

	            } else {
	                console.log("wait for connection...")
	                waitForSocketConnection(socket, callback);
	            }

	        }, 5); // wait 5 milisecond for the connection...
	}
	
	return factory;
});
