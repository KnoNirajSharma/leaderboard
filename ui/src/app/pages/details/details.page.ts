import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { KnolderDetailsModel } from '../../models/knolder-details.model';
import { FormControl } from '@angular/forms';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
import { ScoreBreakDownModel } from '../../models/ScoreBreakDown.model';
import { LoadingControllerService } from '../../services/loading-controller.service ';
import { TrendsModel } from '../../models/trends.model';
import { CommonService } from '../../services/common.service';
import { HallOfFameModel } from '../../models/hallOfFame.model';
import { LeaderModel } from '../../models/leader.model';
import { MedalTallyModel } from '../../models/medalTally.model';

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
  monthFromRoute: string;
  yearFromRoute: number;
  currentDate: Date;
  dateFromRoute: Date;
  datePicker = new FormControl();
  datepickerConfig: Partial<BsDatepickerConfig> = new BsDatepickerConfig();
  pieChartData: ScoreBreakDownModel[] = [];
  trendsData: TrendsModel[];
  contributionsTypeColorList: string[];
  hallOfFameLeaders: HallOfFameModel[];
  medalTally: MedalTallyModel;
  knolderAchievements: LeaderModel[] = [];
  allTimeSelected = false;
  monthList: string[];

  constructor(
    private route: ActivatedRoute,
    private employeeActivityService: EmployeeActivityService,
    private loadingControllerService: LoadingControllerService,
    private commonService: CommonService
  ) { }

  ngOnInit() {
    this.contributionsTypeColorList = this.commonService.chartColorScheme.domain;
    this.route.queryParams
      .subscribe((params: Params) => {
        this.knolderId = Number(params.id);
        this.yearFromRoute = Number(params.year);
        this.monthFromRoute = params.month;
        this.setMonthList();
        this.calenderInitialisation();
      });
    this.loadingControllerService.present({
      message: 'Loading the score details...',
      translucent: 'false',
      spinner: 'bubbles'
    });
    this.getTrendsData();
    this.getHallOfFameData();
    this.getAllTimeDetails();
  }

  calenderInitialisation() {
    this.currentDate = new Date();
    this.dateFromRoute = new Date();
    this.dateFromRoute.setMonth(this.monthList.indexOf(this.monthFromRoute.toLowerCase()));
    this.dateFromRoute.setFullYear(this.yearFromRoute);
    this.datePicker = new FormControl(this.dateFromRoute);
    this.datepickerConfig = { containerClass: 'theme-dark-blue', dateInputFormat: 'MMM-YYYY', minMode: 'month' };
  }

  getMonthlyDetails(month: string, year: number) {
    this.employeeActivityService.getMonthlyDetails(this.knolderId, month, year)
      .subscribe((data: KnolderDetailsModel) => {
        this.knolderDetails = data;
      }, (error) => {
        console.log(error);
      });
  }

  getTrendsData() {
    this.employeeActivityService.getTrendsData(this.knolderId)
      .subscribe((data: TrendsModel[]) => {
        this.trendsData = data;
      }, (error) => {
        console.log(error);
      });
  }

  getAllTimeDetails() {
    this.employeeActivityService.getAllTimeDetails(this.knolderId)
      .subscribe((data: KnolderDetailsModel) => {
        this.allTimeDetails = data;
        this.pieChartData = this.allTimeDetails.scoreBreakDown;
        this.loadingControllerService.dismiss();
      }, () => {
        this.loadingControllerService.dismiss();
      });
  }

  onDateChange(selectedDate: Date) {
    this.allTimeSelected = false;
    this.getMonthlyDetails(this.commonService.getMonthName(selectedDate), selectedDate.getFullYear());
  }

  setAllTimeDetailsOnClick() {
    this.knolderDetails = { ...this.allTimeDetails };
    this.allTimeSelected = true;
  }

  getHallOfFameData() {
    this.employeeActivityService.getHallOfFameData()
      .subscribe((data: HallOfFameModel[]) => {
        this.hallOfFameLeaders = data;
        this.setKnolderAchievements();
        this.setMedalTally();
      }, (error) => {
        console.log(error);
      });
  }

  setKnolderAchievements() {
    this.hallOfFameLeaders
      .forEach(monthLeaders => {
        monthLeaders.leaders.forEach(leader => {
          leader.knolderId ===  this.knolderId
            ? this.knolderAchievements.push({ ...leader, position: monthLeaders.leaders.indexOf(leader) })
            : leader.position = -1;
        });
      });
  }

  setMedalTally() {
    this.medalTally = {
      gold: {
        count: this.knolderAchievements.filter(details => details.position === 0).length,
        imgUrl: './assets/icon/gold-medal.svg'
      },
      silver: {
        count: this.knolderAchievements.filter(details => details.position === 1 || details.position === 2).length,
        imgUrl: './assets/icon/silver-medal.svg'
      },
      bronze: {
        count: this.knolderAchievements.filter(details => details.position === 3 || details.position === 4).length,
        imgUrl: './assets/icon/bronze-medal.svg'
      }
    };
  }

  setMonthList() {
    this.monthList = this.commonService.monthList;
  }
}
