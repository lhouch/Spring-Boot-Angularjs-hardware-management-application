cac.controller('machinesController',function($scope,$http,$rootScope,$filter,$routeParams){var page=0;var urlp=$rootScope.domainename;var shrch=false;var url;var idd;$scope.resp=false;$scope.msg=false;$scope.id=-1;$scope.idm=$routeParams.id;$scope.mcsh='';$scope.ml=false;$http.get(urlp+"Allnewdemande").success(function(data){$rootScope.listdemandenew=data;});$scope.nav=($routeParams.navr)?true:false;$routeParams.navr=$scope.nav;$scope.navsty=function(){$scope.nav=($scope.nav)?false:true;};$http.get(urlp+"type/"+$scope.idm).success(function(data){$scope.type=data;});function getData(){if($scope.ml) url=urlp+"produitdlvr/"+$scope.idm+"?page="+page;else url=(shrch)?urlp+"chercherProduits/"+$scope.idm+"?page="+page+"&mc="+$scope.mcsh:urlp+"produits/"+$scope.idm+"?page="+page;$http.get(url).success(function(data){$scope.produits=data;if($scope.produits.numberOfElements==0&&$scope.produits.number>0){page=$scope.produits.number-1;getData();}});}; $scope.suivente=function(){page=$scope.produits.number+1;getData();};$scope.mlibre=function(){$scope.ml=($scope.ml)?false:true;page=0;getData();};$scope.precedent=function(){page=$scope.produits.number-1;getData();};$scope.sherch=function(){page=0;shrch=true;getData();};$scope.edit=function(ide){prod=filterProd(ide);$scope.id=ide;$scope.label=prod.label;$scope.marque=prod.marque;$scope.model=prod.model;$scope.prix=prod.prix;$scope.numeros=prod.numeros;$scope.description=prod.description;$scope.garantie=filterDate(prod.garantie);$scope.msg="";$scope.resp=false;}; $scope.rest=function(){restForm();}; function restForm(){$scope.label="";$scope.marque="";$scope.model="";$scope.prix="";$scope.numeros="";$scope.description="";$scope.msg="";$scope.resp=false;$scope.garantie=$filter('date')(new Date(),'dd/MM/yyyy');}; $scope.submit=function(){if($scope.id==-1){var data={label:$scope.label,marque:$scope.marque,model:$scope.model,prix:$scope.prix,numeros:$scope.numeros,description:$scope.description,garantie:filterDateu($scope.garantie)};$http.post(urlp+'produit/'+$scope.idm,JSON.stringify(data)).then(function(response){if(response.data){getData();restForm();$scope.resp=true;$scope.msg="Le produit "+$scope.label+" ajouter";}},function(response){$scope.msg="Le produit "+$scope.label+" non ajouter";$scope.resp=false;$scope.statusval=response.status;$scope.statustext=response.statusText;$scope.headers=response.headers();});}else{var data={id:$scope.id,label:$scope.label,marque:$scope.marque,model:$scope.model,prix:$scope.prix,numeros:$scope.numeros,description:$scope.description,garantie:filterDateu($scope.garantie)};$http.put(urlp+'produits/'+$scope.id+'/'+$scope.idm,JSON.stringify(data)).then(function(response){if(response.data){getData();$scope.resp=true;$scope.msg="Le produit Modefier";}},function(response){$scope.msg="Error";getData();$scope.resp=false;$scope.statusval=response.status;$scope.statustext=response.statusText;$scope.headers=response.headers();});$scope.id=-1;}};$scope.delet=function(ids){if(ids<0){$http.delete(urlp+"produits/"+idd).then(function(response){getData();},function(response){$scope.merror="Désolé impossible de supprimer le produit";$('#ErrorModal').modal('show');});}else{pro=filterProd(ids);$scope.msgsup="Voulez-vous vraiment supprimer le produit "+pro.label+"?";idd=ids;}};function filterDate(date){var dates=date.split("-");return dates[2]+"/"+dates[1]+"/"+dates[0];}; function filterDateu(date){if(date){var dates=date.split("/");return dates[2]+"-"+dates[1]+"-"+dates[0];}else{return null;}}; function filterProd(ids){return($filter('filter')($scope.produits.content,{id:ids})[0]);} getData();});