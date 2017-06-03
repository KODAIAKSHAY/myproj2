'use strict';
app.controller("ChatController" , function($scope, ChatService) {
	console.log('ChatController....')
	$scope.messages = [];
    $scope.message = "";
    $scope.max = 140;
    
    $scope.friends=[];
    
    $scope.addMessage = function() {
    	console.log("addMessage")
    ChatService.send($scope.message,$scope.friends);
    	$scope.message = "";
    };
    
    ChatService.recieve().then(null , null, function(message) {
         console.log("recieve") 
         
       $scope.messages.push(message);  
    });
}); 