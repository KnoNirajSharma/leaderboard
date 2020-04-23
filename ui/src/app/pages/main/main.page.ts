import {Component, OnInit} from '@angular/core';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {AuthorModel} from '../../models/author.model';

@Component({
    selector: 'app-main',
    templateUrl: './main.page.html',
    styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
    employeeData: AuthorModel[];
    headerData: [
        { categoryName: 'Author Name' },
        { categoryName: 'Score' },
        { categoryName: 'Rank' }
    ];

    constructor(private employeeActivityService: EmployeeActivityService) {
    }

    ngOnInit() {
        this.employeeActivityService.getData().subscribe((data: AuthorModel[]) => {
            this.employeeData = data;
        });
    }

}
