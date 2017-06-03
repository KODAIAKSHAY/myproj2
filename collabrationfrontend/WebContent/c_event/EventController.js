'use strict';
app.controller('EventController', [
		'$scope',
		'EventService',
		'$location',
		'$rootScope',
		function($scope, EventService, $location, $rootScope) {
			console.log("EventController.....")
			var self = this;
			self.event = {
				event_id : '',
				name : '',
				description : '',
				venue : '',
				dateTime : '',
				errorCode : '',
				errorMessage : ''
			}
			self.events = [];

			self.getSelectedEvent = getEvent

			function getEvent(id) {
				console.log("getting event :" + id)
				EventService.getEvent(id).then(function(d) {
					$location.path('/viewEvent');
				}, function(errResponse) {
					console.error('Error while fetching event with id:' + id)
				});
			}
			;

			self.getAllEvents = function() {
				EventService.getAllEvents().then(function(d) {
					self.events = d;
				}, function(errResponse) {
					console.error('Error while fetching all the events');
				});
			};
			self.getAllEvents();

			self.submit = function() {
				{
					console.log('saving new event', self.event);
					self.createEvent(self.event);
				}
				self.reset();
			};

			self.createEvent = function(event) {
				console.log("create events...")
				EventService.createEvent(event).then(self.getAllEvents,
						function(response) {
					alert("Event successfully created")
							self.response = response.data
							
						}, function(errResponse) {
							console.error("Error while creating event");
						});
			};

			self.reset = function() {
				console.log('resetting the form', self.event);
				self.event = {
					event_id : '',
					name : '',
					description : '',
					venue : '',
					dateTime : '',
					errorCode : '',
					errorMessage : ''
				}
			};

			self.deleteEvent = function(event_Id) {
				EventService.deleteEvent(event_Id).then(self.getAllEvents,
						function(errResponse) {
							console.error("Error while deleting event");
						});
			};

		} ])