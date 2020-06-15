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
        this.service.getDetails(this.knolderId)
            .subscribe((data: KnolderDetailsModel) => {
                this.knolderDetails = data;
            });
        this.currentDate = new Date();
        this.maxDate = new Date();
        this.datePicker = new FormControl(this.currentDate);
        this.dpConfig.containerClass = 'theme-dark-blue';
        this.dpConfig.dateInputFormat = 'MMM-YYYY';
        this.dpConfig.minMode = 'month';
        this.maxDate.setMonth(this.maxDate.getMonth() + 1, 0);
        this.allTimeSelected = false;
    }

    onDateChange(selectedDate: Date) {
        this.allTimeSelected = false;
        this.getMonthlyDetails(this.monthList[selectedDate.getMonth()], selectedDate.getFullYear());
    }

    getMonthlyDetails(month: string, year: number) {
        /* this will call the service method with the selected month and year when date
        right now the api is not available
        once the api is set up i will remove the comments

        this.service.getMonthlyDetails(this.knolderId, month, year)
            .subscribe((data: KnolderDetailsModel) => {
                this.knolderDetails = data;
            });
         */
    }

    getAllTimeDetails() {
        this.allTimeSelected = true;
        /* this will call the service method with for all time details with id of a knolder
        right now the api is not available so i have commented this code
        once the api is set up i will remove the comments

        this.service.getAllTimeDetails(this.knolderId)
            .subscribe((data: KnolderDetailsModel) => {
                this.knolderDetails = data;
            });
         */
    }
}
