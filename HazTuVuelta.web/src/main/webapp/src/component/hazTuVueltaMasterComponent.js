define(['controller/selectionController', 'model/cacheModel', 'model/hazTuVueltaMasterModel', 'component/_CRUDComponent', 'controller/tabController', 'component/hazTuVueltaComponent',
 'component/usuarioComponent'
 ,
 'component/entidadComponent'
 
 ],function(SelectionController, CacheModel, HazTuVueltaMasterModel, CRUDComponent, TabController, HazTuVueltaComponent,
 usuariosHtvComponent
 ,
 tiendaOPComponent
 ) {
    App.Component.HazTuVueltaMasterComponent = App.Component.BasicComponent.extend({
        initialize: function() {
            var self = this;
            this.configuration = App.Utils.loadComponentConfiguration('hazTuVueltaMaster');
            var uComponent = new HazTuVueltaComponent();
            uComponent.initialize();
            uComponent.render('main');
            Backbone.on(uComponent.componentId + '-post-hazTuVuelta-create', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-post-hazTuVuelta-edit', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-pre-hazTuVuelta-list', function() {
                self.hideChilds();
            });
            Backbone.on('hazTuVuelta-master-model-error', function(error) {
                Backbone.trigger(uComponent.componentId + '-' + 'error', {event: 'hazTuVuelta-master-save', view: self, message: error});
            });
            Backbone.on(uComponent.componentId + '-instead-hazTuVuelta-save', function(params) {
                self.model.set('hazTuVueltaEntity', params.model);
                if (params.model) {
                    self.model.set('id', params.model.id);
                } else {
                    self.model.unset('id');
                }
                var usuariosHtvModels = self.usuariosHtvComponent.componentController.usuarioModelList;
                self.model.set('listusuariosHtv', []);
                self.model.set('createusuariosHtv', []);
                self.model.set('updateusuariosHtv', []);
                self.model.set('deleteusuariosHtv', []);
                for (var i = 0; i < usuariosHtvModels.models.length; i++) {
                    var m =usuariosHtvModels.models[i];
                    var modelCopy = m.clone();
                    if (m.isCreated()) {
                        //set the id to null
                        modelCopy.unset('id');
                        self.model.get('createusuariosHtv').push(modelCopy.toJSON());
                    } else if (m.isUpdated()) {
                        self.model.get('updateusuariosHtv').push(modelCopy.toJSON());
                    }
                }
                for (var i = 0; i < usuariosHtvModels.deletedModels.length; i++) {
                    var m = usuariosHtvModels.deletedModels[i];
                    self.model.get('deleteusuariosHtv').push(m.toJSON());
                }
                var tiendaOPModels = self.tiendaOPComponent.componentController.entidadModelList;
                self.model.set('listtiendaOP', []);
                self.model.set('createtiendaOP', []);
                self.model.set('updatetiendaOP', []);
                self.model.set('deletetiendaOP', []);
                for (var i = 0; i < tiendaOPModels.models.length; i++) {
                    var m =tiendaOPModels.models[i];
                    var modelCopy = m.clone();
                    if (m.isCreated()) {
                        //set the id to null
                        modelCopy.unset('id');
                        self.model.get('createtiendaOP').push(modelCopy.toJSON());
                    } else if (m.isUpdated()) {
                        self.model.get('updatetiendaOP').push(modelCopy.toJSON());
                    }
                }
                for (var i = 0; i < tiendaOPModels.deletedModels.length; i++) {
                    var m = tiendaOPModels.deletedModels[i];
                    self.model.get('deletetiendaOP').push(m.toJSON());
                }
                self.model.save({}, {
                    success: function() {
                        Backbone.trigger(uComponent.componentId + '-post-hazTuVuelta-save', self);
                    },
                    error: function(error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'hazTuVuelta-master-save', view: self, error: error});
                    }
                });
            });
        },
        renderChilds: function(params) {
            var self = this;
            this.tabModel = new App.Model.TabModel(
                    {
                        tabs: [
                            {label: "Usuarios Htv", name: "usuariosHtv", enable: true},
                            ,
                            {label: "Tienda O P", name: "tiendaOP", enable: true},
                        ]
                    }
            );

            this.tabs = new TabController({model: this.tabModel});

            this.tabs.render('tabs');
            App.Model.HazTuVueltaMasterModel.prototype.urlRoot = this.configuration.context;
            var options = {
                success: function() {
					self.usuariosHtvComponent = new usuariosHtvComponent();
                    self.usuariosHtvModels = App.Utils.convertToModel(App.Utils.createCacheModel(App.Model.UsuarioModel), self.model.get('listusuariosHtv'));
                    self.usuariosHtvComponent.initialize({
                        modelClass: App.Utils.createCacheModel(App.Model.UsuarioModel),
                        listModelClass: App.Utils.createCacheList(App.Model.UsuarioModel, App.Model.UsuarioList, self.usuariosHtvModels)
                    });
                    self.usuariosHtvComponent.render(self.tabs.getTabHtmlId('usuariosHtv'));
                    Backbone.on(self.usuariosHtvComponent.componentId + '-post-usuario-create', function(params) {
                        params.view.currentUsuarioModel.setCacheList(params.view.usuarioModelList);
                    });
					self.tiendaOPComponent = new tiendaOPComponent();
                    self.tiendaOPModels = App.Utils.convertToModel(App.Utils.createCacheModel(App.Model.EntidadModel), self.model.get('listtiendaOP'));
                    self.tiendaOPComponent.initialize({
                        modelClass: App.Utils.createCacheModel(App.Model.EntidadModel),
                        listModelClass: App.Utils.createCacheList(App.Model.EntidadModel, App.Model.EntidadList, self.tiendaOPModels)
                    });
                    self.tiendaOPComponent.render(self.tabs.getTabHtmlId('tiendaOP'));
                    Backbone.on(self.tiendaOPComponent.componentId + '-post-entidad-create', function(params) {
                        params.view.currentEntidadModel.setCacheList(params.view.entidadModelList);
                    });
                    self.usuariosHtvToolbarModel = self.usuariosHtvComponent.toolbarModel.set(App.Utils.Constans.referenceToolbarConfiguration);
                    self.usuariosHtvComponent.setToolbarModel(self.usuariosHtvToolbarModel);                    
                    self.tiendaOPToolbarModel = self.tiendaOPComponent.toolbarModel.set(App.Utils.Constans.referenceToolbarConfiguration);
                    self.tiendaOPComponent.setToolbarModel(self.tiendaOPToolbarModel);                    
                	
                     
                
                    $('#tabs').show();
                },
                error: function() {
                    Backbone.trigger(self.componentId + '-' + 'error', {event: 'hazTuVuelta-edit', view: self, id: id, data: data, error: error});
                }
            };
            if (params.id) {
                self.model = new App.Model.HazTuVueltaMasterModel({id: params.id});
                self.model.fetch(options);
            } else {
                self.model = new App.Model.HazTuVueltaMasterModel();
                options.success();
            }


        },
        hideChilds: function() {
            $('#tabs').hide();
        }
    });

    return App.Component.HazTuVueltaMasterComponent;
});