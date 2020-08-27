import { Component, EventEmitter, Input, Output, ViewEncapsulation } from '@angular/core';
import { AuthorModel } from '../../models/author.model';
import { Router } from '@angular/router';
import { TableHeaderModel } from '../../models/tableHeader.model';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
  encapsulation: ViewEncapsulation.None,
})

export class TableComponent {
    @Input() tableRows: AuthorModel[];
    @Input() tableHeading: TableHeaderModel[];
    @Output() sortCriteria = new EventEmitter();

    constructor(public router: Router) {
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
      this.sortCriteria.emit(event);
    }
}
