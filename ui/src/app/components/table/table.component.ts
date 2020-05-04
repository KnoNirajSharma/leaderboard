import {Component, Input, OnInit} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {PageTitle} from '../../models/header.model';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss'],
})

export class TableComponent implements OnInit {
    @Input() pageTitle: PageTitle[];
    @Input() employee: AuthorModel[];
    @Input() titleKeys: string[];
    @Input() dataKeys: string[];

    constructor() {
    }

    ngOnInit() {
    }
}

