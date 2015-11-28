module.exports = function(config){
  config.set({

    basePath : './',

    files : [
    'http://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.min.js',
    'https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js',
    'http://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-mocks.js',
    'bower_components/angular-bootstrap/ui-bootstrap.js',
    'bower_components/angular-animate/angular-animate.js',
    'components/*.js',
    'view1/*.js',
    'view2/*.js',
    'view3/*.js',
    'view4/*.js',
    'opendata/*.js',
    'register/*.js',
    'app.js',
    'test/*.js'
    ],

    autoWatch : true,

    frameworks: ['jasmine'],

    browsers : ['Chrome'],

    plugins : [
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine',
            'karma-junit-reporter'
            ],

    junitReporter : {
      outputFile: 'test_out/unit.xml',
      suite: 'unit'
    },
    singleRun: false

  });
};
