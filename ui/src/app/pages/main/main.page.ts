import {Component, OnInit} from '@angular/core';
import {CardDataModel} from '../../models/cardData.model';
import {AuthorModel} from '../../models/author.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {FormControl} from '@angular/forms';
import {EmployeeFilterPipe} from '../../pipe/employee-filter.pipe';

@Component({
    selector: 'app-main',
    templateUrl: './main.page.html',
    styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
    cardData: CardDataModel[];
    employeeData: AuthorModel[];
    pageTitle = 'LEADERBOARD';
    searchBar = new FormControl('');
    empFilterPipe = new EmployeeFilterPipe();
    filteredEmpData: AuthorModel[];

    constructor(private service: EmployeeActivityService) {
    }

    ngOnInit() {
        this.service.getData()
            .subscribe((data: AuthorModel[]) => {
                this.employeeData = data;
                this.prepareCardData();
                this.filteredEmpData = this.employeeData;
            });
    }

    prepareCardData() {
        this.cardData = [
            {
                cardTitle: this.getTotalCount('blogs'), subtitle: 'Blogs'
            }, {
                cardTitle: this.getTotalCount('knolx'), subtitle: 'Knolx'
            }, {
                cardTitle: this.getTotalCount('webinars'), subtitle: 'Webinars'
            }, {
                cardTitle: this.getTotalCount('techhubTemplates'), subtitle: 'TechHub Templates'
            },
        ];
    }

    getTotalCount(category: string): string {
        let count = '0';
        switch (category) {
            case 'blogs': {
                count = (this.employeeData.reduce((sum, current) => sum + current.allTimeScore / 5, 0)).toString();
                break;
            }
            case 'knolx': {
                break;
            }
            case 'webinars': {
                break;
            }
            case 'techhubTemplates': {
                break;
            }
        }
        return count;
    }

    filterEmp() {
        this.filteredEmpData = this.empFilterPipe.transform(this.employeeData, this.searchBar.value);
    }
}
