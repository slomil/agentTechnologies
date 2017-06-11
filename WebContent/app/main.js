var agentService = angular.module('agents', []);

agentService.controller('initController', function($scope, agentFactory, $location){
	
	
	
	$scope.init = function(){
		
		var host = $location.host() + ":" + $location.port();
		
		agentFactory.getAllAgentTypes(host).success(function(data){
			$scope.allAgents = data.data;
		});
		
		agentFactory.getAllRunningAgents(host).success(function(data){
			$scope.runningAgents = data.data;
		});
	}
});