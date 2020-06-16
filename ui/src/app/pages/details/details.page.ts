import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {KnolderDetailsModel} from '../../models/knolder-details.model';
import {FormControl} from '@angular/forms';
import {BsDatepickerConfig} from 'ngx-bootstrap/datepicker';

@Component({
  selector: 'app-details',
  templateUrl: './details.page.html',
  styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {
  mainPageLink = '/';
    knolderDetails: KnolderDetailsModel;
    knolderId: number;
    currentDate: Date;
    maxDate: Date;
    datePicker = new FormControl();
    dpConfig: Partial<BsDatepickerConfig> = new BsDatepickerConfig();
    monthList = [ 'January', 'February', 'March', 'April', 'May', 'June', 'July',
        'August', 'September', 'October', 'November', 'December' ];
    allTimeSelected: boolean;
    constructor(private route: ActivatedRoute,
                private service: EmployeeActivityService) { }

    ngOnInit() {
        this.route.params
            .subscribe(
                (params: Params) => {
                    this.knolderId = params.id;
                }
            );
        this.currentDate = new Date();
        this.maxDate = new Date();
        this.datePicker = new FormControl(this.currentDate);
        this.dpConfig.containerClass = 'theme-dark-blue';
        this.dpConfig.dateInputFormat = 'MMM-YYYY';
        this.dpConfig.minMode = 'month';
        this.maxDate.setMonth(this.maxDate.getMonth() + 1, 0);
        this.allTimeSelected = false;
        this.service.getMonthlyDetails(this.knolderId, this.monthList[this.currentDate.getMonth()], this.currentDate.getFullYear())
            .subscribe((data: KnolderDetailsModel) => {
                this.knolderDetails = data;
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
        this.allTimeSelected = true;
        this.service.getAllTimeDetails(this.knolderId)
            .subscribe((data: KnolderDetailsModel) => {
                this.knolderDetails = data;
            });
    }
}
