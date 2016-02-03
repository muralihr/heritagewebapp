'use strict';

angular.module('heritageMapperAppApp')
    .controller('ImageGeoTagHeritageEntityController', function ($scope, $state, DataUtils, ImageGeoTagHeritageEntity, sharedGeoProperties, ParseLinks) {

        $scope.imageGeoTagHeritageEntitys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.marker;
        $scope.map;
        $scope.loadAll = function() {
        	
       
              
             var latLang = new google.maps.LatLng(sharedGeoProperties.getLatitude(), sharedGeoProperties.getLongitude());
        	 
         	 
        	 ;
             ;
            ImageGeoTagHeritageEntity.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.imageGeoTagHeritageEntitys = result;
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
            $scope.imageGeoTagHeritageEntity = {
                title: null,
                description: null,
                address: null,
                latitude: null,
                longitude: null,
                consolidatedTags: null,
                urlOrfileLink: null,
                photo: null,
                photoContentType: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
        ///MAP FOUNCTIONS
      
        
       
        
   
     
        
 
        
 
		
		//MAP FUNCTIOSN START
		
		///leadf let 
        
        $scope.lat2 = 0;
        
        $scope.lng2 = 0;
		
		angular.extend($scope, {
            center: {
                lat:  12.9326189,
                lng:  77.6733499,
                zoom: 14
            },
            
            markers: {
            	 centerMarker: {
                     lat: 12.9326189,
                     lng: 77.6733499,
                     message: "I want to Tag here!",
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
   	 
    });
