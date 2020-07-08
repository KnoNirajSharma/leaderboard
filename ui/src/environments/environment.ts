export const environment = {
    production: false,
    appVersion: '1.0.0',
    api: {
        version: '1.0.0',
        baseUrl: 'http://34.68.95.196:8000/',
        routes: {
            author: {endpoint: 'reputation', method: 'GET'},
            trends: {endpoint: 'assets/data/trendsData.json', method: 'GET'}
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
    }
};
