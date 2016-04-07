'use strict';

angular.module('heritageMapperAppApp')
    .controller('GroupsController', function ($scope,$http,$state, Principal, HeritageGroup, Auth, Language, $translate) {
        $scope.success = false;
        $scope.error = false;
        $scope.errorGroupExists = false;
        $scope.groupId= null;
        $scope.reason =' ';

        $scope.showmsg=false;
        $scope.myheritagegroups = {};
        $scope.heritagegroups = HeritageGroup.query();
        
        //api/getUserGroups/user/kikiki
        Principal.identity().then(function(account) {
            $scope.settingsAccount = copyAccount(account);
            
            var ugetgroups =  'api/getUserGroups/user/'+ $scope.settingsAccount.login;
            $http.get(ugetgroups).success(function(data, status, headers, config) {
                $scope.myheritagegroups = data;
                console.log( $scope.myheritagegroups);
            });
            
             
        });

        
        $scope.registerToGroup = function () {
            // use $.param jQuery function to serialize data from JSON 
             var data = $.param({
                 reason: $scope.reason 
             });
         
             var config = {
                 headers : {
                     'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
                 }
             }
///registerToGroup/user/{userId}/group/{groupId}
             var url = 'api/registerToGroup/user/'+ $scope.settingsAccount.login+'/group/'+$scope.groupId;
             $http.post(url, data, config)
             .success(function (data, status, headers, config) {
            	 console.log(status);
            	 $scope.showmsg=true;
            	 $scope.success = true;
            	 if(status == 200)
            		 {
            		 $scope.success = true;
            		 $scope.showmsg=true;
            			console.log('  Added to Group'+$scope.showmsg);
            			 alert("  Added to Group Succesfully");
                		 
            		 }
            	 else 
            		 if(status == 201)
            		 {
            			 $scope.errorGroupExists = true;
            			 alert("You are already a Member of the Group/Add Request Already Made");
            		 
            		 	
            		 	console.log(' GroupExists'+$scope.showmsg);
            		 }
            	 else
            		 if(status == 404)
            		 {
            			 $scope.error  = true;
            			 console.log('  Error adding  to Group'+$scope.showmsg);
            			 
            			 alert(" Error adding  to Group");
            	     }
            	 
            	 console.log(data);
                 $scope.PostDataResponse = data;
                 $state.reload();
             })
             .error(function (data, status, header, config) {
                 $scope.ResponseDetails = "Data: " + data +
                     "<hr />status: " + status +
                     "<hr />headers: " + header +
                     "<hr />config: " + config;
             });
         };
         
        $scope.save = function () {
        	
        	
        	/*
            Auth.updateAccount($scope.settingsAccount).then(function() {
                $scope.error = null;
                $scope.success = 'OK';
                Principal.identity(true).then(function(account) {
                    $scope.settingsAccount = copyAccount(account);
                });
                Language.getCurrent().then(function(current) {
                    if ($scope.settingsAccount.langKey !== current) {
                        $translate.use($scope.settingsAccount.langKey);
                    }
                });
            }).catch(function() {
                $scope.success = null;
                $scope.error = 'ERROR';
            });*/
        };

        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login
            }
        }
    });
