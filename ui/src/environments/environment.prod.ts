export const environment = {
    production: true,
    appVersion: '1.0.0',
    api: {
        version: '1.0.0',
        baseUrl: '/assets/data/authorProfile.json',
        routes: {
            author: {endpoint: 'get/authorData', method: 'GET'}
        }
    }
};
