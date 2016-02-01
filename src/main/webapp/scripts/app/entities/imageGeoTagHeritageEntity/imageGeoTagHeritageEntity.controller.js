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
        
        $scope.lat2 = 0;
        $scope.lat222 = 1110;
        $scope.lng2 = 0;
        
   
        $scope.$on('mapInitialized', function(evt, evtMap) {
        	$scope.map = evtMap;          
        });
        
        $scope.oldMarker;
        
        $scope.addMarker = function (ev) {
            var myLatLng = ev.latLng;
            var lat2 = myLatLng.lat();
            var lng2 = myLatLng.lng();
            console.log(lat2);
            $scope.lat2 = lat2;
            $scope.lng2 = lng2;
            $scope.lat222 =  lng2;
            
            sharedGeoProperties.setLongitude(lng2);
            sharedGeoProperties.setLatitude(lat2);
             
            var latLang = new google.maps.LatLng(lat2, lng2);
           
            if($scope.marker)
            	{
            		$scope.marker.setPosition(latLang);
            	}
            else
            	{
            	
            	 $scope.marker = new google.maps.Marker({
                     map : $scope.map,
                     position : latLang,
                     title : "Add Title"
                   });
            	}
		};
		
		//MAP FUNCTIOSN END
    });
