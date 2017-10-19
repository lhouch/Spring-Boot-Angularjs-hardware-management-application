var def = angular.module('defApp', ['ngRoute', 'ui.bootstrap'])
  .config(function($routeProvider ) {
    $routeProvider

      .when('/', {
        templateUrl: 'pages/homedemande.html',
        controller: 'demandeController',
      });

    $routeProvider.otherwise("/");
  });

def.controller('demandeController', function($scope, $http, $location) {
	var urlp =''; //$location.protocol() + "://" + $location.host() + ":" + $location.port()+"/cac/"; 
     
  $scope.statusval = ";"
  $scope.msg = "";
  $scope.resp = false;
  init();
  $scope.change = function() {
    $http.get(urlp+"Alldemandeur/" + $scope.departement_sel).success(function(data) {
      $scope.demandeurs = data;
    });
  }
  $scope.rest = function() {
    $scope.nom = "";
    $scope.matr = "";
    $scope.description = "";
    $scope.msg = "";
    $scope.resp = false;
  }

  $scope.submit = function() {
    var data = {
      nom: $scope.nom,
      matr: $scope.matr,
      description: $scope.description
    };
    $http.post(urlp+'demandeur/' + $scope.departement_id, JSON.stringify(data)).then(function(response) {
      if (response.data) {
        $http.get(urlp+"Alldemandeur/" + $scope.departement_sel).success(function(data) {
          $scope.demandeurs = data;
        });
        $scope.resp = true;
        $scope.msg = "Demandeur ajouter";
      }
    }, function(response) {
      $scope.msg = "Demandeur non ajouter";
      $scope.resp = false;
      $scope.statusval = response.status;
      $scope.statustext = response.statusText;
      $scope.headers = response.headers();
    });
  };
  $scope.send = function() {
    var data = {
      titre: $scope.titre,
      description: $scope.descd,
      date: new Date()
    };
    $http.post(urlp+'demmachine/' + $scope.demandeur_sel, JSON.stringify(data)).then(function(response) {
      if (response.data) {
        $scope.resp = true;
        $scope.msg = "Demande " + $scope.titre + " passe";
      }
    }, function(response) {
      $scope.msg = "demande " + $scope.titre + " non passe";
      $scope.resp = false;
      $scope.statusval = response.status;
      $scope.statustext = response.statusText;
      $scope.headers = response.headers();
    });
  }
  function init() {
    $http.get(urlp+"alldepartements").success(function(data) {
      $scope.departements = data;
    });
  }

});