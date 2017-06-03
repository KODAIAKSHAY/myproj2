'use strict';
app
		.controller(
				'UserController',
				[
						'$scope',
						'UserService',
						'$location',
						'$rootScope',
						'$cookieStore',
						'$http',
						function($scope, UserService, $location, $rootScope,
								$cookieStore, $http) {
							console.log("UserController...")
							var self = this;
							self.user = {
								id : '',
								name : '',
								email : '',
								password : '',
								contact : '',
								address : '',
								role : '',
								status : '',
								reason : '',
								errorCode : '',
								errorMessage : ''
							};
							self.users = [];

							$scope.orderByMe = function(x) {
								$scope.myOrderBy = x;
							}

							self.fetchAllUsers = function() {
								console.log("fetchALlUsers....")
								UserService
										.fetchAllUsers()
										.then(
												function(d) {
													self.users = d;
												},
												function(errResponse) {
													console
															.error("Error while fetching all the users");
												});

							};

							self.createUser = function(user) {
								console.log("createUser....")
								UserService
										.createUser(user)
										.then(
												self.fetchAllUsers,
												function(errResponse) {
													console
															.error("Error while creating a new user...");
												});

							};

							self.useraccept = function(user){
								{
									self.accept(user,user.id);
								}
							};
					
							self.accept =function (user,id){
								console.log('accepting the user');
								UserService.accept(user,id).then(self.fetchAllUsers,
								  function(errresponse){
									console.log('Error while accepting user')
								});
							};
							
							
							self.userreject = function(user){
								{
									self.reject(user,user.id);
								}
							};
					
							self.reject =function (user,id){
								console.log('rejecting the user');
								UserService.reject(user,id).then(self.fetchAllUsers,
								  function(errresponse){
									console.log('Error while rejecting user')
								});
							};
							
							self.userupdate = function(user)
							{
								self.updateUser(self.user);
							
							}

							self.updateUser = function(user, id) {
								console.log("updateUser....")
								UserService
										.updateUser(user, id)
										.then(
												self.fetchAllUsers,
												alert("User successfully updated"),
												function(errResponse) {
													console
															.error("Error while updating user...");
												});

							};

							self.authenticate = function(user) {
								console.log("authenticate...")
								UserService
										.authenticate(user)
										.then(

												function(d) {

													self.user = d;
													console
															.log("user.errorCode: "
																	+ self.user.errorCode)
													if (self.user.errorCode == "404")

													{
														alert(self.user.errorMessage)

														self.user.userId = "";
														self.user.password = "";

													} else { // valid
																// credentials
														console
																.log("Valid credentials. Navigating to home page")
														$rootScope.currentUser = self.user
														$http.defaults.headers.common['Authorization'] = 'Basic '
																+ $rootScope.currentUser;
														$cookieStore
																.put(
																		'currentUser',
																		$rootScope.currentUser);
														$location.path('/');

													}

												},
												function(errResponse) {

													console
															.error('Error while authenticating User');
												});
							};

							self.logout = function() {
								console.log("logout")
								$rootScope.currentUser = {};
								$cookieStore.remove('currentUser');
								UserService.logout()
								$location.path('/');

							};

							self.fetchAllUsers();

							self.login = function() {
								{
									console.log('login validation????????',
											self.user);
									self.authenticate(self.user);
								}

							};

							self.submit = function() {
								{
									console.log('Saving New User', self.user);
									self.createUser(self.user);
								}
								self.reset();
							};

							self.reset = function() {
								self.user = {
									id : '',
									name : '',
									email : '',
									password : '',
									contact : '',
									address : '',
									role : '',
									status : '',
									reason : '',
									errorCode : '',
									errorMessage : ''
								};
								$scope.myForm.$setPristine(); // reset Form
							};

							
							self.myprofile = function(){
								console.log("myProfile...")
								UserService.myprofile($rootScope.currentUser.id)
								.then(function(d){
									self.user=d;
									$location.path("/myprofile")
								},
								function(errResponse){
									console.error('Error while fetching profile.');
								})
							}
						} ]);
