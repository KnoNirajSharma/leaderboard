import {Component, OnInit} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {TableHeaderModel} from '../../models/tableHeader.model';
import {EmployeeActivityService} from '../../services/employee-activity.service';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss'],
})

export class TableComponent implements OnInit {
    tableRows: AuthorModel[];
    dataKeys: string[];
    tableHeaders: TableHeaderModel[] = [
        {title: 'Author Name'},
        {title: 'Score'},
        {title: 'Rank'}
    ];

    constructor(private service: EmployeeActivityService) {
    }

    ngOnInit() {
        this.service.getData()
            .subscribe((data: AuthorModel[]) => {
                this.tableRows = data;
                this.dataKeys = Object.keys(this.tableRows[0]);
            });
    }
}
