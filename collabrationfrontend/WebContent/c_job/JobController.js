'use strict';

app
		.controller(
				'JobController',
				[
						'JobService',
						'$scope',
						'$location',
						'$rootScope',
						function(JobService, $scope, $location, $rootScope) {
							console.log("JobController...")
							var self = this;
							self.job = {
								id : '',
								title : '',
								description : '',
								dateTime : '',
								qualification : '',
								status : '',
								errorCode : '', 
								errorMessage : ''
							};

							self.jobs = [];
							
							self.jobapplied = {
									id : '',
									userID : '',
									jobID :'',
									dateTime :'',
									remarks :'',
									status :'',
									errorCode:'',
									errorMessage:''			
							};
							self.jobsapplied=[];
							
							self.myjobapplied = {
									id : '',
									userID : '',
									jobID :'',
									dateTime :'',
									remarks :'',
									status :'',
									errorCode:'',
									errorMessage:''			
							};
							self.myjobsapplied=[];

							self.applyForJob = applyForJob

							function applyForJob(jobID) {
								console.log("applyForJob")
								var currentUser = $rootScope.currentUser
								console.log("currentUser.id:" + currentUser.id)

								if (typeof currentUser.id == 'undefined') {
									alert("Please login to apply for the job..")
									console
											.log("User not logged in. Login to apply for job..")
									$location.path('/login');
								}
								console.log("userID:" + currentUser.id
										+ "applying for job:" + jobID)
								JobService
										.applyForJob(jobID)
										.then(
												function(d) {
													self.job = d;
													alert("You have successfully applied for the job...")
												},
												function(errResponse) {
													console
															.error('error while applying for job..try again later...');
												});
							};

							self.getMyAppliedJobs = function() {
								console
										.log('calling method to getMyAppliedJobs');
								JobService
										.getMyAppliedJobs()
										.then(
												function(d) {
													self.myjobsapplied = d;
												},
												function(errResponse) {
													console
															.error('error while fetv=ching jobs');
												});
							};
							self.getMyAppliedJobs();
							
							 self.getAllJobsApplied=function(){
						        	console.log('calling the method getAllJobsApplied');
						        	JobService.getAllJobsApplied()
						        	.then(
						        			function(d){
						        				self.jobsapplied=d;
						        			},
						        			function(errResponse){
						        				console.error('Error while fetching all opened jobs');
						        			});
						        };
						        self.getAllJobsApplied();

							self.rejectJobApplication = function(userID) {
								var jobID = $rootScope.selectedJob.id;
								JobService
										.rejectJobApplication(userID, jobID)
										.then(
												function(d) {
													self.jobs = d;
													alert("successfully rejected jobApplication")
												},
												function(errResponse) {
													console
															.error('error while rejecting job');
												});
							};

							self.callForInterview = function(userID) {
								var jobID = $rootScope.selectedJob.id;
								JobService
										.callForInterview(userID, jobID)
										.then(
												function(d) {
													self.jobs = d;
													alert("successfully called for interview the jobApplication")
												},
												function(errResponse) {
													console
															.error('error while callng for job');
												});
							};

							self.selectUser = function(userID) {
								var jobID = $rootScope.selectedJob.id;
								JobService
										.selectUser(userID, jobID)
										.then(
												function(d) {
													self.jobs = d;
													alert("successfully selected the jobApplication")
												},
												function(errResponse) {
													console
															.error('error while selecting user for job');
												});
							};

							self.getAllJobs = function() {
								console.log('calling method getAllJobs')
								JobService
										.getAllJobs()
										.then(
												function(d) {
													self.jobs = d;
												},
												function(errResponse) {
													console
															.error('Error while fetching all the jobs');
												});
							};

							self.getAllJobs();

							self.submit = function() {
								{
									console.log('submit a new job', self.job);
									self.postAJob(self.job);
								}
								self.reset();
							};

							self.postAJob = function(job) {
								console.log('submit a new job', self.job);
								JobService
										.postAJob(job)
										.then(
												function(d) {
													self.job = d;
													alert("successfully posted a job")
												},
												function(errResponse) {
													console
															.error('Error while posting a job..');
												});
							};

							self.getJobDetails = getJobDetails

							function getJobDetails(jobID) {
								console.log('get job details of the id:'
										+ jobID)
								JobService
										.getJobDetails(jobID)
										.then(
												function(d) {
													self.job = d;
													$location
															.path('/getJobDetails');
												},
												function(errResponse) {
													console
															.error('unable to get job details...');
												});
							};
							
							self.jobaccept = function(jobapplied){
								{
									self.accept(jobapplied,jobapplied.id);
								}
							};

							self.accept =function (jobapplied,id){
								console.log('accepting the jobapplied');
								JobService.accept(jobapplied,id).then(self.getAllJobsApplied,
								  function(errresponse){
									console.log('Error while accepting jobapplied')
								});
							};
							
							self.jobreject = function(jobapplied){
								{
									self.reject(jobapplied,jobapplied.id);
								}
							};

							self.reject =function (jobapplied,id){
								console.log('rejecting the jobapplied');
								JobService.reject(jobapplied,id).then(self.getAllJobsApplied,
								  function(errresponse){
									console.log('Error while rejecting jobapplied')
								});
							};

							self.reset = function() {
								console.log('resetting the job');
								self.job = {
									id : '',
									title : '',
									description : '',
									dateTime : '',
									qualification : '',
									status : '',
									errorCode : '',
									errorMessage : ''
								};
								$scope.myForm.$setPristine();
							};
						} ]);
