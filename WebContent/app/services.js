agentService.factory('agentFactory', function($http){
	
	var factory = [];
	
	factory.getAllAgentTypes = function(host){
		return $http.get(host+'/AT2/rest/agents/getAllAgentTypes');
	};
	
	factory.getAllRunningAgents = function(){
		return $http.get(host+'/AT2/rest/agents/getRunningAgents');
	}
	
	factory.startAgent = function(type, name){
		return $http.put("");
	}
	
	factory.stopAgent = function(aid){
		return $http.delete();
	}
	
	factory.sendMessage = function(message){
		return $http.post("");
	}
	
	factory.getPerformatives = function(){
		return 
	}
	
	return factory;
});