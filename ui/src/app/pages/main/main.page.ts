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

  sortTable(event) {
    if (event.newValue === 'asc') {
      this.filteredEmpData
        .sort((a, b) => (a[event.column.prop] === b[event.column.prop] ? a.allTimeScore < b.allTimeScore : a[event.column.prop] > b[event.column.prop]) ? 1 : -1);
    } else {
      this.filteredEmpData.sort((a, b) => a[event.column.prop] < b[event.column.prop] ? 1 : -1);
    }
  }
}
