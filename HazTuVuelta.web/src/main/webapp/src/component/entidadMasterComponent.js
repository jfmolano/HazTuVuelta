define(['controller/selectionController', 'model/cacheModel', 'model/entidadMasterModel', 'component/_CRUDComponent', 'controller/tabController', 'component/entidadComponent',
 'component/sedeComponent'
 
 ],function(SelectionController, CacheModel, EntidadMasterModel, CRUDComponent, TabController, EntidadComponent,
 sedeEntidadComponent
 ) {
    App.Component.EntidadMasterComponent = App.Component.BasicComponent.extend({
        initialize: function() {
            var self = this;
            this.configuration = App.Utils.loadComponentConfiguration('entidadMaster');
            var uComponent = new EntidadComponent();
            uComponent.initialize();
            uComponent.render('main');
            Backbone.on(uComponent.componentId + '-post-entidad-create', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-post-entidad-edit', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-pre-entidad-list', function() {
                self.hideChilds();
            });
            Backbone.on('entidad-master-model-error', function(error) {
                Backbone.trigger(uComponent.componentId + '-' + 'error', {event: 'entidad-master-save', view: self, message: error});
            });
            Backbone.on(uComponent.componentId + '-instead-entidad-save', function(params) {
                self.model.set('entidadEntity', params.model);
                if (params.model) {
                    self.model.set('id', params.model.id);
                } else {
                    self.model.unset('id');
                }
                var sedeEntidadModels = self.sedeEntidadComponent.componentController.sedeModelList;
                self.model.set('listsedeEntidad', []);
                self.model.set('createsedeEntidad', []);
                self.model.set('updatesedeEntidad', []);
                self.model.set('deletesedeEntidad', []);
                for (var i = 0; i < sedeEntidadModels.models.length; i++) {
                    var m =sedeEntidadModels.models[i];
                    var modelCopy = m.clone();
                    if (m.isCreated()) {
                        //set the id to null
                        modelCopy.unset('id');
                        self.model.get('createsedeEntidad').push(modelCopy.toJSON());
                    } else if (m.isUpdated()) {
                        self.model.get('updatesedeEntidad').push(modelCopy.toJSON());
                    }
                }
                for (var i = 0; i < sedeEntidadModels.deletedModels.length; i++) {
                    var m = sedeEntidadModels.deletedModels[i];
                    self.model.get('deletesedeEntidad').push(m.toJSON());
                }
                self.model.save({}, {
                    success: function() {
                        Backbone.trigger(uComponent.componentId + '-post-entidad-save', self);
                    },
                    error: function(error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'entidad-master-save', view: self, error: error});
                    }
                });
            });
        },
        renderChilds: function(params) {
            var self = this;
            this.tabModel = new App.Model.TabModel(
                    {
                        tabs: [
                            {label: "Sede Entidad", name: "sedeEntidad", enable: true},
                        ]
                    }
            );

            this.tabs = new TabController({model: this.tabModel});

            this.tabs.render('tabs');
            App.Model.EntidadMasterModel.prototype.urlRoot = this.configuration.context;
            var options = {
                success: function() {
					self.sedeEntidadComponent = new sedeEntidadComponent();
                    self.sedeEntidadModels = App.Utils.convertToModel(App.Utils.createCacheModel(App.Model.SedeModel), self.model.get('listsedeEntidad'));
                    self.sedeEntidadComponent.initialize({
                        modelClass: App.Utils.createCacheModel(App.Model.SedeModel),
                        listModelClass: App.Utils.createCacheList(App.Model.SedeModel, App.Model.SedeList, self.sedeEntidadModels)
                    });
                    self.sedeEntidadComponent.render(self.tabs.getTabHtmlId('sedeEntidad'));
                    Backbone.on(self.sedeEntidadComponent.componentId + '-post-sede-create', function(params) {
                        params.view.currentSedeModel.setCacheList(params.view.sedeModelList);
                    });
                    self.sedeEntidadToolbarModel = self.sedeEntidadComponent.toolbarModel.set(App.Utils.Constans.referenceToolbarConfiguration);
                    self.sedeEntidadComponent.setToolbarModel(self.sedeEntidadToolbarModel);                    
                	
                     
                
                    $('#tabs').show();
                },
                error: function() {
                    Backbone.trigger(self.componentId + '-' + 'error', {event: 'entidad-edit', view: self, id: id, data: data, error: error});
                }
            };
            if (params.id) {
                self.model = new App.Model.EntidadMasterModel({id: params.id});
                self.model.fetch(options);
            } else {
                self.model = new App.Model.EntidadMasterModel();
                options.success();
            }


        },
        hideChilds: function() {
            $('#tabs').hide();
        }
    });

    return App.Component.EntidadMasterComponent;
});