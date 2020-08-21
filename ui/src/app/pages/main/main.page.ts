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
  pageTitle = 'Leaderboard';
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

  sortFn(x, y, property) {
    // if (x[property] < y[property]) {
    //   console.log('a < b');
    //   console.log(x[property], y[property]);
    //   console.log(x.name, y.name);
    //   return true;
    // } else {
    //   console.log('a > b');
    //   console.log(x[property], y[property]);
    //   console.log(x.name, y.name);
    //   return true;
    // }


    if (x[property] === y[property]) {
      console.log('equal');
      console.log(x.mathsMarks < y.mathsMarks);
      return x.mathsMarks > y.mathsMarks;
    } else {
      return x[property] < y[property];
    }


    // console.log(x[property], y[property]);
    // console.log(x.name, y.name);
    // console.log(x[property] > y[property]);
    // return x[property] > y[property];

    // return x.mathsMarks < y.mathsMarks;
  }

  sortTable(event) {
    // if (event.ascending === true) {
    //   console.log('in ascend');
    //   this.filteredEmpData = this.filteredEmpData.sort((a, b) => a[event.property] > b[event.property] ? 1 : -1);
    // } else {
    //   console.log('in descend');
    //   // this.data.sort((a, b) => (a.mathsMarks < b.mathsMarks) ? 1 : -1);
    //   // this.sortFn(a, b, event.property)
    //   this.filteredEmpData = this.filteredEmpData.sort((a, b) => this.sortFn(a, b, event.property) ? 1 : -1);
    // }
    console.log(event);
  //   if (event.newValue === 'asc') {
  //     console.log('in asc');
  //     this.filteredEmpData = this.filteredEmpData.sort((a, b) => a[event.property] > b[event.property] ? 1 : -1);
  //   } else {
  //     console.log('in desc');
  //   }
  }
}
