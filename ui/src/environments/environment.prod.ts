export const environment = {
  production: true,
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
