(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-report', {
            parent: 'entity',
            url: '/user-report?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'seguridMapApp.userReport.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-report/user-reports.html',
                    controller: 'UserReportController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userReport');
                    $translatePartialLoader.addPart('genero');
                    $translatePartialLoader.addPart('genero');
                    $translatePartialLoader.addPart('estadoReporte');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-report-detail', {
            parent: 'entity',
            url: '/user-report/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'seguridMapApp.userReport.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-report/user-report-detail.html',
                    controller: 'UserReportDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userReport');
                    $translatePartialLoader.addPart('genero');
                    $translatePartialLoader.addPart('genero');
                    $translatePartialLoader.addPart('estadoReporte');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserReport', function($stateParams, UserReport) {
                    return UserReport.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-report',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-report-detail.edit', {
            parent: 'user-report-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-report/user-report-dialog.html',
                    controller: 'UserReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserReport', function(UserReport) {
                            return UserReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-report.new', {
            parent: 'user-report',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-report/user-report-dialog.html',
                    controller: 'UserReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                anonimo: null,
                                apellidoPaternoDenunciante: null,
                                apellidoMaternoDenunciante: null,
                                genero: null,
                                correoDenunciante: null,
                                telefonoDenunciante: null,
                                domicilioDenunciante: null,
                                cpDenunciante: null,
                                nombreDenunciado: null,
                                apellidoPaternoDenunciado: null,
                                apellidoMaternoDenunciado: null,
                                sobrenombreDenunciado: null,
                                generoDenunciado: null,
                                descripcionDenunciado: null,
                                delito: null,
                                horarioDelito: null,
                                diaDelito: null,
                                calleDelito: null,
                                numeroExtDelito: null,
                                numIntDelito: null,
                                callePrincipalDelito: null,
                                calleCruceDelito: null,
                                descripcionDomicilioDelito: null,
                                narracionDelito: null,
                                policia: null,
                                fechaDelitoPolicia: null,
                                horaAproximadaDelitoPolicia: null,
                                municipioPolicia: null,
                                corporacionPolicia: null,
                                numeroUnidadPolicia: null,
                                colorUnidadaPolicia: null,
                                placasPolicia: null,
                                nombrePolicia: null,
                                aliasPolicia: null,
                                domicilioPolicia: null,
                                descripcionHechosPolicia: null,
                                estadoReporte: null,
                                nombreDenunciante: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-report', null, { reload: 'user-report' });
                }, function() {
                    $state.go('user-report');
                });
            }]
        })
        .state('user-report.edit', {
            parent: 'user-report',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-report/user-report-dialog.html',
                    controller: 'UserReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserReport', function(UserReport) {
                            return UserReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-report', null, { reload: 'user-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-report.delete', {
            parent: 'user-report',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-report/user-report-delete-dialog.html',
                    controller: 'UserReportDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserReport', function(UserReport) {
                            return UserReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-report', null, { reload: 'user-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
