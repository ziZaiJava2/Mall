var app = angular.module('mall', []);
app.controller('userInfo', function($scope){
	var user ={};
	user.nick_name = 'wei';
	user.email = 'wei@test.com';
	user.gender = 'Female';
	user.address = 'shanghai';
	user.telephone = '12345678910';
	$scope.user=user;
})