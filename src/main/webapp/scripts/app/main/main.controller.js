'use strict';

angular.module('heritageMapperAppApp')
    .controller('MainController',['$scope', '$http','$uibModal', '$location', 'Principal','leafletData','HeritageCategory','HeritageLanguage', 
                                  'ImageGeoTagHeritageEntity','AudioGeoTagHeritageEntity','VideoGeoTagHeritageEntity','TextGeoTagHeritageEntity',
                                  function ($scope,$http,$uibModal, $location, Principal,  leafletData , HeritageCategory,HeritageLanguage ,ImageGeoTagHeritageEntity,AudioGeoTagHeritageEntity,VideoGeoTagHeritageEntity,TextGeoTagHeritageEntity) {
       
    	
    	
    	Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;         
                 
            
        });
  
    	///ANGULAR LEAFLET 
    	$scope.map = null;
    	
    	leafletData.getMap("map").then(function(map){
            $scope.map = map;
        });
    	 angular.extend($scope, {
             center: {
                 lat:  12.9326189,
                 lng:  77.6733499,
                 zoom: 14
             },
             aurovillecenter: {
                 lat:  12.006833 ,
                 lng:  79.810513,
                 zoom: 14
             },
             towncenter: {
                 lat:   11.935001,
                 lng:   79.819558,  
                 zoom: 14
             },
             heritagecenter: { //, 
                 lat:  11.936723,
                 lng:  79.786921,
                 zoom: 14
             },
             bahourcenter: { //(11.803506, 79.738941)
                 lat:  11.803506,
                 lng:  79.738941,
                 zoom: 14
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
                     mapid: 'mapbox.pirates'
                 }
             },
             geojson:
             {
            	  
            	 
             }

         });
    	 
     
    	 
    	 

         //$http.get("https://a.tiles.mapbox.com/v4/feelcreative.llm8dpdk/features.json?access_token=pk.eyJ1IjoibXVyYWxpaHI3NyIsImEiOiJjaWo5c2tqZjYwMDNtdXhseGFqeHlsZnQ4In0.W_DdV-qM8lNZzacVotHDEA").success(function(data) {
    	 $http.get("api/allGeoTagHeritageEntitysGeoJson").success(function(data) {
           //  $scope.geojson.data = data;
           //  console.log("geojson data "+data);
    		 
    		 angular.extend($scope, {
    	            geojson: {
    	                data: data,
    	                resetStyleOnMouseout: true,
    	                style: {
    	                    fillColor: "green",
    	                    weight: 2,
    	                    opacity: 1,
    	                    color: 'white',
    	                    dashArray: '3',
    	                    fillOpacity: 0.7
    	                }
    	            }
    	        });
             
             
             //show window 
             
             
         });
    	//LEAFLET 
    	 
    	 //leaf let variables 
    	 
    	 $scope.markerLatSelected;
         $scope.markerLngSelected;
         $scope.titleSelected ="dddd";
         $scope.descrSelected;
         $scope.urlLink;
         $scope.showmsg=false;
         
    
         ///
         
   
         $scope.$on("leafletDirectiveGeoJson.mouseover", function(ev, args) {
              
             $scope.titleSelected  =  args.model.properties.title;;
             console.log("$scope.titleSelected"+$scope.titleSelected);
             $scope.descrSelected = args.model.properties.description;;
             $scope.urlLink  = args.model.properties.url; 
             console.log("  $scope.urlLink"+  $scope.urlLink);
             $scope.media=args.model.properties.mediatype;
             $scope.showmsg=true;
         });
         
         
         $scope.$on("leafletDirectiveGeoJson.mouseout", function(ev, args) {
        	 $scope.showmsg=false;
            
         });
         
        
    	 ///
    	 $scope.$on('leafletDirectiveGeoJson.click', function(event, args){
    		 console.log("click on leafletDirectiveMarker"+event);
    		 
    		 //initialize everything here ;
    		 var markerName = args.leafletEvent.target.options.name; //has to be set above
    		 var portfolioURL = args.model.properties.url;
    		 
             $scope.titleSelected  =  args.model.properties.title;;
             console.log("$scope.titleSelected"+$scope.titleSelected);
             $scope.descrSelected = args.model.properties.description;;
             $scope.urlLink  = args.model.properties.url; 
             console.log("  $scope.urlLink"+  $scope.urlLink);
             $scope.media=args.model.properties.mediatype;
             
             //based on the media type change the template url;
    		 
    		 $uibModal.open({
                      
    			 		templateUrl: 'scripts/app/main/showimagetemplate.html',
                    
    			 		scope: $scope, //passed current scope to the modal
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: "knknkssdnkn",
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
                            }
                        }
                    });
    		  
    		 
     
             
    	 });
 
        
        $scope.markerLat = 23.200000;
        $scope.markerLng = 79.225487;
        $scope.infoTitle = 'India';
        $scope.baseUrl;
        $scope.heritagecategorys = HeritageCategory.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        
       
        
        
        $scope.listOfPoints = 
        	[
                               
                               { title: "", description: "", Address : "", Latitude : 0,Longitude :0,UrlOrfileLink :""} 
                               
            ];
        
        $scope.initBaseURL = function ($location)
        {
        	
        	 $scope.baseUrl = $location.absUrl();
        	 console.log(" $scope.baseUrl"+ $scope.baseUrl);
        }
        
        $scope.initBaseURL($location);
        
         
       
  
        
        
        
 }  ] );
