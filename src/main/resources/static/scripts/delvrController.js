cac.controller('delvrController',function($scope,$http,$filter,$routeParams,$rootScope){$scope.idm=$routeParams.id;var urlp=$rootScope.domainename;$scope.produit;$scope.msg;$scope.resp=false;$scope.demandeA;init();$scope.nav=($routeParams.navr)?true:false;$routeParams.navr=$scope.nav;$scope.navsty=function(){$scope.nav=($scope.nav)?false:true;};$scope.change=function(){$http.get(urlp+"Alldemandeur/"+$scope.departement_sel).success(function(data){$scope.demandeurs=data;});}; $scope.submit=function(){var data={date:filterDateu($scope.dated)};$http.post(urlp+'delivre/'+$scope.produit.id+'/'+$scope.demandeur_sel+'/'+$scope.produit.numeros,JSON.stringify(data)).then(function(response){if(response.data){init();$scope.resp=true;$scope.msg="Le produit "+$scope.produit.label+" délivré";}},function(response){$scope.msg="Le produit "+$scope.produit.label+" non délivré";$scope.resp=false;$scope.statusval=response.status;$scope.statustext=response.statusText;$scope.headers=response.headers();});}; $scope.print=function(idd){var fileName="terminal.pdf";var a=document.createElement("a");document.body.appendChild(a);$http.get(urlp+'printdemande/'+idd,{responseType:'arraybuffer'}).then(function(response){var file=new Blob([response.data],{type:'application/pdf'});var fileURL=window.URL.createObjectURL(file);a.href=fileURL;a.download=fileName;a.click();});}; $scope.send=function(){var data={nom:$scope.nom,matr:$scope.matr,description:$scope.description};$http.post(urlp+'demandeur/'+$scope.departement_id,JSON.stringify(data)).then(function(response){if(response.data){init();restForm();$scope.resp=true;$scope.msg="Demandeur "+$scope.nom+" ajouter";}},function(response){$scope.msg="Demandeur "+$scope.nom+" non ajouter";$scope.resp=false;$scope.statusval=response.status;$scope.statustext=response.statusText;$scope.headers=response.headers();});}; $scope.delet=function(ids){if(ids<0){$http.delete(urlp+"demande/"+idd).then(function(response){init();},function(response){alert("Le demandeur relier à des demandes");});}else{demande=filterDemande(ids);$scope.msgsup="Voulez-vous vraiment supprimer la demande ?";idd=ids;}};$scope.rest=function(){restForm();}; function restForm(){$scope.nom="";$scope.matr="";$scope.description="";$scope.msg="";$scope.resp=false;$scope.departement_sel="";$scope.demandeur_sel="";}; function init(){$http.get(urlp+"produit/"+$scope.idm).success(function(data){$scope.produit=data;$http.get(urlp+"demandebypro/"+data.id).success(function(data){$scope.demandes=data;findEta();});});$http.get(urlp+"alldepartements").success(function(data){$scope.departements=data;});$scope.dated=$filter('date')(new Date(),'dd/MM/yyyy');}; $scope.libre=function(ids){if(ids<0){$http.get(urlp+"libreDemande/"+$scope.idd).then(function(response){init();findEta();},function(response){});}else{demande=filterDemande(ids);$scope.msgsup="Voulez-vous vraiment libre le produit ?";$scope.idd=ids;}};function filterDemande(ids){return($filter('filter')($scope.demandes,{id:ids})[0]);}; function findEta(){var log=[];angular.forEach($scope.demandes,function(value,key){if(value.delvre) $scope.demandeA=value;});}; function filterDate(date){var dates=date.split("-");return dates[2]+"/"+dates[1]+"/"+dates[0];}; function filterDateu(date){var dates=date.split("/");return dates[2]+"-"+dates[1]+"-"+dates[0];}});