import { Component, OnInit } from '@angular/core';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {StreakReputationModel} from '../../models/streakReputation.model';

@Component({
  selector: 'app-monthly-streak-table',
  templateUrl: './monthly-streak-table.component.html',
  styleUrls: ['./monthly-streak-table.component.scss'],
})
export class MonthlyStreakTableComponent implements OnInit {
  employeeStreakData: StreakReputationModel[];
  dataKeys: string[];
  tableHeaders =  [
    {title: 'Author Name'},
    {title: '3 month streak'},
    {title: 'rank'}
  ];

  constructor(private service: EmployeeActivityService) {
  }

  ngOnInit() {
    this.service.getStreakData()
        .subscribe((data: StreakReputationModel[]) => {
          this.employeeStreakData = data;
          this.dataKeys = Object.keys(this.employeeStreakData[0]);
        });
  }
}
