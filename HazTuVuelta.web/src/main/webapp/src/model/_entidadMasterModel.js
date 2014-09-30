define([], function() {
    App.Model._EntidadMasterModel = Backbone.Model.extend({
		initialize: function() {
            this.on('invalid', function(model,error) {
                Backbone.trigger('entidad-master-model-error', error);
            });
        },
        validate: function(attrs, options){
        	var modelMaster = new App.Model.EntidadModel();
        	if(modelMaster.validate){
            	return modelMaster.validate(attrs.entidadEntity,options);
            }
        }
    });

    App.Model._EntidadMasterList = Backbone.Collection.extend({
        model: App.Model._EntidadMasterModel,
        initialize: function() {
        }

    });
    return App.Model._EntidadMasterModel;
    
});