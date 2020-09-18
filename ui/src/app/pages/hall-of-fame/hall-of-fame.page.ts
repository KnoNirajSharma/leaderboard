import { Component, OnInit } from '@angular/core';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { HallOfFameModel } from '../../models/hallOfFame.model';
import { LoadingControllerService } from '../../services/loading-controller.service ';

@Component({
  selector: 'app-hall-of-fame',
  templateUrl: './hall-of-fame.page.html',
  styleUrls: ['./hall-of-fame.page.scss'],
})
export class HallOfFamePage implements OnInit {
  mainPageLink = '/';
  hallOfFameLeaders: HallOfFameModel[];
  paginationLength: number;
  startIndex: number;
  lastIndex: number;

  constructor(private service: EmployeeActivityService, private loadingControllerService: LoadingControllerService) {
  }

  ngOnInit() {
    this.loadingControllerService.present({
      message: 'Loading the Leaderboard...',
      translucent: 'false',
      spinner: 'bubbles'
    });
    this.service.getHallOfFameData()
      .subscribe((data: HallOfFameModel[]) => {
        this.hallOfFameLeaders = [...data];
        this.paginationLength = Math.ceil(this.hallOfFameLeaders.length / 10);
        this.loadingControllerService.dismiss();
      }, (error) => {
        this.loadingControllerService.dismiss();
        console.log(error);
      });
    this.generateSliceIndex(0);
  }

  generateSliceIndex(event) {
    this.startIndex = event * 10;
    this.lastIndex = this.startIndex + 10;
    console.log(event);
  }
}
