import {Component, OnInit} from '@angular/core';
import {MonthlyReputationModel} from '../../models/monthlyReputation.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';

@Component({
    selector: 'app-monthly-table',
    templateUrl: './monthly-table.component.html',
    styleUrls: ['./monthly-table.component.scss'],
})
export class MonthlyTableComponent implements OnInit {
    monthlyEmployeeData: MonthlyReputationModel[];
    dataKeys: string[];
    tableHeaders =  [
        {title: 'Author Name'},
        {title: 'Monthly Score'},
        {title: 'Monthly Rank'}
    ];

    constructor(private service: EmployeeActivityService) {
    }

    ngOnInit() {
        this.service.getMonthlyData()
            .subscribe((data: MonthlyReputationModel[]) => {
                this.monthlyEmployeeData = data;
                this.dataKeys = Object.keys(this.monthlyEmployeeData[0]);
            });
    }
}
