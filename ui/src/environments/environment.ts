export const environment = {
    production: false,
    appVersion: '1.0.0',
    api: {
        version: '1.0.0',
        baseUrl: 'http://34.68.95.196:8000/',
        routes: {
            author: {endpoint: 'reputation', method: 'GET'},
            monthlyReputation: {endpoint: '/assets/data/monthlyAuthorProfile.json', method: 'GET'},
            streakReputation: {endpoint: '/assets/data/streakData.json', method: 'GET'},
        }
    }
};
