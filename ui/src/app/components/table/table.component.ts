import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthorModel} from '../../models/author.model';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss'],
    encapsulation: ViewEncapsulation.None,
})

export class TableComponent implements OnInit {
    @Input() tableRows: AuthorModel[];
    @Input() dataKeys: string[];
    columns = [{name: 'Name', prop: 'knolderName', headerClass: 'tableHeaderStyle', cellClass: 'tableCellStyle'},
        {name: 'Overall Score', prop: 'allTimeScore', headerClass: 'tableHeaderStyle', cellClass: 'tableCellStyle'},
        {name: 'Overall Rank', prop: 'allTimeRank', headerClass: 'tableHeaderStyle', cellClass: 'tableCellStyle'},
        {name: 'Monthly Score', prop: 'monthlyScore', headerClass: 'tableHeaderStyle', cellClass: 'tableCellStyle'},
        {name: 'Monthly Rank', prop: 'monthlyScore', headerClass: 'tableHeaderStyle', cellClass: 'tableCellStyle'},
        {
            name: '3 Month Streak',
            prop: 'quarterlyStreak',
            sortable: false,
            headerClass: 'tableHeaderStyle',
            cellClass: 'tableCellStyle'
        }];

    constructor() {
    }

    ngOnInit() {
    }
}
