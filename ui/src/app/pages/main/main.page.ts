import {Component, OnInit} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {FormControl} from '@angular/forms';
import {EmployeeFilterPipe} from '../../pipe/employee-filter.pipe';
import {TableHeaderModel} from '../../models/tableHeader.model';

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
    tableHeading: TableHeaderModel[];


    constructor(private service: EmployeeActivityService) {
    }

    ngOnInit() {
        this.service.getData()
            .subscribe((data: AuthorModel[]) => {
                this.employeeData = data;
                this.filteredEmpData = this.employeeData;
            });
        this.currentDate = new Date();
        this.tableHeading = [
            {title: 'Name'},
            {title: 'Monthly Rank'},
            {title: 'Monthly Score'},
            {title: 'Overall Rank'},
            {title: 'Overall Score'},
            {title: '3 Month Streak'}
            ];
    }

    filterEmp() {
        this.filteredEmpData = this.empFilterPipe.transform(this.employeeData, this.searchBar.value);
    }
}
