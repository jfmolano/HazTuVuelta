define(['model/_entidadMasterModel'], function() { 
    App.Model.EntidadMasterModel = App.Model._EntidadMasterModel.extend({

    });

    App.Model.EntidadMasterList = App.Model._EntidadMasterList.extend({
        model: App.Model.EntidadMasterModel
    });

    return  App.Model.EntidadMasterModel;

});