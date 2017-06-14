var agentService = angular.module('agents', []);
var agenti="";
var pokrenut="";

agentService.controller('initController', function($scope, $window, agentFactory, $location){
	
	
	$scope.init = function(){
		
		var host = $location.host() + ":" + $location.port();
		
		agentFactory.getAllAgentTypes(host).success(function(data){
			
			agenti=data;
			var agenti2=[];
			for(var i=0;i<agenti.length;i++){
				agenti2[i]=agenti[i].name;
			}
			$scope.allAgents=agenti2;
		});
	
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
	    		agentFactory.startAgent(host, type, name).success(function(data){
	    			$scope.selectedItem = $scope.allAgents[0].value;
	    		});
	    		break;
	    		}
	    	}

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
	var option = document.createElement("option");
	option.id=pokrenut;
	option.value = pokrenut;
	option.text = pokrenut;
	selectBox.appendChild(option);
	
}