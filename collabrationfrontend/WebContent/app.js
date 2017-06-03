var app = angular.module("myApp",["ngRoute","ngCookies"]);
app.config(function($routeProvider){
	$routeProvider
	.when("/",{
		templateUrl : "c_home/home.html",
		controller : "HomeController"
	})
	
	.when("/register",{
templateUrl : "c_user/register.html",
controller : "UserController"
})

.when("/login",{
templateUrl : "c_user/login.html",
controller : "UserController"
})

.when("/myprofile",{
templateUrl : "c_user/myprofile.html",
controller : "UserController"
})

.when("/listBlog",{
	templateUrl:"c_blog/listBlog.html",
	controller:"BlogController"
})

.when("/createBlog",{
	templateUrl:"c_blog/createBlog.html",
	controller:"BlogController"
})

.when("/viewBlog",{
	templateUrl:"c_blog/viewBlog.html",
	controller:"BlogController"
})

.when("/getJobDetails",{
	templateUrl : "c_job/getJobDetails.html",
	controller : "JobController"
})

.when("/search_job",{
	templateUrl : "c_job/searchjob.html",
	controller : "JobController"
})

.when("/view_applied_jobs",{
	templateUrl : "c_job/viewAllJobs.html",
	controller : "JobController"
})

.when("/viewAppliedJobs",{
	templateUrl : "c_job/viewAppliedJobs.html",
	controller : "JobController"
})

.when('/post_job',{
	templateUrl : 'c_job/postjob.html',
		controller : 'JobController'	
})

.when('/adminHome',{
	templateUrl : 'c_admin/adminHome.html'
})

.when('/manage_job',{
	templateUrl : 'c_admin/admin_manage_job.html'
})

.when('/manageUsers',{
	templateUrl : 'c_admin/manageUsers.html'
})

.when('/searchFriend',{
	templateUrl : 'c_friend/searchFriend.html',
		controller : 'FriendController'	
})

.when('/viewFriend',{
	templateUrl : 'c_friend/viewFriend.html',
		controller : 'FriendController'	
})

.when('/manageFriends',{
	templateUrl : 'c_friend/manageFriends.html',
		controller : 'FriendController'	
})

.when('/friends',{
	templateUrl : 'c_friend/friends.html',
		controller : 'FriendController'	
})

.when('/sentRequests',{
	templateUrl : 'c_friend/sentRequests.html',
		controller : 'FriendController'	
})

.when('/viewFriendRequest',{
	templateUrl : 'c_friend/viewFriendRequest.html',
		controller : 'FriendController'	
})

.when('/chat_forum',{
	templateUrl : 'c_chat_forum/chat_forum.html',
		controller : 'ChatForumController'	
})

.when('/chat',{
	templateUrl : 'c_chat/chat.html',
		controller : 'ChatController'	
})

.when('/createEvent',{
	templateUrl : 'c_admin/createEvent.html',
		
})

.when('/eventsList',{
	templateUrl : 'c_event/eventsList.html',
		controller : 'EventController'	
})

.when('/viewEvent',{
	templateUrl : 'c_event/viewEvent.html',
		controller : 'EventController'	
})

.otherwise({redirectTo: '/'});
})


app.run(function($rootScope, $location, $cookieStore, $http){
	 $rootScope.$on('$locationChangeStart', function (event, next, current) {
		 console.log("$locationChangeStart")
		  //http://localhost:8081/Collaboration/addjob
	         //redirect to login page if not logged in and trying to access a restricted page
		  var userPages = ['/myprofile','/createBlog','/searchFriend','/manageRequests','/createBlog','/listBlog','/viewBlog']
		 var adminPages = ['/postjob','/manageUsers','/admin_manage_job','/adminHome']
		// var restrictedPage=$.inArray($location.path(),['//','/','/view_blog','/register','/list_blog',])=== -1;
		// -1 ----> non-restricted pages are more and for restricted pages ----> 1 ;
		 var currentPage = $location.path()
		 
		 var isUserPage = $.inArray(currentPage,userPages)==1;
		 var isAdminPage = $.inArray(currentPage,adminPages)==1;
		 
	
		// console.log("restrictedPage:" +restrictedPage)
	     var loggedIn = $rootScope.currentUser.id;
	     
		 console.log("loggedIn:"+loggedIn)
		 console.log("isUserPage:"+isUserPage)
		 console.log("isAdminPage:"+isAdminPage)
		 
	 if (!loggedIn) 
     {
    	 
    	 if(isUserPage||isAdminPage) {
    		 console.log("Navigating to login page:")
    		 alert("Need to login to do this operation")
    		 $location.path('/login');
    	 }
    	  
     }
     else //logged in
    	 {
    	 
    	 var role = $rootScope.currentUser.role;
    	 //var userRestrictedPage = $.inArray($location.path(), ['/post_job','/adminhome']) ===0;
    	 
    	 if (isAdminPage && role!='admin')
    		 {
    		 alert("You cannot do this operation as you are not logged in as:"+role)
    		 $location.path('/login');
    		 }
    	 }
    	 
});
		
	   //keep user logged in after page refresh

	 $rootScope.currentUser = $cookieStore.get('currentUser') ||{};
	 if($rootScope.currentUser){
		 $http.defaults.headers.common['Authorization']='Basic'+$rootScope.currentUser;
		 }
});
