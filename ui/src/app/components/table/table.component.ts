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
    columns = [{ name: 'Name', prop: 'knolderName', headerClass: 'my-custom-header', cellClass: 'my-custom-cell'},
        { name: 'Overall Score', prop: 'allTimeScore', headerClass: 'my-custom-header', cellClass: 'my-custom-cell'},
        { name: 'Overall Rank', prop: 'allTimeRank', headerClass: 'my-custom-header', cellClass: 'my-custom-cell'},
        { name: 'Monthly Score', prop: 'monthlyScore', headerClass: 'my-custom-header', cellClass: 'my-custom-cell'},
        { name: 'Monthly Rank', prop: 'monthlyScore', headerClass: 'my-custom-header', cellClass: 'my-custom-cell'},
        { name: '3 Month Streak', prop: 'quarterlyStreak', sortable: false, headerClass: 'my-custom-header', cellClass: 'my-custom-cell'}];

    constructor() {
    }

    ngOnInit() {
    }
}
