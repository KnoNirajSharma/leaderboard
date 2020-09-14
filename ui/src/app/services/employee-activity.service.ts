import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { KnolderDetailsModel } from '../models/knolder-details.model';
import { ReputationModel } from '../models/reputation.model';
import { TrendsModel } from '../models/trends.model';
import {HallOfFameModel} from '../models/hallOfFame.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeActivityService {
  private url = `${environment.api.baseUrl}${environment.api.routes.author.endpoint}`;
  private trendUrl = `${environment.api.baseUrl}${environment.api.routes.trends.endpoint}`;
  private hallOfFameUrl = '/assets/data/hofData.json';

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
}
