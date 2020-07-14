import {Component, OnInit} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {FormControl} from '@angular/forms';
import {EmployeeFilterPipe} from '../../pipe/employee-filter.pipe';
import {ReputationModel} from '../../models/reputation.model';

@Component({
    selector: 'app-main',
    templateUrl: './main.page.html',
    styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
    employeeData: AuthorModel[];
    pageTitle = 'Leaderboard';
    searchBar = new FormControl('');
    empFilterPipe = new EmployeeFilterPipe();
    filteredEmpData: AuthorModel[];
    today: Date = new Date();
    currentDate: Date;
    reputation: ReputationModel;


    constructor(private service: EmployeeActivityService) {
    }

    ngOnInit() {
        this.service.getData()
            .subscribe((data: ReputationModel) => {
                this.reputation = data;
                this.employeeData = this.reputation.reputationData;
                this.filteredEmpData = this.employeeData;
            });
        this.currentDate = new Date();
    }

    filterEmp() {
        this.filteredEmpData = this.empFilterPipe.transform(this.employeeData, this.searchBar.value);
    }
}
