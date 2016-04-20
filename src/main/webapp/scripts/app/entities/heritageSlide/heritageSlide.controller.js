'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageSlideController', function ($scope, $state, $http,$uibModal, $location, Principal,  leafletData , HeritageSlide,HeritageWalk, HeritageMedia, ParseLinks, sharedGeoProperties) {

        $scope.heritageSlides = [];
        $scope.slideList = [];
        $scope.selected=null;
        
        
        $scope.heritageSlide = {
                indexVal: null,
                notes: null,
                url:null,
                title:null,
                description:null,
                media:null,
                lat:0,
                lng :0,
                id: null
       };
        
        $scope.heritageOrderedSlide = {
                indexVal: null,
                notes: null,
                url: null,
                title: null,
                description: null,
                media: null,
                mediasymbol : null,
                lat:0,
                lng :0,
             
                id: null
            };
        $scope.predicate = 'id';
        $scope.account = null;
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HeritageSlide.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.heritageSlides = result;
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
            $scope.heritageSlide = {
                indexVal: null,
                notes: null,
                url:null,
                title:null,
                lat:0,
                lng:0,
                id: null
            };
        };
        
        
   
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;         
                 
            
        });
  ///slide config

 
        $scope.heritagemedias = HeritageMedia.query();
        $scope.heritagewalks = HeritageWalk.query();
        //slide config
        
        
        
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
             aurovillecenter: { //12.007127, 79.810771
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
    	 
    	 ////
    	 

         //$http.get("https://a.tiles.mapbox.com/v4/feelcreative.llm8dpdk/features.json?access_token=pk.eyJ1IjoibXVyYWxpaHI3NyIsImEiOiJjaWo5c2tqZjYwMDNtdXhseGFqeHlsZnQ4In0.W_DdV-qM8lNZzacVotHDEA").success(function(data) {
    	 $http.get("api/mapp").success(function(data) {
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
         $scope.modalInstance  = null;
    
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
         
         $scope.newIndex=0;
         $scope.heritageSlide.notes ="";
         $scope.heritageSlide.heritageMediaId="";
         $scope.heritageSlide.userId=null;
         $scope.heritageSlide.heritageWalkId = null;
         $scope.newMedia=0;
     	 $scope.newWalk=0;
     	 $scope.newUser=0;
    	 ///
    	 $scope.$on('leafletDirectiveGeoJson.click', function(event, args){
    		 console.log("click on leafletDirectiveMarker"+event);
    		 
    		 //initialize everything here ;
    		 var markerName = args.leafletEvent.target.options.name; //has to be set above
    		 var portfolioURL = args.model.properties.url;
             $scope.titleSelected  =  args.model.properties.title;
            console.log("$scope. $scope.heritageSlide.heritageMediaId"+ $scope.heritageSlide.heritageMediaId);
             console.log("$scope.titleSelected"+$scope.titleSelected);
             $scope.descrSelected = args.model.properties.description;;
             $scope.urlLink  = args.model.properties.url; 
              
             console.log("  $scope.urlLink"+  $scope.urlLink);
             $scope.media=args.model.properties.mediatype;
             
             $scope.heritageSlide.url = args.model.properties.url;
             $scope.heritageSlide.media =args.model.properties.mediatype;
             $scope.heritageSlide.userId = $scope.account.id;

    		 $scope.heritageSlide.indexVal = $scope.heritageSlide.indexVal +1; 
    		 $scope.heritageSlide.title =  args.model.properties.title;;
    		 $scope.heritageSlide.url = args.model.properties.url; 
               
             $scope.heritageSlide.heritageMediaId =  args.model.id;
             $scope.heritageSlide.lng =  args.model.geometry.coordinates[0];
             $scope.heritageSlide.lat =  args.model.geometry.coordinates[1];
             
             
             
             ///
             
             
             //based on the media type change the template url;
    		 
             $scope.modalInstance = $uibModal.open({
    			 
    			 		templateUrl: 'scripts/app/entities/heritageSlide/showslidetemplate.html',
                    
    			 		scope: $scope, //passed current scope to the modal
                        size: 'lg',
                        resolve: {
                            entity: function () {
                            	  return {
                                      indexVal: null,
                                      notes: null,
                                      url:null,
                                      title:null,
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
        $scope.listOfPoints = 
        	[
                               
                { title: "", description: "", Address : "", Latitude : 0,Longitude :0,UrlOrfileLink :""} 
                               
            ];
        
           
        	
        	 $scope.entity = function () {
                  return {
                      indexVal: null,
                      notes: null,
                      id: null
                  };
              };
             $scope.heritageSlide = $scope.entity;
             $scope.convertListToGeoJSONAndSave = function()
             { 
            	var geojson2 = GeoJSON.parse($scope.slideList, {Point: ['lat', 'lng']});            	
            	sharedGeoProperties.setGeoJSON(geojson2);
            	//
            	 
             }

            $scope.addSlideToList = function()
            {            	
            	  
                var newSlide = new function() {
                    
                	this.title = $scope.heritageSlide.title;
                	this.description = $scope.heritageSlide.description;
                	this.url = $scope.heritageSlide.url;
                	this.media = $scope.heritageSlide.media;
                	this.notes = $scope.heritageSlide.notes;
                	this.newIndex = $scope.heritageSlide.indexVal;
                	this.lat = $scope.heritageSlide.lat;
                	this.lng = $scope.heritageSlide.lng;                		
                    this.newMedia    = $scope.heritageSlide.heritageMediaId;
        			this.newWalk  =  $scope.heritageSlide.heritageWalkId;
        			this.currentuser =  $scope.account.login;
                    this.id    = '';
                }
                $scope.slideList.push(newSlide);
                $scope.heritageSlide.notes="";
                $scope.heritageSlide.url="";
                $scope.heritageSlide.lat=0;
                $scope.heritageSlide.lng=0;
                $scope.heritageSlide.title="";
                $scope.heritageSlide.heritageMediaId="";
                $scope.modalInstance.dismiss('cancel');
                console.log( " $scope.slideList.length"+$scope.slideList.length);
            }
            $scope.$watch('slideList', function(slideList) {
                $scope.modelAsJson = angular.toJson(slideList, true);
            }, true);
           
        $scope.initBaseURL = function ($location)
        {
        	
        	 $scope.baseUrl = $location.absUrl();
        	 console.log(" $scope.baseUrl"+ $scope.baseUrl);
        }
        
        $scope.initBaseURL($location);
    	 //
    	 // second map
        //Latitude 11.930508703668062       Longitude 79.81968641281128
        
        
  /*
   * 
   *    { title: 'Location A', description: 'AA',url:' ', media: 'VIDEO',walkName: "sss", lat: 11.930508703668062, lng: 79.81968641281128 },
 	    	            { title: 'Location B', description: 'BB',url:' ', media: 'VIDEO',walkName: "sss", lat: 11.930508703668062, lng: 79.91968641281128},
 	    	            { title: 'Location C', description: 'CC',url:' ', media: 'VIDEO',walkName: "sss", lat: 11.930508703668062, lng: 79.71968641281128 }
 	    	          ]
   */
  
        ///
    });
