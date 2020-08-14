export const environment = {
  production: false,
  appVersion: '1.0.0',
  api: {
    version: '1.0.0',
    baseUrl: 'http://35.232.185.162:8000/',
    routes: {
      author: { endpoint: 'reputation', method: 'GET' },
      trends: { endpoint: 'reputation/twelvemonths', method: 'GET' }
    }
  },
  ngxChartOptions: {
    verticalBarChart: {
      barPadding: 16,
      yAxisLabel: 'score',
    },
    chartColorScheme: {
      domain: ['#2C42A5', '#4CA52C', '#C7AD05', '#224A4B', '#0B8D84', '#2CA1A5', '#2F3640']
      // domain: ['#15509e', '#1862c6', '#1a81ff', '#3a84e6', '#5ca6ff', '#d2e3f9']
    }
  },
  firebaseConfig: {
    apiKey: 'AIzaSyDhPeGTkkQGw28-cptvC305P7rH7M2X9bc',
    authDomain: 'knoldus-leaderboard.firebaseapp.com',
    databaseURL: 'https://knoldus-leaderboard.firebaseio.com',
    projectId: 'knoldus-leaderboard',
    storageBucket: 'knoldus-leaderboard.appspot.com',
    messagingSenderId: '394282533183',
    appId: '1:394282533183:web:6a016df4558e387bfd938a',
    measurementId: 'G-ETLJGR5H9B'
  }
};
