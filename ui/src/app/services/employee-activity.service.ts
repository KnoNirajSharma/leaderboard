import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthorModel} from '../models/author.model';
import {environment} from '../../environments/environment';
import {MonthlyReputationModel} from '../models/monthlyReputation.model';
import {StreakReputationModel} from '../models/streakReputation.model';

@Injectable({
    providedIn: 'root'
})
export class EmployeeActivityService {
    private url = environment.api.baseUrl + environment.api.routes.author.endpoint;
    private  monthlyApiUrl = '/assets/data/monthlyAuthorProfile.json';
    private scoreStreakApiUrl =  '/assets/data/streakData.json';
    constructor(private httpClient: HttpClient) {
    }

    getData(): Observable<AuthorModel[]> {
        return this.httpClient.get<AuthorModel[]>(this.url);
    }

    getMonthlyData(): Observable<MonthlyReputationModel[]> {
        return this.httpClient.get<MonthlyReputationModel[]>(this.monthlyApiUrl);
    }

    getStreakData(): Observable<StreakReputationModel[]> {
        return this.httpClient.get<StreakReputationModel[]>(this.scoreStreakApiUrl);
    }
}
