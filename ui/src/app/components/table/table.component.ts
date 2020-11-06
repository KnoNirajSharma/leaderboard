import { Component, Input, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import * as moment from 'moment';

import { AuthorModel } from '../../models/author.model';
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
    currentDate = moment();

    constructor(public router: Router) { }

    onActivate(event) {
      let id: number;
      if (event.type === 'click') {
        id = event.row.knolderId;
        this.router.navigate(
          ['/details'],
          {
            queryParams: {
              id,
              year: this.currentDate.year(),
              month: this.currentDate.format('MMMM'),
            },
          },
        );
      }
    }

    comparisonBasedOnAllTimeScore(firstEmp: AuthorModel, secEmp: AuthorModel, propertyName: string) {
      return firstEmp[propertyName] === secEmp[propertyName]
        ? firstEmp.allTimeScore < secEmp.allTimeScore
        : firstEmp[propertyName] > secEmp[propertyName];
    }

    totalOfQuarterlyScore(quarterlyScore: string) {
      return quarterlyScore.split('-')
        .map(Number)
        .reduce((firstMonth, secondMonth) => firstMonth + secondMonth);
    }

    compareQuarterlyScore(firstEmpScoreStreak, secEmpScoreStreak, sortType) {
      return sortType === 'asc'
        ? this.totalOfQuarterlyScore(firstEmpScoreStreak) < this.totalOfQuarterlyScore(secEmpScoreStreak)
        : this.totalOfQuarterlyScore(firstEmpScoreStreak) > this.totalOfQuarterlyScore(secEmpScoreStreak);
    }

    onSort(event) {
      if (event.column.prop === 'quarterlyStreak') {
        this.tableRows.sort((secEmp, firstEmp) => {
          return this.compareQuarterlyScore(firstEmp.quarterlyStreak, secEmp.quarterlyStreak,  event.newValue) ? 1 : -1;
        });
      } else if (event.newValue === 'asc') {
        this.tableRows
          .sort((secEmp, firstEmp) => this.comparisonBasedOnAllTimeScore(secEmp, firstEmp, event.column.prop) ? 1 : -1);
      } else {
        this.tableRows
          .sort((secEmp, firstEmp) => secEmp[event.column.prop] < firstEmp[event.column.prop] ? 1 : -1);
      }
    }

}
