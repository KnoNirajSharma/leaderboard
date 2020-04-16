import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthorModel} from '../models/author.model';


@Injectable({
  providedIn: 'root'
})
export class EmployeeActivityService {

  private url = '/assets/data/authorProfile.json';

  constructor(private httpClient: HttpClient) {
  }

  getData(): Observable<AuthorModel[]> {
    return this.httpClient.get<AuthorModel[]>(this.url);
  }
}


