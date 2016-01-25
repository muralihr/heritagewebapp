'use strict';

angular.module('heritageMapperAppApp')
    .controller('MainController', function ($scope,$http, Principal, HeritageCategory,HeritageLanguage ,ImageGeoTagHeritageEntity,AudioGeoTagHeritageEntity,VideoGeoTagHeritageEntity,TextGeoTagHeritageEntity) {
       
    	
    	
    	Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;         
                 
            
        });
        
        $scope.markerLat = 23.200000;
        $scope.markerLng = 79.225487;
        $scope.infoTitle = 'India';
        
        $scope.heritagecategorys = HeritageCategory.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        
        $scope.imageGeoTagHeritageEntitys = ImageGeoTagHeritageEntity.query();
        $scope.audioGeoTagHeritageEntitys = AudioGeoTagHeritageEntity.query();
        $scope.videoGeoTagHeritageEntitys = VideoGeoTagHeritageEntity.query();
        $scope.textGeoTagHeritageEntitys = TextGeoTagHeritageEntity.query();//TextGeoTagHeritageEntity
        
        
        $scope.listOfPoints = 
        	[
                               
                               { title: "", description: "", Address : "", Latitude : 0,Longitude :0,UrlOrfileLink :""} 
                               
            ];
        
        
        $scope.loadlistOfPoints = function( )
        {
        	
         	console.log("loadlistOfPoints");
        	console.log(  $scope.textGeoTagHeritageEntitys.length);
        	angular.forEach($scope.videoGeoTagHeritageEntitys, function(location, key)
        	{
        		 listOfPoints.push(location);
        		 console.log(location.title);
        		 
        	});

        	angular.forEach($scope.audioGeoTagHeritageEntitys, function(location, key)
                	{
                		 listOfPoints.push(location);
                		 console.log(location.title);
                		 
                	});
                	
        	angular.forEach($scope.textGeoTagHeritageEntitys, function(location, key)
                	{
                		 listOfPoints.push(location);
                		 console.log(location.title);
                		 
                	});
        };
        
        $scope.loadlistOfPoints();
        
        $scope.roles = [
                        'guest', 
                        'user', 
                        'customer', 
                        'admin'
                      ];
                      $scope.user = {
                        roles: []
                      };
                      $scope.checkAll = function() {
                        $scope.user.roles = angular.copy($scope.heritagecategorys);
                      };
                      $scope.uncheckAll = function() {
                        $scope.user.roles = [];
                      };
                      $scope.checkFirst = function() {
                        $scope.user.roles.splice(0, $scope.user.roles.length); 
                        $scope.user.roles.push('guest');
                        console.log("sss");
                      };

        var india = new google.maps.LatLng($scope.markerLat, $scope.markerLng);

        var mapOptions = {
          zoom : 4,
          center : india,
          mapTypeId : google.maps.MapTypeId.TERRAIN
        }

        $scope.marker;
        $scope.map;
        $scope.$on('mapInitialized', function(evt, evtMap) {
        	$scope.map = evtMap;
        	$scope.map.height = 100;
          
        });
        $scope.markers = [];
        
        
        $scope.addOtherMarkers = function(  i )
        {
        	
        	 
        	var ajaxLink ;
        	if(i == 1)
        		{
        			ajaxLink = 'api/textGeoTagHeritageEntitys';
        			var customIcons = {
                       	    
                   	        icon: 'http://icons.iconarchive.com/icons/icons-land/vista-map-markers/48/Map-Marker-Board-Pink-icon.png',
                   	        shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
                   	     
                   	};
        			

    				var src1 =  'http://localhost:8080/audios/' + location.urlOrfileLink ;
    				var imagePopUp  =  '<h2 >'+"aaaa" +'</h2>';
        		}
        		if(i == 2)
        			{
        				ajaxLink = 'api/audioGeoTagHeritageEntitys';
        				var customIcons = {
        	               	    
        	           	        icon: 'http://icons.iconarchive.com/icons/icons-land/vista-map-markers/48/Map-Marker-Marker-Outside-Pink-icon.png',
        	           	        shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
        	           	     
        	           	};
        				var src1 =  'http://localhost:8080/audios/' + location.urlOrfileLink ;
        				var imagePopUp  =  '<iframe  id='+'"'+itemPreviewId+'"'+ "width="+"'"+"300px"+"'"+"height="+"'"+"250px"+"'" +'src='+ src1  +">"+ "</iframe>";
        				
        			}
        			if(i == 3)
        				{
        					ajaxLink = 'api/videoGeoTagHeritageEntitys';
        					var customIcons = {
        		               	    
        		           	        icon: 'http://icons.iconarchive.com/icons/icons-land/vista-map-markers/48/Map-Marker-Marker-Outside-Chartreuse-icon.png',
        		           	        shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
        		           	     
        		           	};
        					var src1 =  'http://localhost:8080/videos/' + location.urlOrfileLink ;
            				var imagePopUp  =  '<iframe  id='+'"'+itemPreviewId+'"'+ "width="+"'"+"300px"+"'"+"height="+"'"+"250px"+"'" +'src='+ src1  +">"+ "</iframe>";
        				}
        			
        			
        	$http.get(ajaxLink).success(function(data) {
        	        	///for each
        	        		 console.log("success" + data);
        	        		 angular.forEach(data, function(location, key){
        	        			 
        	        			 console.log(location+"location");
        	        			 
        	        			 console.log(location.latitude+"location");
        	        			 
        	        			 //prepare a custome pop up window
        	        		     	
        	      	            	var itemPreviewId = "ItemPreview"  + location.id;
        	      	             	
        	      	            	var customPopup = "Title Name" + location.title+"<br/>" +imagePopUp ;
        	      	                  var customOptions =
        	      	                    {
        	      	                    'maxWidth': '500',
        	      	                    'className' : 'custom'
        	      	                    }
        	      	                  
        	      	                var infoWindow = new google.maps.InfoWindow();
        	        			 
        	      	                var latLang = new google.maps.LatLng(location.latitude, location.longitude);
        	        			  
        	      	                var marker = new google.maps.Marker({
        	                       map : $scope.map,
        	                       position : latLang,
        	                       icon: customIcons.icon,
        	                       title : location.title,
        	                       label: 'A'
        	                     });
        	                     marker.content = '<div class="infoWindowContent">'
        	   	                    + customPopup + '</div>';
        	                     
        	                     
        	                     google.maps.event.addListener(marker, 'click', (function (marker ) {
        	                         return function (e) {
        	                        	 infoWindow.setContent('<h2>' + marker.title + '</h2>'
        	            	                      + marker.content);
        	            	                  infoWindow.open($scope.map, marker);
        	                         };
        	                     })(marker ));
        	                     $scope.markers.push(marker);
        	        		 });
        	        	});
        		
        }
        /// 
        $scope.addImageMarkers  =  function(  ){       	
        	
        	//httpp
        	
        	  var customIcons = {
              	    
          	        icon: 'http://icons.iconarchive.com/icons/icons-land/vista-map-markers/48/Map-Marker-Marker-Outside-Azure-icon.png',
          	        shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
          	     
          	};
          
        	$http.get('/api/imageGeoTagHeritageEntitys').success(function(data) {
        	///for each
        		 console.log("success" + data);
        		 angular.forEach(data, function(location, key){
        			 
        			 console.log(location+"location");
        			 
        			 console.log(location.latitude+"location");
        			 
        			 //prepare a custome pop up window
        		     	var src1 =  'data:image/*;base64,' + location.photo;
      	            	var itemPreviewId = "ItemPreview"  + location.id;
      	             	var imagePopUp  =  '<img id='+'"'+itemPreviewId+'"'+ "width="+"'"+"300px"+"'"+"height="+"'"+"250px"+"'" +'src='+ src1  +">"+ "</img>";
      	            	var customPopup = "Title Name" + location.title+"<br/>" +imagePopUp ;
      	                  var customOptions =
      	                    {
      	                    'maxWidth': '500',
      	                    'className' : 'custom'
      	                    }
      	                  
      	                var infoWindow = new google.maps.InfoWindow();
        			 
      	                var latLang = new google.maps.LatLng(location.latitude, location.longitude);
        			  
      	                var marker = new google.maps.Marker({
                       map : $scope.map,
                       position : latLang,
                       icon: customIcons.icon,
                       title : location.title,
                       label: 'A'
                     });
                     marker.content = '<div class="infoWindowContent">'
   	                    + customPopup + '</div>';
                     
                     
                     google.maps.event.addListener(marker, 'click', (function (marker ) {
                         return function (e) {
                        	 infoWindow.setContent('<h2>' + marker.title + '</h2>'
            	                      + marker.content);
            	                  infoWindow.open($scope.map, marker);
                         };
                     })(marker ));
                     $scope.markers.push(marker);
        		 });
        	});

        }
        
      $scope.addMarkers  =  function(  ) 
        {
        	console.log("get markers"); 
        	
        	$scope.addImageMarkers( );	
        	$scope.addOtherMarkers( 1);
        	$scope.addOtherMarkers( 2);
        	$scope.addOtherMarkers( 3);
        		
        	///end function	
  	           
        }
        
        

        
        
		
        
        
        
    });
