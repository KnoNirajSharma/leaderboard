import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {EmployeeModel} from '../models/employee.model';


@Injectable({
  providedIn: 'root'
})
export class EmployeeActivityService {

  private url = '/assets/data/authorProfile.json';

  constructor(private httpClient: HttpClient) {
  }

  getData(): Observable<EmployeeModel[]> {
    return this.httpClient.get<EmployeeModel[]>(this.url);
  }
}


