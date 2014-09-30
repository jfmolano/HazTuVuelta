define(['model/_hazTuVueltaMasterModel'], function() { 
    App.Model.HazTuVueltaMasterModel = App.Model._HazTuVueltaMasterModel.extend({

    });

    App.Model.HazTuVueltaMasterList = App.Model._HazTuVueltaMasterList.extend({
        model: App.Model.HazTuVueltaMasterModel
    });

    return  App.Model.HazTuVueltaMasterModel;

});