import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {ActivatedRoute, Params} from '@angular/router';
import * as moment from 'moment';
import {BsDatepickerConfig} from 'ngx-bootstrap/datepicker';

import {HallOfFameModel} from '../../../models/hallOfFame.model';
import {KnolderDetailsModel} from '../../../models/knolder-details.model';
import {LeaderModel} from '../../../models/leader.model';
import {MedalTallyModel} from '../../../models/medalTally.model';
import {ScoreBreakDownModel} from '../../../models/ScoreBreakDown.model';
import {TrendsModel} from '../../../models/trends.model';
import {CommonService} from '../../../services/common/common.service';
import {EmployeeActivityService} from '../../../services/employee-activity/employee-activity.service';


@Component({
    selector: 'app-details',
    templateUrl: './details.page.html',
    styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {
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
        private commonService: CommonService,
    ) {
    }

    ngOnInit() {
        this.contributionsTypeColorList = this.commonService.colorScheme.domain;
        this.route.queryParams
            .subscribe((params: Params) => {
                this.knolderId = Number(params.id);
                this.yearFromRoute = Number(params.year);
                this.monthFromRoute = params.month.toLowerCase();
                this.calenderInitialisation();
                this.getTrendsData();
                this.getHallOfFameData();
                this.getAllTimeDetails();
            });
    }

    calenderInitialisation() {
        this.dateFromRoute = moment();
        this.dateFromRoute.month(this.monthFromRoute);
        this.dateFromRoute.set({year: this.yearFromRoute});
        this.datePicker = new FormControl(this.dateFromRoute.toDate());
        this.datepickerConfig = {
            containerClass: 'theme-dark-blue',
            dateInputFormat: 'MMM-YYYY',
            minMode: 'month',
        };
    }

    getMonthlyDetails(month: string, year: number) {
        this.employeeActivityService.getMonthlyDetails(this.knolderId, month, year)
            .subscribe((data: KnolderDetailsModel) => {
                this.knolderDetails = data;
            });
    }

    getTrendsData() {
        this.employeeActivityService.getTrendsData(this.knolderId)
            .subscribe((data: TrendsModel[]) => {
                this.trendsData = data;
            });
    }

    getAllTimeDetails() {
        this.employeeActivityService.getAllTimeDetails(this.knolderId)
            .subscribe((data: KnolderDetailsModel) => {
                this.allTimeDetails = data;
                this.pieChartData = this.allTimeDetails.scoreBreakDown;
            });
    }

    onDateChange(selectedDate: Date) {
        this.allTimeSelected = false;
        const date = moment().set({year: selectedDate.getFullYear(), month: selectedDate.getMonth()});
        this.getMonthlyDetails(date.format('MMMM').toLowerCase(), date.year());
    }

    setAllTimeDetailsOnClick() {
        this.knolderDetails = {...this.allTimeDetails};
        this.allTimeSelected = true;
    }

    getHallOfFameData() {
        this.employeeActivityService.getHallOfFameData()
            .subscribe((data: HallOfFameModel[]) => {
                this.hallOfFameLeaders = data;
                this.setKnolderAchievements();
                this.setMedalTally();
            });
    }

    setKnolderAchievements() {
        this.knolderAchievements = this.hallOfFameLeaders.map((monthData) => {
            return monthData.leaders
                .map((leader, index) => {
                    return {
                        ...leader,
                        position: index,
                    };
                })
                .filter(leader => leader.knolderId === this.knolderId);
        })
            .reduce((knolderAchievementList, leaderDetails) => {
                return knolderAchievementList.concat(leaderDetails);
            }, []);
    }

    setMedalTally() {
        this.medalTally = {
            gold: {
                count: this.knolderAchievements.filter(details => details.position === 0).length,
                imgUrl: './assets/icon/gold-medal.svg',
            },
            silver: {
                count: this.knolderAchievements.filter(details => details.position === 1 || details.position === 2).length,
                imgUrl: './assets/icon/silver-medal.svg',
            },
            bronze: {
                count: this.knolderAchievements.filter(details => details.position === 3 || details.position === 4).length,
                imgUrl: './assets/icon/bronze-medal.svg',
            },
        };
    }
}
