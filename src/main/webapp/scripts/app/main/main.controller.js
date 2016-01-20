'use strict';

angular.module('heritageMapperAppApp')
    .controller('MainController', function ($scope,$http, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            
          
            
            
            
            
        });
        
        $scope.markerLat = 23.200000;
        $scope.markerLng = 79.225487;
        $scope.infoTitle = 'India';

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
          
        });
        $scope.markers = [];
        
        
        var customIcons = {
        	    
        	        icon: 'http://labs.google.com/ridefinder/images/mm_20_blue.png',
        	        shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
        	     
        	};
        
        
        $scope.addMarkers  =  function( ev) 
        {
        	console.log("get markers"); 
        	
        	
        	///end function
        	
        	//httpp
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
        
  	            // Loop through the 'locations' and place markers on the map
  	        	
  	           
        }
        
        

        
        
		
        
        
        
    });
