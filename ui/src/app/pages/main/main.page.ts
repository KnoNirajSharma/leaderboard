import { Component, OnInit } from '@angular/core';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {AuthorModel} from '../../models/author.model';

@Component({
  selector: 'app-main',
  templateUrl: './main.page.html',
  styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {

  constructor(private employeeActivityService: EmployeeActivityService) { }
  employeeData: AuthorModel[];

  ngOnInit() {
    this.employeeActivityService.getData().subscribe((data: AuthorModel[]) => {
      this.employeeData = data;
        });
  }

}
