define([], function() {
    App.Model._HazTuVueltaMasterModel = Backbone.Model.extend({
		initialize: function() {
            this.on('invalid', function(model,error) {
                Backbone.trigger('hazTuVuelta-master-model-error', error);
            });
        },
        validate: function(attrs, options){
        	var modelMaster = new App.Model.HazTuVueltaModel();
        	if(modelMaster.validate){
            	return modelMaster.validate(attrs.hazTuVueltaEntity,options);
            }
        }
    });

    App.Model._HazTuVueltaMasterList = Backbone.Collection.extend({
        model: App.Model._HazTuVueltaMasterModel,
        initialize: function() {
        }

    });
    return App.Model._HazTuVueltaMasterModel;
    
});