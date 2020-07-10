import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {Router} from '@angular/router';
import {TableHeaderModel} from '../../models/tableHeader.model';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss'],
    encapsulation: ViewEncapsulation.None,
})

export class TableComponent implements OnInit {
    @Input() tableRows: AuthorModel[];
    @Input() tableHeading: TableHeaderModel[];
    columns = [
        {
            name: 'Name',
            prop: 'knolderName',
            sortable: false,
            headerClass: 'text-body font-weight-bold h6',
            cellClass: 'text-body p py-2'
        },
        {
            name: 'Monthly Rank',
            prop: 'monthlyRank',
            headerClass: 'text-body font-weight-bold h6',
            cellClass: 'text-body p py-2'
        },
        {
            name: 'Monthly Score',
            prop: 'monthlyScore',
            headerClass: 'text-body font-weight-bold h6',
            cellClass: 'text-body p py-2'
        },
        {
            name: 'Overall Rank',
            prop: 'allTimeRank',
            headerClass: 'text-body font-weight-bold h6',
            cellClass: 'text-body p py-2'
        },
        {
            name: 'Overall Score',
            prop: 'allTimeScore',
            headerClass: 'text-body font-weight-bold h6',
            cellClass: 'text-body p py-2'
        },
        {
            name: '3 Month Streak',
            prop: 'quarterlyStreak',
            sortable: false,
            headerClass: 'text-body font-weight-bold h6',
            cellClass: 'text-body p py-2'
        }];

    constructor(public router: Router) {
    }

    ngOnInit() {
    }

    onActivate(event) {
        let id: number;
        if (event.type === 'click') {
            id = event.row.knolderId;
            this.router.navigate(['/details', id]);
        } else {
            this.router.navigate(['/']);
        }
    }
}
