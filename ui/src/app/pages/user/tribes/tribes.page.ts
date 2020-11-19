import {Component, OnInit} from '@angular/core';

import {TribesSummeryModel} from '../../../models/tribe-main-page/tribes-summery.model';
import {EmployeeActivityService} from '../../../services/employee-activity/employee-activity.service';

@Component({
    selector: 'app-tribes',
    templateUrl: './tribes.page.html',
    styleUrls: ['./tribes.page.scss'],
})
export class TribesPage implements OnInit {
    tribesList: TribesSummeryModel[];

    constructor(private employeeActivityService: EmployeeActivityService) {
    }

    ngOnInit() {
        this.employeeActivityService.getAllTribesData()
            .subscribe((response: TribesSummeryModel[]) => {
                this.tribesList = [...response];
            });
    }
}
