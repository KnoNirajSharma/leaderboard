import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthorModel} from '../models/author.model';
import {environment} from '../../environments/environment';
import {KnolderDetailsModel} from '../models/knolder-details.model';
import {TrendsModel} from '../models/trends.model';

@Injectable({
    providedIn: 'root'
})
export class EmployeeActivityService {
    private url = `${environment.api.baseUrl}${environment.api.routes.author.endpoint}`;
    mockTrendUrl = environment.api.routes.trends.endpoint;

    constructor(private httpClient: HttpClient) {
    }

    getData(): Observable<AuthorModel[]> {
        return this.httpClient.get<AuthorModel[]>(this.url);
    }

    getAllTimeDetails(id: number): Observable<KnolderDetailsModel> {
        return this.httpClient.get<KnolderDetailsModel>(this.url + '/' + id);
    }

    getMonthlyDetails(id: number, month: string, year: number): Observable<KnolderDetailsModel> {
        return this.httpClient.get<KnolderDetailsModel>(this.url + '/' + id + '?month=' + month + '&year=' + year);
    }

    getTrendsData(): Observable<TrendsModel[]> {
        return this.httpClient.get<TrendsModel[]>(this.mockTrendUrl);
    }
}
