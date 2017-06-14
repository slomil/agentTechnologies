var agentService = angular.module('agents', []);
var agenti="";
var pokrenut="";
var ws = "";
var number = 0;

agentService.controller('initController', function($scope, $window, agentFactory, 
			$location, messageFactory){
	
	
	$scope.init = function(){
		
		var host = $location.host() + ":" + $location.port();
		ws = new WebSocket("ws://"+host+"/AT2/wsClient");
		
		/*agentFactory.getAllAgentTypes(host).success(function(data){
			
			agenti=data;
			var agenti2=[];
			for(var i=0;i<agenti.length;i++){
				agenti2[i]=agenti[i].name;
			}
			$scope.allAgents=agenti2;
		});*/
		
		var wsMessage = JSON.stringify({
			"request" : "getAgentTypes",
			"host" : host,
			"agentType" : "",
			"name" : ""
		});
		
		messageFactory.sendMessage(wsMessage, ws);
		
		var wsMessage = JSON.stringify({
			"request" : "getAllRunningAgents",
			"host" : host, 
			"agentType" : "",
			"name" : ""
		});
		
		messageFactory.sendMessage(wsMessage, ws);
		
		ws.onmessage = function(data){
			var retVal = JSON.parse(data.data);
			var action = retVal.action;
			if(action=="getAllRunningAgents"){
				var agents = JSON.parse(retVal.retVal);
				var i=0;
		    	var ragents = [];
		    	for(i=0; i<agents.length; i++){
		    		var name = agents[i].id.name;
		    		ragents.push(name);
		    	}
				
		    	number = agents.length;
				 $scope.$apply(function () {
					 $scope.runningAgents = ragents;
			      });
			
			}else if(action=="getAgentTypes"){
				var retVal = JSON.parse(data.data);
				var action = retVal.action;
				if(action=="getAgentTypes"){
					agenti=JSON.parse(retVal.retVal);
					var agenti2=[];
					for(var i=0;i<agenti.length;i++){
						agenti2[i]=agenti[i].name;
					}
					 $scope.$apply(function () {
						 $scope.allAgents=agenti2;
				      });
				}
			}
		}
	}
	
	$scope.update=function(){
		
		var host = $location.host() + ":" + $location.port();
		
		var selectBox = document.getElementById("select1");
	    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
	   // alert(selectedValue);
	    
	    for(var i=0;i<agenti.length;i++){
	    	if (selectedValue==agenti[i].name){
	    		pokrenut=agenti[i].name;
	    		addSender();
	    		addReceiver();
	    		var type=JSON.stringify({
	    	    	"name":agenti[i].name,
	    			"module":agenti[i].module
	    	    });
	    		var random = Math.floor(Math.random() *1000);
	    		var name = agenti[i].name + random;
	    		
	    		/*agentFactory.startAgent(host, type, name).success(function(data){
	    			$scope.selectedItem = $scope.allAgents[0].value;
	    		});
	    		
	    		agentFactory.getAllRunningAgents(host).success(function(data){
	    	
			    	var i=0;
			    	var ragents = [];
			    	for(i=0; i<data.length; i++){
			    		var name = data[i].id.name;
			    		ragents.push(name);
			    	}
					$scope.runningAgents = ragents;
				});
				 
				 agentFactory.getPerformatives(host).success(function(data){
			    	$scope.allPerformative = data;
			    });
	    		*/
	    		
	    		var wsMessage = JSON.stringify({
	    			"request" : "startAgent",
	    			"host" : host, 
	    			"agentType" : type,
	    			"name" : name
	    		});
	    		
	    		messageFactory.sendMessage(wsMessage, ws);
	    		break;
	    	}	
	    }

	    
	    var wsMessage = JSON.stringify({
			"request" : "getAllRunningAgents",
			"host" : host, 
			"agentType" : "",
			"name" : ""
		});
		
		messageFactory.sendMessage(wsMessage, ws);
		
		var wsMessage = JSON.stringify({
			"request" : "getPerformatives",
			"host" : host, 
			"agentType" : "",
			"name" : ""
		});
		
		messageFactory.sendMessage(wsMessage, ws);
		
		ws.onmessage = function(data){
			var retVal = JSON.parse(data.data);
			var action = retVal.action;
			if(action=="startAgent"){
   			 $scope.$apply(function () {
   				 $scope.selectedItem = $scope.allAgents[0].value;
   		      });
   			}else if(action=="getAllRunningAgents"){
				var agents = JSON.parse(retVal.retVal);
				var i=0;
		    	var ragents = [];
		    	for(i=0; i<agents.length; i++){
		    		var name = agents[i].id.name;
		    		ragents.push(name);
		    	}
				
				 $scope.$apply(function () {
					 $scope.runningAgents = ragents;
			      });
	    	}else if(action=="getPerformatives"){
	    	
			 $scope.$apply(function () {
				 $scope.allPerformative = JSON.parse(retVal.retVal);
		      });
    		}
			
		}
	    
	}
	
	
	$scope.sendMessage=function(){
		var selectBox = document.getElementById("select2");
	    var selectedPerf = selectBox.options[selectBox.selectedIndex].value;
		
	    selectBox = document.getElementById("selectSender");
	    var  selectedSender = selectBox.options[selectBox.selectedIndex].value;
	    
	    var selectBox = document.getElementById("selectReceiver");
	    var selectedReciever = selectBox.options[selectBox.selectedIndex].value;
	    
		var message=JSON.stringify({
			"performative": selectedPerf,
			"sender": selectedSender,
			"recivers":selectedReciever,
			"replyTo":document.getElementById("replyTo").value,
			"content":document.getElementById("content").value,
			"language":document.getElementById("language").value,
			"encoding":document.getElementById("encoding").value,
			"ontology":document.getElementById("onthology").value,
			"protocol":document.getElementById("protocol").value,
			"conversationId": document.getElementById("conversationId").value,
			"replyWith":document.getElementById("replyWith").value,
			"inReplyTo":document.getElementById("inReplyTo").value,
			"replyBy":document.getElementById("replyBy").value
		});
		$("#textarea").val(message);
		
		agentFactory.sendMessage(message).success(function(data){
			
		});
		
	}
	
	
	$scope.clearMessages=function(){
				
	}
	
});

var addSender=function(){
	var selectBox = document.getElementById("selectSender");
	var children = selectBox.childNodes;
	var bool = false;
	var i=0;
	var size = children.length;
	for(i=0; i<size; i++){
		if(children[i].id==pokrenut)
			bool = true;
	}
	if(bool == false){
		var option = document.createElement("option");
		option.id=pokrenut;
		option.value = pokrenut;
		option.text = pokrenut;
		selectBox.appendChild(option);
	}
}

var addReceiver=function(){
	var selectBox = document.getElementById("selectReceiver");
	var children = selectBox.childNodes;
	var bool = false;
	var i=0;
	var size = children.length;
	for(i=0; i<size; i++){
		if(children[i].id==pokrenut)
			bool = true;
	}
	if(bool==false){
		var selectBox = document.getElementById("selectReceiver");
		var option = document.createElement("option");
		option.id=pokrenut;
		option.value = pokrenut;
		option.text = pokrenut;
		selectBox.appendChild(option);
	}
}