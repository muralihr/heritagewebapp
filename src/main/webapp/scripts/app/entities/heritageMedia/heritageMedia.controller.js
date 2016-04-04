'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageMediaController', function ($scope, $state, DataUtils, HeritageMedia, ParseLinks,Principal,sharedGeoProperties) {

        $scope.heritageMedias = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
   	 	$scope.isAdmin = false;
        $scope.loadAll = function() {
            HeritageMedia.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.heritageMedias = result;
               
                
                Principal.hasAuthority('ROLE_ADMIN')
                .then(function (result) {
                    if (result) {
                    	
                    	
                    	 $scope.isAdmin = true;
                    	 console.log("setting $scope.isAdmin true" );
                    } else {
                    	$scope.isAdmin = false;
                    	console.log("setting $scope.isAdmin false" );
                    	//
                    	
                    
                        
                    }
                });
                
                
                console.log("vaairbale $scope.isAdmin"+$scope.isAdmin);
                
                
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
            $scope.heritageMedia = {
                title: null,
                description: null,
                address: null,
                latitude: null,
                longitude: null,
                mediaType: null,
                mediaFile: null,
                mediaFileContentType: null,
                urlOrfileLink: null,
                consolidatedTags: null,
                userAgent: null,
                uploadTime: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
        
        ///start 

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

        ///end
        
    });
