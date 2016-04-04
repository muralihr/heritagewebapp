'use strict';

angular.module('heritageMapperAppApp').controller('HeritageMediaDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'HeritageMedia', 'HeritageCategory', 'HeritageLanguage', 'sharedGeoProperties','HeritageGroup', 'HeritageApp', 'User',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, HeritageMedia, HeritageCategory, HeritageLanguage, sharedGeoProperties, HeritageGroup, HeritageApp, User) {

        $scope.heritageMedia = entity;
        

        $scope.heritageMedia.latitude = sharedGeoProperties.getLatitude(); 
        $scope.heritageMedia.longitude = sharedGeoProperties.getLongitude();  
        $scope.heritagecategorys = HeritageCategory.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        $scope.heritagegroups = HeritageGroup.query();
        $scope.heritageapps = HeritageApp.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            HeritageMedia.get({id : id}, function(result) {
                $scope.heritageMedia = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritagemapperappApp:heritageMediaUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageMedia.id != null) {
                HeritageMedia.update($scope.heritageMedia, onSaveSuccess, onSaveError);
            } else {
                HeritageMedia.save($scope.heritageMedia, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setMediaFile = function ($file, heritageMedia) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        heritageMedia.mediaFile = base64Data;
                        heritageMedia.mediaFileContentType = $file.type;                      
                        
                        heritageMedia.urlOrfileLink = $file.name;                        
                        heritageMedia.uploadTime = new  Date();
                        heritageMedia.userAgent = BrowserDetect.browser;
                    });
                };
            }
        };
        $scope.datePickerForUploadTime = {};

        $scope.datePickerForUploadTime.status = {
            opened: false
        };

        $scope.datePickerForUploadTimeOpen = function($event) {
            $scope.datePickerForUploadTime.status.opened = true;
        };
        
        ///brower detect
        

        var BrowserDetect = {
            init: function () {
                this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
                this.version = this.searchVersion(navigator.userAgent)
                    || this.searchVersion(navigator.appVersion)
                    || "an unknown version";
                this.OS = this.searchString(this.dataOS) || "an unknown OS";
            },
            searchString: function (data) {
                for (var i=0;i<data.length;i++)    {
                    var dataString = data[i].string;
                    var dataProp = data[i].prop;
                    this.versionSearchString = data[i].versionSearch || data[i].identity;
                    if (dataString) {
                        if (dataString.indexOf(data[i].subString) != -1)
                            return data[i].identity;
                    }
                    else if (dataProp)
                        return data[i].identity;
                }
            },
            searchVersion: function (dataString) {
                var index = dataString.indexOf(this.versionSearchString);
                if (index == -1) return;
                return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
            },
            dataBrowser: [
                {
                    string: navigator.userAgent,
                    subString: "Chrome",
                    identity: "Chrome"
                },
                {     string: navigator.userAgent,
                    subString: "OmniWeb",
                    versionSearch: "OmniWeb/",
                    identity: "OmniWeb"
                },
                {
                    string: navigator.vendor,
                    subString: "Apple",
                    identity: "Safari",
                    versionSearch: "Version"
                },
                {
                    prop: window.opera,
                    identity: "Opera",
                    versionSearch: "Version"
                },
                {
                    string: navigator.vendor,
                    subString: "iCab",
                    identity: "iCab"
                },
                {
                    string: navigator.vendor,
                    subString: "KDE",
                    identity: "Konqueror"
                },
                {
                    string: navigator.userAgent,
                    subString: "Firefox",
                    identity: "Firefox"
                },
                {
                    string: navigator.vendor,
                    subString: "Camino",
                    identity: "Camino"
                },
                {        // for newer Netscapes (6+)
                    string: navigator.userAgent,
                    subString: "Netscape",
                    identity: "Netscape"
                },
                {
                    string: navigator.userAgent,
                    subString: "MSIE",
                    identity: "Explorer",
                    versionSearch: "MSIE"
                },
                {
                    string: navigator.userAgent,
                    subString: "Gecko",
                    identity: "Mozilla",
                    versionSearch: "rv"
                },
                {         // for older Netscapes (4-)
                    string: navigator.userAgent,
                    subString: "Mozilla",
                    identity: "Netscape",
                    versionSearch: "Mozilla"
                }
            ],
            dataOS : [
                {
                    string: navigator.platform,
                    subString: "Win",
                    identity: "Windows"
                },
                {
                    string: navigator.platform,
                    subString: "Mac",
                    identity: "Mac"
                },
                {
                       string: navigator.userAgent,
                       subString: "iPhone",
                       identity: "iPhone/iPod"
                },
                {
                    string: navigator.platform,
                    subString: "Linux",
                    identity: "Linux"
                }
            ]

        };
        BrowserDetect.init();
        
        ///browser
}]);
