'use strict';
app
		.factory(
				'EventService',
				[
						'$http',
						'$q',
						'$rootScope',
						function($http, $q, $rootScope) {
							console.log("EventService...")
							var BASE_URL = "http://localhost:8081/CollaborationBE"

							return {

								getAllEvents : function() {
									return $http
											.get(BASE_URL + '/events')
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while returning the list of events')
														return $q
																.reject(errResponse);
													});
								},

								createEvent : function(event) {
									return $http
											.post(BASE_URL + '/event/', event)
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while creating event');
														return $q
																.reject(errResponse);
													});
								},

								getEvent : function(id) {
									return $http
											.get(BASE_URL + '/event/' + id)
											.then(
													function(response) {
														$rootScope.selectedEvent = response.data
														return response.data;
													},
													function(errResponse) {
														console
																.error("Error while getting event");
														return $q
																.reject(errResponse);
													});
								},

								deleteEvent : function(id) {
									return
											$http,
											delete (BASE_URL + '/event/' + id)
													.then(
															function(response) {
																return response.data;

															},
															function(
																	errResponse) {
																console
																		.error('Error while deleting event');
																return $q
																		.reject(errResponse);
															});
								},
							}
						} ])