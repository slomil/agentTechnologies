var agentService = angular.module('agents', []);

agentService.controller('agentController', function($scope, agentFactory, $location){
	
	
	
	$scope.init = function(){
		
		var host = $location.host() + ":" + $location.port();
		
		agentFactory.getAllAgentTypes(host).success(function(data){
			$scope.allAgents = data.data;
		});
		
		agentFactoru.getAllRunningAgents(host).success(function(data){
			$scope.runningAgents = data.data;
		});
	}
});