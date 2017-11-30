(function() {
    'use strict';

    angular
        .module('jprApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('Scrap Inward Form',{
        	parent: 'app',
        	url:'/scrapInwardForm',
        	data: {
        		authorities: []
        	},
        	views: {
        		'content@': {
                    templateUrl: 'app/scrap/inwardform.html',
                    controller: 'inwardController',
                    controllerAs: 'vm'
        	}
        	}
        });
    }
})();