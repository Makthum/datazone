(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('inwardController', inwardController);

    inwardController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function inwardController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        
        vm.today= function() {
            vm.dt = new Date();
          };

          vm.clear=function() {
            vm.dt = null;
          };

          vm.inlineOptions = {
            minDate: new Date(),
            showWeeks: true
          };

          vm.dateOptions = {
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            minDate: new Date(2016, 1, 1),
            startingDay: 1
          };

          vm.items = [{
        	  'date' : vm.dt,
        	  'scrapType' : vm.scrapType,
        	  'quantity' : vm.Quantity,
        	  'opened': false
          }]
          
          vm.addNew = function (){
        	  vm.items.push ({
        		  'date' : new Date(),
        		  'scrapType' : " ",
        		  'quantity' :0,
        		  'opened' : false
        	  })
          }
          vm.open1= function(count) {
            vm.items[count].opened = true;
          };

         
          vm.setDate= function (year, month, day) {
            vm.dt = new Date(year, month, day);
          };

          vm.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
          vm.format = vm.formats[0];
          vm.altInputFormats = ['M!/d!/yyyy'];

          vm.popup1 = {
            opened: false
          };
    }
    
    



        
    
})();