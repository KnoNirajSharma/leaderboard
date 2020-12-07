import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {environment} from '../../../environments/environment';
import {HallOfFameModel} from '../../models/hallOfFame.model';
import {KnolderDetailsModel} from '../../models/knolder-details.model';
import {ReputationModel} from '../../models/reputation.model';
import {ScoringTableModel} from '../../models/scoring-table.model';
import {TrendsModel} from '../../models/trends.model';
import {TribeDetailsModel} from '../../models/tribe-details.model';
import {TribesSummeryModel} from '../../models/tribes-summery.model';


@Injectable({
    providedIn: 'root',
})
export class EmployeeActivityService {
    private url = `${environment.api.baseUrl}${environment.api.routes.author.endpoint}`;
    private trendUrl = `${environment.api.baseUrl}${environment.api.routes.trends.endpoint}`;
    private hallOfFameUrl = `${environment.api.baseUrl}${environment.api.routes.hallOfFame.endpoint}`;
    private scoringInfoUrl = `${environment.api.baseUrl}${environment.api.routes.dynamicScoring.endpoint}`;
    private allTribesUrl = '/assets/data/tribes-main.json';
    private tribeDetailsUrl = '/assets/data/tribe-details.json';

    constructor(private httpClient: HttpClient) {
    }

    getData(): Observable<ReputationModel> {
        return this.httpClient.get<ReputationModel>(this.url);
    }

    getAllTimeDetails(id: number): Observable<KnolderDetailsModel> {
        return this.httpClient.get<KnolderDetailsModel>(this.url + '/' + String(id));
    }

    getMonthlyDetails(id: number, month: string, year: number): Observable<KnolderDetailsModel> {
        return this.httpClient.get<KnolderDetailsModel>(this.url + '/' + String(id) + '?month=' + String(month) + '&year=' + String(year));
    }

    getTrendsData(id: number): Observable<TrendsModel[]> {
        return this.httpClient.get<TrendsModel[]>(this.trendUrl + '/' + String(id));
    }

    getHallOfFameData(): Observable<HallOfFameModel[]> {
        return this.httpClient.get<HallOfFameModel[]>(this.hallOfFameUrl);
    }

    getScoringInfoData(): Observable<ScoringTableModel> {
        return this.httpClient.get<ScoringTableModel>(this.scoringInfoUrl);
    }

    getAllTribesData(): Observable<TribesSummeryModel[]> {
        return this.httpClient.get<TribesSummeryModel[]>(this.allTribesUrl);
    }

    getTribeDetails(tribeId: string): Observable<TribeDetailsModel> {
        return this.httpClient.get<TribeDetailsModel>(this.tribeDetailsUrl);
    }
}
