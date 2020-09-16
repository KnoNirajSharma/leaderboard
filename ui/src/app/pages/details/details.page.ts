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
import { HallOfFameModel } from '../../models/hallOfFame.model';
import { LeaderModel } from '../../models/leader.model';

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
  contributionsTypeColorList: string[];
  hallOfFameLeaders: HallOfFameModel[];
  knolderAchievements: LeaderModel[] = [];
  medalTally: { first: number; sec: number; third: number; };

  constructor(
    private route: ActivatedRoute,
    private service: EmployeeActivityService,
    private loadingControllerService: LoadingControllerService,
    private ngxChartConfigs: NgxChartConfigService
  ) { }

  ngOnInit() {
    this.contributionsTypeColorList = this.ngxChartConfigs.colorScheme.domain;
    this.route.params
      .subscribe((params: Params) => {
        this.knolderId = Number(params.id);
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
    this.service.getHallOfFameData()
      .subscribe((data: HallOfFameModel[]) => {
        this.hallOfFameLeaders = data.reverse();
        // this.hallOfFameLeaders = this.hallOfFameLeaders.reverse();
        this.hallOfFameLeaders.map(monthLeaders => {
          monthLeaders.leaders.map(leader => {
            leader.knolderId ===  this.knolderId
              ? this.knolderAchievements.push({ ...leader, position: monthLeaders.leaders.indexOf(leader) })
              : leader;
          });
        });
        this.medalTally = {
          first: this.knolderAchievements.filter(details => details.position === 0).length,
          sec: this.knolderAchievements.filter(details => details.position === 1 || details.position === 2).length,
          third: this.knolderAchievements.filter(details => details.position === 3 || details.position === 4).length,
        };
      });
    console.log('leader', this.hallOfFameLeaders);
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
