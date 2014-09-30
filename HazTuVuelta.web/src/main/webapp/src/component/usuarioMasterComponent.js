define(['controller/selectionController', 'model/cacheModel', 'model/usuarioMasterModel', 'component/_CRUDComponent', 'controller/tabController', 'component/usuarioComponent',
 'component/citaComponent'
 ,
 'component/turnoComponent'
 
 ],function(SelectionController, CacheModel, UsuarioMasterModel, CRUDComponent, TabController, UsuarioComponent,
 citasUsComponent
 ,
 turnoUsuarioComponent
 ) {
    App.Component.UsuarioMasterComponent = App.Component.BasicComponent.extend({
        initialize: function() {
            var self = this;
            this.configuration = App.Utils.loadComponentConfiguration('usuarioMaster');
            var uComponent = new UsuarioComponent();
            uComponent.initialize();
            uComponent.render('main');
            Backbone.on(uComponent.componentId + '-post-usuario-create', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-post-usuario-edit', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-pre-usuario-list', function() {
                self.hideChilds();
            });
            Backbone.on('usuario-master-model-error', function(error) {
                Backbone.trigger(uComponent.componentId + '-' + 'error', {event: 'usuario-master-save', view: self, message: error});
            });
            Backbone.on(uComponent.componentId + '-instead-usuario-save', function(params) {
                self.model.set('usuarioEntity', params.model);
                if (params.model) {
                    self.model.set('id', params.model.id);
                } else {
                    self.model.unset('id');
                }
                var citasUsModels = self.citasUsComponent.componentController.citaModelList;
                self.model.set('listcitasUs', []);
                self.model.set('createcitasUs', []);
                self.model.set('updatecitasUs', []);
                self.model.set('deletecitasUs', []);
                for (var i = 0; i < citasUsModels.models.length; i++) {
                    var m =citasUsModels.models[i];
                    var modelCopy = m.clone();
                    if (m.isCreated()) {
                        //set the id to null
                        modelCopy.unset('id');
                        self.model.get('createcitasUs').push(modelCopy.toJSON());
                    } else if (m.isUpdated()) {
                        self.model.get('updatecitasUs').push(modelCopy.toJSON());
                    }
                }
                for (var i = 0; i < citasUsModels.deletedModels.length; i++) {
                    var m = citasUsModels.deletedModels[i];
                    self.model.get('deletecitasUs').push(m.toJSON());
                }
                var turnoUsuarioModels = self.turnoUsuarioComponent.componentController.turnoModelList;
                self.model.set('listturnoUsuario', []);
                self.model.set('createturnoUsuario', []);
                self.model.set('updateturnoUsuario', []);
                self.model.set('deleteturnoUsuario', []);
                for (var i = 0; i < turnoUsuarioModels.models.length; i++) {
                    var m =turnoUsuarioModels.models[i];
                    var modelCopy = m.clone();
                    if (m.isCreated()) {
                        //set the id to null
                        modelCopy.unset('id');
                        self.model.get('createturnoUsuario').push(modelCopy.toJSON());
                    } else if (m.isUpdated()) {
                        self.model.get('updateturnoUsuario').push(modelCopy.toJSON());
                    }
                }
                for (var i = 0; i < turnoUsuarioModels.deletedModels.length; i++) {
                    var m = turnoUsuarioModels.deletedModels[i];
                    self.model.get('deleteturnoUsuario').push(m.toJSON());
                }
                self.model.save({}, {
                    success: function() {
                        Backbone.trigger(uComponent.componentId + '-post-usuario-save', self);
                    },
                    error: function(error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'usuario-master-save', view: self, error: error});
                    }
                });
            });
        },
        renderChilds: function(params) {
            var self = this;
            this.tabModel = new App.Model.TabModel(
                    {
                        tabs: [
                            {label: "Citas Us", name: "citasUs", enable: true},
                            ,
                            {label: "Turno Usuario", name: "turnoUsuario", enable: true},
                        ]
                    }
            );

            this.tabs = new TabController({model: this.tabModel});

            this.tabs.render('tabs');
            App.Model.UsuarioMasterModel.prototype.urlRoot = this.configuration.context;
            var options = {
                success: function() {
					self.citasUsComponent = new citasUsComponent();
                    self.citasUsModels = App.Utils.convertToModel(App.Utils.createCacheModel(App.Model.CitaModel), self.model.get('listcitasUs'));
                    self.citasUsComponent.initialize({
                        modelClass: App.Utils.createCacheModel(App.Model.CitaModel),
                        listModelClass: App.Utils.createCacheList(App.Model.CitaModel, App.Model.CitaList, self.citasUsModels)
                    });
                    self.citasUsComponent.render(self.tabs.getTabHtmlId('citasUs'));
                    Backbone.on(self.citasUsComponent.componentId + '-post-cita-create', function(params) {
                        params.view.currentCitaModel.setCacheList(params.view.citaModelList);
                    });
					self.turnoUsuarioComponent = new turnoUsuarioComponent();
                    self.turnoUsuarioModels = App.Utils.convertToModel(App.Utils.createCacheModel(App.Model.TurnoModel), self.model.get('listturnoUsuario'));
                    self.turnoUsuarioComponent.initialize({
                        modelClass: App.Utils.createCacheModel(App.Model.TurnoModel),
                        listModelClass: App.Utils.createCacheList(App.Model.TurnoModel, App.Model.TurnoList, self.turnoUsuarioModels)
                    });
                    self.turnoUsuarioComponent.render(self.tabs.getTabHtmlId('turnoUsuario'));
                    Backbone.on(self.turnoUsuarioComponent.componentId + '-post-turno-create', function(params) {
                        params.view.currentTurnoModel.setCacheList(params.view.turnoModelList);
                    });
                    self.citasUsToolbarModel = self.citasUsComponent.toolbarModel.set(App.Utils.Constans.referenceToolbarConfiguration);
                    self.citasUsComponent.setToolbarModel(self.citasUsToolbarModel);                    
                    self.turnoUsuarioToolbarModel = self.turnoUsuarioComponent.toolbarModel.set(App.Utils.Constans.referenceToolbarConfiguration);
                    self.turnoUsuarioComponent.setToolbarModel(self.turnoUsuarioToolbarModel);                    
                	
                     
                
                    $('#tabs').show();
                },
                error: function() {
                    Backbone.trigger(self.componentId + '-' + 'error', {event: 'usuario-edit', view: self, id: id, data: data, error: error});
                }
            };
            if (params.id) {
                self.model = new App.Model.UsuarioMasterModel({id: params.id});
                self.model.fetch(options);
            } else {
                self.model = new App.Model.UsuarioMasterModel();
                options.success();
            }


        },
        hideChilds: function() {
            $('#tabs').hide();
        }
    });

    return App.Component.UsuarioMasterComponent;
});