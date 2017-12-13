(function() {
    'use strict';

    angular
        .module('jprApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('issueCreateForm',{
        	parent: 'app',
        	url:'/issueCreateForm',
        	data: {
        		authorities: []
        	},
        	views: {
        		'content@': {
                    templateUrl: 'app/issue/issueCreateForm.html',
                    controller: 'issueController',
                    controllerAs: 'vm'
        	}
        	}
        });
    }
})();