import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { KnolderDetailsModel } from '../../models/knolder-details.model';
import { FormControl } from '@angular/forms';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
import { ScoreBreakDownModel } from '../../models/ScoreBreakDown.model';
import { LoadingControllerService } from '../../services/loading-controller.service ';
import { TrendsModel } from '../../models/trends.model';
import { environment } from '../../../environments/environment';

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
  dpConfig: Partial<BsDatepickerConfig> = new BsDatepickerConfig();
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
  pieChartData: ScoreBreakDownModel[] = [];
  allTimeSelected: boolean;
  trendsData: TrendsModel[];
  contributionsTypeColorList = environment.ngxChartOptions.chartColorScheme.domain;

  constructor(
    private route: ActivatedRoute,
    private service: EmployeeActivityService,
    private loadingControllerService: LoadingControllerService
  ) {
  }

  ngOnInit() {
    this.route.params
      .subscribe((params: Params) => {
        this.knolderId = params.id;
      });
    this.loadingControllerService.present({
      message: 'Loading the score details...',
      translucent: 'false',
      spinner: 'bubbles'
    });
    this.currentDate = new Date();
    this.datePicker = new FormControl(this.currentDate);
    this.dpConfig.containerClass = 'theme-dark-blue';
    this.dpConfig.dateInputFormat = 'MMM-YYYY';
    this.dpConfig.minMode = 'month';
    this.allTimeSelected = false;
    this.getMonthlyDetails(this.monthList[this.currentDate.getMonth()], this.currentDate.getFullYear());
    this.service.getTrendsData(this.knolderId)
      .subscribe((data: TrendsModel[]) => {
        this.trendsData = data;
      });
    this.service.getAllTimeDetails(this.knolderId)
      .subscribe((data: KnolderDetailsModel) => {
        this.allTimeDetails = data;
        this.pieChartData = this.allTimeDetails.scoreBreakDown;
        this.loadingControllerService.dismiss();
      });
  }

  onDateChange(selectedDate: Date) {
    this.allTimeSelected = false;
    this.getMonthlyDetails(this.monthList[selectedDate.getMonth()], selectedDate.getFullYear());
  }

  getMonthlyDetails(month: string, year: number) {
    this.service.getMonthlyDetails(this.knolderId, month, year)
      .subscribe((data: KnolderDetailsModel) => {
        this.knolderDetails = data;
      });
  }

  getAllTimeDetails() {
    this.knolderDetails = this.allTimeDetails;
    this.allTimeSelected = true;
  }
}
