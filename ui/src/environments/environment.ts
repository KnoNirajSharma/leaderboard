export const environment = {
    production: false,
    appVersion: '1.0.0',
    api: {
        version: '1.0.0',
        baseUrl: 'http://34.68.95.196:8000/',
        routes: {
            author: {endpoint: 'reputation', method: 'GET'},
            trends: {endpoint: 'reputation/twelvemonths', method: 'GET'}
        }
    },

    ngxChartOptions: {
        verticalBarChart: {
            barPadding: 16,
            yAxisLabel: 'score',
        },
        chartColorScheme: {
            domain: ['#15509e', '#1862c6', '#1a81ff', '#3a84e6', '#5ca6ff', '#d2e3f9']
        }
    },

    googleClientId: '6056193565-4s9g8pg84i8vanmivgpvpfr1m1s02nm0.apps.googleusercontent.com',

    firebaseConfig : {
        apiKey: 'AIzaSyDd51yg1TGiUvxmz4ARmmQ7pP5KL0RD1Bc',
        authDomain: 'leaderboard-283708.firebaseapp.com',
        databaseURL: 'https://leaderboard-283708.firebaseio.com',
        projectId: 'leaderboard-283708',
        storageBucket: 'leaderboard-283708.appspot.com',
        messagingSenderId: '6056193565',
        appId: '1:6056193565:web:fa81ffed1d776b590720f4',
        measurementId: 'G-1R9PBV6QB1'
    }
};
