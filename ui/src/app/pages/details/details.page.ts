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
import * as moment from 'moment';

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
  currentDate = moment().toDate();
  dateFromRoute: moment.Moment;
  datePicker = new FormControl();
  datepickerConfig: Partial<BsDatepickerConfig> = new BsDatepickerConfig();
  pieChartData: ScoreBreakDownModel[] = [];
  trendsData: TrendsModel[];
  contributionsTypeColorList: string[];
  hallOfFameLeaders: HallOfFameModel[];
  medalTally: MedalTallyModel;
  knolderAchievements: LeaderModel[] = [];
  allTimeSelected = false;

  constructor(
    private route: ActivatedRoute,
    private employeeActivityService: EmployeeActivityService,
    private loadingControllerService: LoadingControllerService,
    private commonService: CommonService
  ) { }

  ngOnInit() {
    this.contributionsTypeColorList = this.commonService.colorScheme.domain;
    this.route.queryParams
      .subscribe((params: Params) => {
        this.knolderId = Number(params.id);
        this.yearFromRoute = Number(params.year);
        this.monthFromRoute = params.month.toLowerCase();
        this.loadingControllerService.present({
          message: 'Loading the score details...',
          translucent: 'false',
          spinner: 'bubbles'
        });
        this.calenderInitialisation();
        this.getTrendsData();
        this.getHallOfFameData();
        this.getAllTimeDetails();
      });
  }

  calenderInitialisation() {
    this.dateFromRoute = moment();
    this.dateFromRoute.month(this.monthFromRoute);
    this.dateFromRoute.set({ year: this.yearFromRoute });
    this.datePicker = new FormControl(this.dateFromRoute.toDate());
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
    const date = moment().set({ year: selectedDate.getFullYear(), month: selectedDate.getMonth() });
    this.getMonthlyDetails(date.format('MMMM').toLowerCase(), date.year());
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
    this.knolderAchievements = this.hallOfFameLeaders.map(monthsData => {
      return monthsData.leaders
        .map(leader => leader.knolderId === this.knolderId ? { ...leader, position: monthsData.leaders.indexOf(leader) } : leader)
        .filter(leader => leader.knolderId === this.knolderId);
    })
      .reduce((knolderAchievementList, el) => {
        return knolderAchievementList.concat(el);
      }, []);
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
}
