import {Component, Input, OnInit} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {TableHeaderModel} from '../../models/tableHeader.model';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss'],
})

export class TableComponent implements OnInit {
    @Input() tableHeaders: TableHeaderModel[];
    @Input() tableRows: AuthorModel[];
    @Input() dataKeys: string[];

    constructor() {
    }

    ngOnInit() {
    }
}
