export const environment = {
    production: true,
    appVersion: '1.0.0',
    api: {
        version: '1.0.0',
        baseUrl: 'http://34.68.95.196:8000/',
        routes: {
            author: {endpoint: 'reputation', method: 'GET'},
            monthlyReputation: {endpoint: 'reputation/monthly', method: 'GET'},
            streakReputation: {endpoint: '/assets/data/streakData.json', method: 'GET'},
        }
    }
};
