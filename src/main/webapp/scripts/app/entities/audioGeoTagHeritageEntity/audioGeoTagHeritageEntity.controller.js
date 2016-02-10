'use strict';

angular.module('heritageMapperAppApp')
    .controller('AudioGeoTagHeritageEntityController', function ($scope, $state, Principal, DataUtils,sharedGeoProperties, AudioGeoTagHeritageEntity, ParseLinks) {

        $scope.audioGeoTagHeritageEntitys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.isAdmin = false;
        $scope.loadAll = function() {
            AudioGeoTagHeritageEntity.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.audioGeoTagHeritageEntitys = result;
                

                Principal.hasAuthority('ROLE_ADMIN')
                .then(function (result) {
                    if (result) {
                    	
                    	
                    	 $scope.isAdmin = true;
                    	 console.log("$scope.isAdmin true" );
                    } else {
                    	$scope.isAdmin = false;
                    	console.log("$scope.isAdmin false" );
                    }
                });
                
                
                console.log("$scope.isAdmin"+$scope.isAdmin);
                
         
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.audioGeoTagHeritageEntity = {
                title: null,
                description: null,
                address: null,
                latitude: null,
                longitude: null,
                consolidatedTags: null,
                urlOrfileLink: null,
                audioFile: null,
                audioFileContentType: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
///MAP FOUNCTIONS
        

		//MAP FUNCTIOSN START
		
		///leadf let 
        
      
		
		angular.extend($scope, {
            center: {
                lat:  11.93252413,
                lng:  79.82084512, 
                
                zoom: 15
            },
            
            markers: {
            	 centerMarker: {
            		 lat:  11.93269,
                     lng:  79.8287844, 
                     
                     message: "Click on the map to move the marker to a Specific Location!",
                     focus: true,
                     draggable: true
                 }
            },
            
            events: {
		        markers: {
		            enable: ['click'],
		            logic: 'emit'
		        }
		    },
            tiles: {
                name: 'Mapbox Park',
                url: 'http://api.tiles.mapbox.com/v4/{mapid}/{z}/{x}/{y}.png?access_token={apikey}',
                type: 'xyz',
                options: {
                    apikey: 'pk.eyJ1IjoibXVyYWxpaHI3NyIsImEiOiJjaWo5c2tqZjYwMDNtdXhseGFqeHlsZnQ4In0.W_DdV-qM8lNZzacVotHDEA',
                    mapid: 'mapbox.streets'
                }
            },
            geojson: {}

        });
		
		    $scope.lat2 = $scope.center.lat;	        
	        $scope.lng2 =  $scope.center.lng;
		
		 $scope.$on('leafletDirectiveMap.click', function(event, args){
    		 console.log("click on leafletDirectiveMap"+event);
    		 
    		 var lat = args.leafletEvent.latlng.lat; //has to be set above
    		 var lng = args.leafletEvent.latlng.lng; //has to be set above
    		 
    		 $scope.markers.centerMarker.lat = lat;
    		 $scope.markers.centerMarker.lng = lng;
    		 
    		 $scope.lat2 = lat;
             $scope.lng2 = lng;
          
             
             sharedGeoProperties.setLongitude(lng );
             sharedGeoProperties.setLatitude(lat );
    		 
    		 
		 });
		 ///leaf let end
    });
