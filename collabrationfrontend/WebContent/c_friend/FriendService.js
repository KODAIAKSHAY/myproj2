'use strict';
app
		.factory(
				'FriendService',
				[
						'$http',
						'$q',
						'$rootScope',
						function($http, $q, $rootScope)

						{
							console.log("FriendService....")

							var BASE_URL = "http://localhost:8081/CollaborationBE"
							return {
								getMyFriends : function() {
									console.log("Getting friends from service")
									return $http
											.get(BASE_URL + '/myFriends')
											.then(
													function(response) {
														console.log('Friends are:')
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while fetching Friends');
														return $q
														.reject(errResponse);

													});
								},

								getRequestsSentByMe : function() {
									console.log("Getting requests sent by me")
									return $http
											.get(
													BASE_URL
															+ '/getRequestsSentByMe/')
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while fetching Friends');
														return $q
																.reject(errResponse);
													});
								},

								getMyFriendRequests : function() {
									console
											.log("Getting new  requests sent to me")
									return $http
											.get(
													BASE_URL
															+ '/getMyFriendRequests/')
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while getting new FriendRequests..');
														return $q
																.reject(errResponse);
													});
								},
								
								accept: function(myfriend,id){
									console.log("accepting in friend")
									return $http.put(BASE_URL+'/friendaccept/'+myfriend.id,myfriend)
									.then(
											function(response){
												return response.data;
											},
											function(errResponse){
												console.error('Error while friend user');
												return $q.reject(errResponse);
											});
								},
								
								reject: function(myfriend,id){
									console.log("rejecting in friend")
									return $http.put(BASE_URL+'/friendreject/'+myfriend.id,myfriend)
									.then(
											function(response){
												return response.data;
											},
											function(errResponse){
												console.error('Error while friend user');
												return $q.reject(errResponse);
											});
								},

								sendFriendRequest : function(friendID) {
									return $http
											.get(
													BASE_URL + '/addFriend/'
															+ friendID)
											.then(
													function(response) {

														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while creating friend');
														return $q
																.reject(errResponse);
													});
								},

								updateFriendRequest : function(friend, id) {
									return $http
											.put(BASE_URL + '/friend/' + id,
													friend)
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while updating friend');
														return $q
																.reject(errResponse);
													});
								},

								deleteFriend : function(id) {
									return
											$http,
											delete (BASE_URL + '/friend/' + id)
													.then(
															function(response) {
																return response.data;
															},
															function(
																	errResponse) {
																console
																		.error('Error while deleting friend');
																return $q
																		.reject(errResponse);
															});
								},
							};
						} ]);