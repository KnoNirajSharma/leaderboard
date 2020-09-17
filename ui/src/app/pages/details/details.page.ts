import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { KnolderDetailsModel } from '../../models/knolder-details.model';
import { FormControl } from '@angular/forms';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
import { ScoreBreakDownModel } from '../../models/ScoreBreakDown.model';
import { LoadingControllerService } from '../../services/loading-controller.service ';
import { TrendsModel } from '../../models/trends.model';
import { NgxChartConfigService } from '../../services/ngxChartConfig.service';
import {BsDatepickerViewMode} from 'ngx-bootstrap/datepicker/models';

@Component({
  selector: 'app-details',
  templateUrl: './details.page.html',
  styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {
  mainPageLink = '/';
  knolderDetails: KnolderDetailsModel;
  allTimeDetails: KnolderDetailsModel;
  knolderId: number;
  currentDate: Date;
  datePicker = new FormControl();
  datepickerConfig: Partial<BsDatepickerConfig> = new BsDatepickerConfig();
  pieChartData: ScoreBreakDownModel[] = [];
  trendsData: TrendsModel[];
  contributionsTypeColorList: string[];
  allTimeSelected = false;
  monthList = [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
    'July',
    'August',
    'September',
    'October',
    'November',
    'December'
  ];

  constructor(
    private route: ActivatedRoute,
    private service: EmployeeActivityService,
    private loadingControllerService: LoadingControllerService,
    public ngxChartConfigs: NgxChartConfigService
  ) { }

  ngOnInit() {
    this.route.params
      .subscribe((params: Params) => {
        this.knolderId = Number(params.id);
      });
    this.loadingControllerService.present({
      message: 'Loading the score details...',
      translucent: 'false',
      spinner: 'bubbles'
    });
    this.calenderInitialisation();
    this.getMonthlyDetails(this.monthList[this.currentDate.getMonth()], this.currentDate.getFullYear());
    this.getTrendsData();
    this.getAllTimeDetails();
  }

  calenderInitialisation() {
    this.currentDate = new Date();
    this.datePicker = new FormControl(this.currentDate);
    this.datepickerConfig = { containerClass: 'theme-dark-blue', dateInputFormat: 'MMM-YYYY', minMode: 'month' };
  }

  getMonthlyDetails(month: string, year: number) {
    this.service.getMonthlyDetails(this.knolderId, month, year)
      .subscribe((data: KnolderDetailsModel) => {
        this.knolderDetails = data;
      }, (error) => {
        console.log(error);
      });
  }

  getTrendsData() {
    this.service.getTrendsData(this.knolderId)
      .subscribe((data: TrendsModel[]) => {
        this.trendsData = data;
      }, (error) => {
        console.log(error);
      });
  }

  getAllTimeDetails() {
    this.service.getAllTimeDetails(this.knolderId)
      .subscribe((data: KnolderDetailsModel) => {
        this.allTimeDetails = data;
        this.pieChartData = this.allTimeDetails.scoreBreakDown;
        this.loadingControllerService.dismiss();
      }, (error) => {
        console.log(error);
        this.loadingControllerService.dismiss();
      });
  }

  onDateChange(selectedDate: Date) {
    this.allTimeSelected = false;
    this.getMonthlyDetails(this.monthList[selectedDate.getMonth()], selectedDate.getFullYear());
  }

  viewAllTimeDetails() {
    this.knolderDetails = { ...this.allTimeDetails };
    this.allTimeSelected = true;
  }
}
