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
    public rows = [];
    public currentPageLimit: number = 10;
    public currentVisible: number = 3;
    // public column = [{name:'Name'},{name:'Gender'},{name:'Company'}];
    public readonly pageLimitOptions = [
        {value: 5},
        {value: 10},
        {value: 25},
        {value: 50},
        {value: 100},
    ];
    public readonly visibleOptions = [
        {value: 1},
        {value: 3},
        {value: 5},
        {value: 10},
    ];
    columns = [{name: 'Name', prop: 'knolderName', headerClass: 'table-header-style', cellClass: 'table-cell-style'},
        {name: 'Overall Score', prop: 'allTimeScore', headerClass: 'table-header-style', cellClass: 'table-cell-style'},
        {name: 'Overall Rank', prop: 'allTimeRank', headerClass: 'table-header-style', cellClass: 'table-cell-style'},
        {name: 'Monthly Score', prop: 'monthlyScore', headerClass: 'table-header-style', cellClass: 'table-cell-style'},
        {name: 'Monthly Rank', prop: 'monthlyScore', headerClass: 'table-header-style', cellClass: 'table-cell-style'},
        {
            name: '3 Month Streak',
            prop: 'quarterlyStreak',
            sortable: false,
            headerClass: 'table-header-style',
            cellClass: 'table-cell-style'
        }];

    constructor() {
    }

    ngOnInit() {
    }
}
