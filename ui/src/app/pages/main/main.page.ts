import { Component, OnInit } from '@angular/core';
import { AuthorModel } from '../../models/author.model';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { FormControl } from '@angular/forms';
import { EmployeeFilterPipe } from '../../pipe/employee-filter.pipe';
import { TableHeaderModel } from '../../models/tableHeader.model';
import { ReputationModel } from '../../models/reputation.model';
import { LoadingControllerService } from '../../services/loading-controller.service ';

@Component({
  selector: 'app-main',
  templateUrl: './main.page.html',
  styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
  employeeData: AuthorModel[];
  searchBar = new FormControl('');
  empFilterPipe = new EmployeeFilterPipe();
  filteredEmpData: AuthorModel[];
  today: Date = new Date();
  currentDate: Date;
  tableHeading: TableHeaderModel[];
  reputation: ReputationModel;
  scoringInfoData = [
    { type: 'Blog', weight: '5', integrated: '' },
    { type: 'Knolx', weight: '20', integrated: '' },
    { type: 'Webinar', weight: '15', integrated: '' },
    { type: 'TechHub Templates', weight: '30', integrated: '' },
    { type: 'OS Contribution', weight: '', integrated: '' },
    { type: 'Conferences', weight: '', integrated: '' },
    { type: 'Books', weight: '', integrated: '' },
    { type: 'Research Paper', weight: '', integrated: '' },
  ];

  constructor(private service: EmployeeActivityService, private loadingControllerService: LoadingControllerService) {
  }

  ngOnInit() {
    this.loadingControllerService.present({
      message: 'Loading the Leaderboard...',
      translucent: 'false',
      spinner: 'bubbles'
    });
    this.service.getData()
      .subscribe((data: ReputationModel) => {
        this.reputation = data;
        this.employeeData = this.reputation.reputation;
        this.filteredEmpData = [...this.employeeData];
        this.loadingControllerService.dismiss();
      });
    this.currentDate = new Date();
    this.tableHeading = [
      { title: 'MONTHLY RANK' },
      { title: 'MONTHLY SCORE' },
      { title: 'OVERALL RANK' },
      { title: 'OVERALL SCORE' },
      { title: '3-MONTH-STREAK' }
    ];
  }

  filterEmp() {
    this.filteredEmpData = this.empFilterPipe.transform(this.employeeData, this.searchBar.value);
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

  sortTable(event) {
    if (event.column.prop === 'quarterlyStreak') {
      this.filteredEmpData
        .sort((secEmp, firstEmp) => this.compareQuarterlyScore(firstEmp.quarterlyStreak, secEmp.quarterlyStreak,  event.newValue) ? 1 : -1);
    } else if (event.newValue === 'asc') {
      this.filteredEmpData
        .sort((secEmp, firstEmp) => this.comparisonBasedOnAllTimeScore(secEmp, firstEmp, event.column.prop) ? 1 : -1);
    } else {
      this.filteredEmpData
        .sort((secEmp, firstEmp) => secEmp[event.column.prop] < firstEmp[event.column.prop] ? 1 : -1);
    }
  }
}
