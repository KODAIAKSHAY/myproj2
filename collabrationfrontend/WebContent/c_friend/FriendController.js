'use strict';
app
		.controller(
				'FriendController',
				[
						'UserService',
						'$scope',
						'FriendService',
						'$location',
						'$routeParams',
						'$http',
						'$rootScope',
						function(UserService, $scope, FriendService, $location,
								$routeParams, $http, $rootScope) {
							console.log("FriendController....")
							var self = this;

							self.friend = {
								id : '',
								userID : '',
								friendID : '',
								status : ''
							};

							self.friends = [];

							self.user = {
								id : '',
								name : '',
								password : '',
								email : '',
								contact : '',
								address : '',
								status : '',
								isOnline : '',
								role : '',
								errorCode : '',
								errorMessage : ''
							};

							self.users = [];

							self.myfriend = {
								id : '',
								userID : '',
								friendID : '',
								status : ''
							};

							self.myfriends = [];

							self.myrequest = {
									id : '',
									userID : '',
									friendID : '',
									status : ''
								};

								self.myrequests = [];

							self.getMyFriendRequests = function() {
								FriendService
										.getMyFriendRequests()
										.then(
												function(d) {
													self.myfriends = d;
												},
												function(errResponse) {
													console
															.error('Error while getting friend requests')
												});
							};
							self.getMyFriendRequests();
							
							self.getRequestsSentByMe = function() {
								FriendService
										.getRequestsSentByMe()
										.then(
												function(d) {
													self.myrequests = d;
												},
												function(errResponse) {
													console
															.error('Error while getting friend requests sent by me')
												});
							};
							self.getRequestsSentByMe();

							self.sendFriendRequest = sendFriendRequest
							function sendFriendRequest(friendID) {
								console.log("send friendRequest:" + friendID)
								FriendService
										.sendFriendRequest(friendID)
										.then(
												function(d) {
													self.friend = d;
													alert("friend request sent")
												},
												function(errResponse) {
													console
															.error('Error while sending friend request');
												});
							};
							

							self.getMyFriends = function() {
								FriendService
										.getMyFriends()
										.then(
												function(d) {
													self.friends = d;
												},
												function(errResponse) {
													console
															.error('Error while getting friends')
												});
							};
							self.getMyFriends();

							self.friendaccept = function(myfriend) {
								{
									self.accept(myfriend, myfriend.id);
								}
							};

							self.accept = function(myfriend, id) {
								console.log('accepting the user');
								FriendService
										.accept(myfriend, id)
										.then(
												self.fetchAllUsers,
												alert("Successfully accepted request..."),
												function(errresponse) {
													console
															.log('Error while accepting user')
												});
							};

							self.friendreject = function(myfriend) {
								{
									self.reject(myfriend, myfriend.id);
								}
							};

							self.reject = function(myfriend, id) {
								console.log('rejecting the user');
								FriendService
										.reject(myfriend, id)
										.then(
												self.fetchAllUsers,
												alert("Successfully rejected request..."),
												function(errresponse) {
													console
															.log('Error while rejecting user')
												});
							};

							self.unFriend = function(id) {
								FriendService
										.unFriend(id)
										.then(
												self.fetchAllFriends,
												function(errResponse) {
													console
															.error('Error while unfriending friend request...')
												});
							};

							self.fetchAllUsers = function() {
								UserService
										.fetchAllUsers()
										.then(
												function(d) {
													self.users = d;
												},
												function(errresponse) {
													console
															.error('Error while fetching users');
												});
							};

							self.deleteFriend = function(id) {
								FriendService
										.deleteFriend(id)
										.then(
												self.fetchAllFriends,
												function(errResponse) {
													console
															.error('Error while deleting friend');
												});
							};

							self.updateFriendRequest = function(friend, id) {
								FriendService
										.updateFriendRequest(friend, id)
										.then(
												self.fetchAllFriends,
												function(errResponse) {
													console
															.error('Error while updating friend');
												});
							};

							self.fetchAllUsers();

						} ]);