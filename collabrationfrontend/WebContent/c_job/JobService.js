'use strict';
app
		.factory(
				'JobService',
				[
						'$http',
						'$q',
						'$rootScope',
						function($http, $q, $rootScope) {
							console.log("JobService")

							var BASE_URL = "http://localhost:8081/CollaborationBE"
							return {
								applyForJob : function(jobID) {
									return $http
											.post(
													BASE_URL + '/applyForJob/'
															+ jobID)
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while applying for job');
														return $q
																.reject(errResponse);
													});
								},

								getJobDetails : function(jobID) {
									console.log("Get job details of :" + jobID)
									return $http
											.get(
													BASE_URL
															+ '/getjobDetails/'
															+ jobID)
											.then(
													function(response) {
														$rootScope.selectedJob = response.data
														return response.data;
													},
													function(errResponse) {
														console
																.error("Error while getting job details");
														return $q
																.reject(errResponse);
													});
								},

								getMyAppliedJobs : function() {
									return $http
											.get(
													BASE_URL
															+ '/getMyAppliedJobs/')
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error whilegetting appiled jobs');
													});
								},

								postAJob : function(job) {
									return $http
											.post(BASE_URL + '/postAJob/' , job)
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while posting a job');
														return $q
																.reject(errResponse);
													});
								},
								
								accept: function(jobapplied,id){
									console.log("accepting in service")
									return $http.put(BASE_URL+'/jobaccept/'+jobapplied.id,jobapplied)
									.then(
											function(response){
												return response.data;
											},
											function(errResponse){
												console.error('Error while updating jobapplied');
												return $q.reject(errResponse);
											});
								},	
								
								reject: function(jobapplied,id){
									console.log("rejecting in service")
									return $http.put(BASE_URL+'/jobreject/'+jobapplied.id,jobapplied)
									.then(
											function(response){
												return response.data;
											},
											function(errResponse){
												console.error('Error while updating jobapplied');
												return $q.reject(errResponse);
											});
								},	

								rejectJobApplication : function(userID, jobID) {
									return $http
											.put(
													BASE_URL
															+ '/rejectJobApplication/'
															+ userID + "/"
															+ jobID)
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while rejecting job');
														return $q
																.reject(errResponse);
													});
								},

								callForInterview : function(id) {
									return $http
											.put(
													BASE_URL
															+ '/callForInterview/'
															+ userID, jobID)
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while calling for interview');
														return $q
																.reject(errResponse);
													});
								},

								selectUser : function(id) {
									return $http
											.put(
													BASE_URL + '/selectUser/'
															+ userID, jobID)
											.them(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while selecting user..');
														return $q
																.reject(errResponse);
													});
								},
								
								getAllJobsApplied: function(){
							    	  return $http.get(BASE_URL+'/getAllJobsApplication/')
							    	  .then(
							    			  function(response){
							    				  return response.data;
							    			  },
							    			  function(errResponse){
							    				  console.error('Error while getting all jobsapplied');
							    				  return $q.reject(errResponse);
							    			  });
							      },  

								getAllJobs : function() {
									return $http
											.get(BASE_URL + '/getAlljobs')
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while getting all the jobs ..');
														return $q
																.reject(errResponse);
													});
								}
							};
						} ]);