'use strict';

angular.module('heritageMapperAppApp')
    .factory('VideoGeoTagHeritageEntity', function ($resource, DateUtils) {
        return $resource('api/videoGeoTagHeritageEntitys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

angular.module('heritageMapperAppApp' )
.service('sharedGeoProperties', function () {
    var longitude = 0;
    var latitude = 0 ;
    
    var geoJSON = null;

    return {
        getLongitude: function () {
            return longitude;
        },
        setLongitude: function(value) {
        	console.log("setLongitude"+value);
        	longitude = value;
        },
        
        getLatitude: function () {
            return latitude;
        },
        setLatitude: function(value) {
        	console.log("setLatitude"+value);
        	latitude = value;
        },        
        getGeoJSON: function () {
            return geoJSON;
        },
        setGeoJSON: function(value) {
        	console.log("setGeoJSON"+value);
        	geoJSON = value;
        }
    };
});




angular.module('heritageMapperAppApp').config(function($sceDelegateProvider) {
	  $sceDelegateProvider.resourceUrlWhitelist(['**']);
	});
 

