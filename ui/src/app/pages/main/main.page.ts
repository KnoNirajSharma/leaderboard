import { Component, OnInit } from '@angular/core';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {EmployeeModel} from '../../models/employee.model';

@Component({
  selector: 'app-main',
  templateUrl: './main.page.html',
  styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {

  constructor(private employeeActivityService: EmployeeActivityService) { }
  employeeData: EmployeeModel[];
  ngOnInit() {
    this.employeeActivityService.getData().subscribe((data: EmployeeModel[]) => {
      this.employeeData = data;
        });
  }

}
