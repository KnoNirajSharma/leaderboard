import { Component, OnInit } from '@angular/core';

import { HallOfFameModel } from '../../models/hallOfFame.model';
import { CommonService } from '../../services/common.service';
import { EmployeeActivityService } from '../../services/employee-activity.service';

@Component({
  selector: 'app-hall-of-fame',
  templateUrl: './hall-of-fame.page.html',
  styleUrls: ['./hall-of-fame.page.scss'],
})
export class HallOfFamePage implements OnInit {
  mainPageLink = '/';
  hallOfFameLeaders: HallOfFameModel[];
  paginationLength: number;
  startIndexOfListForPage: number;
  lastIndexOfListForPage: number;
  numberOfItemsInPage: number;

  constructor(
    private service: EmployeeActivityService,
    private commonService: CommonService,
  ) {}

  ngOnInit() {
    this.numberOfItemsInPage = this.commonService.getNumberOfItemsInHallOfFame;
    this.service.getHallOfFameData()
      .subscribe((data: HallOfFameModel[]) => {
        this.hallOfFameLeaders = [...data];
        this.paginationLength = Math.ceil(this.hallOfFameLeaders.length / this.numberOfItemsInPage);
      });
    this.setListIndexForPage(0);
  }

  setListIndexForPage(pageIndex) {
    this.startIndexOfListForPage = pageIndex * this.numberOfItemsInPage;
    this.lastIndexOfListForPage = this.startIndexOfListForPage + this.numberOfItemsInPage;
  }
}
