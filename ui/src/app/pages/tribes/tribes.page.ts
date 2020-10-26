import { Component, OnInit } from '@angular/core';
import { TribesSummeryModel } from '../../models/tribes-summery.model';
import { EmployeeActivityService } from '../../services/employee-activity.service';

@Component({
  selector: 'app-tribes',
  templateUrl: './tribes.page.html',
  styleUrls: ['./tribes.page.scss'],
})
export class TribesPage implements OnInit {
  tribesList: TribesSummeryModel[];

  constructor(private employeeActivityService: EmployeeActivityService) {}

  ngOnInit() {
    this.employeeActivityService.getAllTribesData()
      .subscribe((response: TribesSummeryModel[]) => {
        this.tribesList = [...response];
      });
  }
}
