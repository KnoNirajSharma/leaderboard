import { Component, OnInit } from '@angular/core';
import { AuthorModel } from '../../models/author.model';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { FormControl } from '@angular/forms';
import { EmployeeFilterPipe } from '../../pipe/employee-filter.pipe';
import { TableHeaderModel } from '../../models/tableHeader.model';
import { ReputationModel } from '../../models/reputation.model';
import { LoadingControllerService } from '../../services/loading-controller.service ';
import { ScoringInfoModel } from '../../models/scoringInfo.model';

@Component({
  selector: 'app-main',
  templateUrl: './main.page.html',
  styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
  knoldersReputationList: AuthorModel[];
  searchBar = new FormControl('');
  empFilterPipe = new EmployeeFilterPipe();
  filteredKnolderList: AuthorModel[];
  reputation: ReputationModel;
  scoringInfoData: ScoringInfoModel[] = [
    { type: 'Blog', weight: '5', integrated: true, symbol: '&#10004;' },
    { type: 'Knolx', weight: '20', integrated: true, symbol: '&#10004;' },
    { type: 'Webinar', weight: '15', integrated: true, symbol: '&#10004;' },
    { type: 'TechHub Templates', weight: '15', integrated: true, symbol: '&#10004;' },
    { type: 'OS Contribution', weight: '30', integrated: true, symbol: '&#10004;' },
    { type: 'Research Paper', weight: '50', integrated: true, symbol: '&#10004;' },
    { type: 'Conference', weight: '100', integrated: true, symbol: '&#10004;' },
    { type: 'Book', weight: '100', integrated: true, symbol: '&#10004;' },
  ];
  knoldusStatsReputationKeys: string[];
  currentDate: Date = new Date();
  tableHeading: TableHeaderModel[] = [
    { title: 'MONTHLY RANK' },
    { title: 'MONTHLY SCORE' },
    { title: 'OVERALL RANK' },
    { title: 'OVERALL SCORE' },
    { title: '3-MONTH-STREAK' }
  ];

  constructor(
    private employeeActivityService: EmployeeActivityService,
    private loadingControllerService: LoadingControllerService
  ) { }

  ngOnInit() {
    this.loadingControllerService.present({
      message: 'Loading the Leaderboard...',
      translucent: 'false',
      spinner: 'bubbles'
    });
    this.getReputationData();
  }

  getReputationData() {
    this.employeeActivityService.getData()
      .subscribe((data: ReputationModel) => {
        this.reputation = { ...data };
        this.setKnoldusStatsReputationKeys();
        this.setKnoldersList();
        this.setInitialFilteredKnolderList();
        this.loadingControllerService.dismiss();
      }, error => {
        this.loadingControllerService.dismiss();
      });
  }

  setKnoldusStatsReputationKeys() {
    this.knoldusStatsReputationKeys = Object.keys(this.reputation).filter(x => x !== 'reputation');
  }

  setKnoldersList() {
    this.knoldersReputationList = [
      ...this.reputation.reputation
        .map(knolder => this.reputation.reputation.indexOf(knolder) < 5 ? { ...knolder, topRanker: true } : knolder)
    ];
  }

  setInitialFilteredKnolderList() {
    this.filteredKnolderList = [...this.knoldersReputationList];
  }

  filterKnolderList() {
    this.filteredKnolderList = this.empFilterPipe.transform(this.knoldersReputationList, this.searchBar.value);
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
      this.filteredKnolderList
        .sort((secEmp, firstEmp) => this.compareQuarterlyScore(firstEmp.quarterlyStreak, secEmp.quarterlyStreak,  event.newValue) ? 1 : -1);
    } else if (event.newValue === 'asc') {
      this.filteredKnolderList
        .sort((secEmp, firstEmp) => this.comparisonBasedOnAllTimeScore(secEmp, firstEmp, event.column.prop) ? 1 : -1);
    } else {
      this.filteredKnolderList
        .sort((secEmp, firstEmp) => secEmp[event.column.prop] < firstEmp[event.column.prop] ? 1 : -1);
    }
  }
}
