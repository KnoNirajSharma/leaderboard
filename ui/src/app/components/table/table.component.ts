import { Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { AuthorModel } from '../../models/author.model';
import { Router } from '@angular/router';
import { TableHeaderModel } from '../../models/tableHeader.model';
import {SortType} from '@swimlane/ngx-datatable';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
  encapsulation: ViewEncapsulation.None,
})

export class TableComponent implements OnInit {
    @Input() tableRows: AuthorModel[];
    @Input() tableHeading: TableHeaderModel[];
    @Input() employeeRows: AuthorModel[];
    @Output() sortCriteria = new EventEmitter();
    sorts = [{ dir: 'asc', prop: 'monthlyRank' }];
    classList = ['table-header-style', 'toSort'];
    tableHeaderStyle = this.classList.join(' ');
    multi = SortType.multi;

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

    onSort(event) {
      console.log(event);
      this.sortCriteria.emit(event);
    }
}
