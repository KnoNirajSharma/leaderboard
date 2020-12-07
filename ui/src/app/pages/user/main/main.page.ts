import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';

import {AuthorModel} from '../../../models/author.model';
import {ReputationModel} from '../../../models/reputation.model';
import {ScoringTableModel} from '../../../models/scoring-table.model';
import {TableHeaderModel} from '../../../models/tableHeader.model';
import {EmployeeActivityService} from '../../../services/employee-activity/employee-activity.service';


@Component({
  selector: 'app-main',
  templateUrl: './main.page.html',
  styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
  @ViewChild('knoldusStats', { static: false }) knoldusStatsRef: ElementRef;
  knoldersReputationList: AuthorModel[];
  searchBar = new FormControl('');
  reputation: ReputationModel;
  scoringInfoData: ScoringTableModel;
  scoringInfoKeys: string[];
  knoldusStatsReputationKeys: string[];
  currentDate: Date = new Date();
  boostedScoreCount: any;
  tableHeading: TableHeaderModel[] = [
    { title: 'MONTHLY RANK' },
    { title: 'MONTHLY SCORE' },
    { title: 'OVERALL RANK' },
    { title: 'OVERALL SCORE' },
    { title: '3-MONTH-STREAK' },
  ];
  knoldusStatsLegend = {
    currentMonth: '#0F7291',
    allTime: '#602CA5',
  };
  knoldusStatsLegendPosX = 0;
  knoldusStatsLegendPosY = 0;

  constructor(private employeeActivityService: EmployeeActivityService) { }

  ngOnInit() {
    this.getScoringInfoData();
    this.getReputationData();
  }

  getReputationData() {
    this.employeeActivityService.getData()
      .subscribe((data: ReputationModel) => {
        this.reputation = { ...data };
        this.setAllKnolderData();
      });
  }

  getScoringInfoData() {
    this.employeeActivityService.getScoringInfoData()
      .subscribe((scoringInfoData: ScoringTableModel) => {
        this.scoringInfoData = { ...scoringInfoData };
        this.scoringInfoKeys = this.getScoringInfoKeys();
        this.boostedScoreCount = this.getNumberOfScoresBoosted();
      });
  }

  setAllKnolderData() {
    this.setKnoldusStatsReputationKeys();
    this.setKnoldersList();
  }

  setKnoldusStatsReputationKeys() {
    this.knoldusStatsReputationKeys = Object.keys(this.reputation).filter(x => x !== 'reputation');
  }

  getScoringInfoKeys(): string[] {
    return Object.keys(this.scoringInfoData);
  }

  setKnoldersList() {
    this.knoldersReputationList = [
      ...this.reputation.reputation
        .map(knolder => this.reputation.reputation.indexOf(knolder) < 5 ? { ...knolder, topRanker: true } : knolder),
    ];
  }

  getNumberOfScoresBoosted(): number {
    return this.scoringInfoKeys.map(key => this.scoringInfoData[key])
      .filter(scoreInfo => scoreInfo.pointsMultiplier > 1).length;
  }

  mouseEnterOnKnoldusStatsHandler(event) {
    this.knoldusStatsLegendPosX = event.offsetX;
    this.knoldusStatsLegendPosY = this.knoldusStatsRef.nativeElement.offsetHeight;
  }
}
