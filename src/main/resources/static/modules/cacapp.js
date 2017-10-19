var cac = angular
		.module('cacApp',
				[ 'ngRoute', 'ui.bootstrap', 'ngCookies', 'chart.js' ])
		.run(
				[
						'$rootScope',
						'$location',
						'$cookieStore',
						'$http',
						function($rootScope, $location, $cookieStore, $http) {
							$rootScope.globals = $cookieStore.get('globals')
									|| {};
							$rootScope.user = $cookieStore.get('user') || {};
							$rootScope.domainename = '';
							var urlp = $rootScope.domainename;
							if ($rootScope.globals.currentUser) {
								$http.defaults.headers.common['Authorization'] = 'Basic '
										+ $rootScope.globals.currentUser.authdata;
							}
							$rootScope.$on('$locationChangeStart', function(
									event, next, current) {
								if ($location.path() !== '/login'
										&& !$rootScope.globals.currentUser) {
									$location.path('/login');
								}
							});
							$http.get(urlp + "types").success(function(data) {
								$rootScope.listtypes = data;
							});
							$http.get(urlp + "Allnewdemande").success(
									function(data) {
										$rootScope.listdemandenew = data;
									});
						} ]).config(function($routeProvider) {
			$routeProvider.when('/', {
				templateUrl : 'pages/home.html',
				controller : 'mainController',
			}).when('/machines', {
				templateUrl : 'pages/machines.html',
				controller : 'machinesController'
			}).when('/type', {
				templateUrl : 'pages/typeMachine.html',
				controller : 'typecontroller'
			}).when('/demandeurs', {
				templateUrl : 'pages/demandeursController.html',
				controller : 'demandeursController'
			}).when('/delvr', {
				templateUrl : 'pages/delvr.html',
				controller : 'delvrController'
			}).when('/delvrdemandeur', {
				templateUrl : 'pages/delvrdemandeur.html',
				controller : 'delvrDemController'
			}).when('/demabdenew', {
				templateUrl : 'pages/newdemande.html',
				controller : 'newdemandeController'
			}).when('/listdemandenew', {
				templateUrl : 'pages/listnewdemande.html',
				controller : 'listdemandeController'
			}).when('/departements', {
				templateUrl : 'pages/listDepartement.html',
				controller : 'departementecontroller'
			}).when('/login', {
				templateUrl : 'pages/login.html',
				controller : 'loginController'
			}).when('/users', {
				templateUrl : 'pages/users.html',
				controller : 'userscontroller'
			});
			$routeProvider.otherwise("/");
		});
cac.controller('mainController', function($scope, $rootScope, $routeParams,
		$http) {
	urlp = $rootScope.domainename;
	$scope.labels = [];
	$scope.data = [];
	$scope.labelsr = [];
	$scope.datar = [], [];
	var totalaray = [];
	var librearay = [];
	$scope.listAllDepartements = [];
	$http.get(urlp + "Allnewdemande").success(function(data) {
		$rootScope.listdemandenew = data;
	});
	$scope.nav = ($routeParams.navr) ? true : false;
	$routeParams.navr = $scope.nav;
	$scope.navsty = function() {
		$scope.nav = ($scope.nav) ? false : true;
	};
	$http.get(urlp + "alldepartements").success(function(data) {
		$scope.listAllDepartements = data;
		$http.get(urlp + "allDemandes").success(function(data) {
			$scope.listAllDemandes = data;
			initDiag();
		});
	});
	$http.get(urlp + "produits").success(function(data) {
		angular.forEach(data, function(value) {
			$scope.labelsr.push(value.nom);
			totalaray.push(value.total);
			librearay.push(value.libre);
		});
		$scope.datar.push(totalaray);
		$scope.datar.push(librearay);
	});
	$scope.series = [ 'TOTAL', 'LIBRE' ];
	$scope.colors = [ {
		backgroundColor : "rgba(62, 137, 185, 0.2)",
		pointBackgroundColor : "rgba(62, 137, 185, 0.9)",
		pointHoverBackgroundColor : "rgba(62, 137, 185, 0.9)",
		borderColor : "rgba(62, 137, 185, 0.9)",
		pointBorderColor : '#fff',
		pointHoverBorderColor : "rgba(62, 137, 185, 0.9)"
	}, "rgba(5,120,33,0.5)", "#9a9a9a", "rgb(233,177,69)" ];
	function initDiag() {
		var total;
		angular.forEach($scope.listAllDepartements, function(valueDep) {
			$scope.labels.push(valueDep.nom);
			total = 0;
			angular.forEach($scope.listAllDemandes, function(value) {
				if (value.demandeur.departement.id == valueDep.id
						&& value.delvre) {
					total += value.prod.prix;
				}
			});
			$scope.data.push(total);
		});
	}
});