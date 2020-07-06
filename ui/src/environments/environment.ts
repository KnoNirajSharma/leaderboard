export const environment = {
    production: false,
    appVersion: '1.0.0',
    api: {
        version: '1.0.0',
        baseUrl: '/',
        routes: {
            author: {endpoint: 'assets/data/authorProfile.json', method: 'GET'},
        }
    },
    pieChartColorScheme: {
        domain: ['#15509e', '#1862c6', '#1a81ff', '#3a84e6', '#5ca6ff', '#d2e3f9']
    }
};
