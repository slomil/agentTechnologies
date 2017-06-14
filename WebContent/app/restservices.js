agentService.factory('agentFactory', function($http){
	
	var factory = [];
	
	factory.getAllAgentTypes = function(host){
		return $http.get("http://"+host+'/AT2/rest/agents/classes');
	};
	
	factory.getAllRunningAgents = function(host){
		return $http.get("http://"+host+'/AT2/rest/agents/running');
	}
	
	
	factory.startAgent = function(host,type, name){
		return $http.put("http://"+host+'/AT2/rest/agents/running/'+type+'/'+name);
	}
	
	factory.stopAgent = function(aid){
		return $http.delete();
	}
	
	factory.sendMessage = function(message){	
		return $http.post(message);
	}
	
	factory.getPerformatives = function(host){
		return $http.get("http://"+host+'/AT2/rest/messages');
	}
	
	return factory;
});