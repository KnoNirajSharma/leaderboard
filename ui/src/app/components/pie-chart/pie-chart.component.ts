import {Component, Input, OnInit} from '@angular/core';
import {ScoreBreakDownModel} from '../../models/ScoreBreakDown.model';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss'],
})
export class PieChartComponent implements OnInit {
  @Input() inputResult: ScoreBreakDownModel[];
  colorScheme = environment.ngxChartOptions.chartColorScheme;

  result: {name: string, value: number}[] = [];

  constructor() {
  }

  ngOnInit() {
    this.inputResult.map(obj => this.result.push({name: obj.contributionType, value: obj.contributionScore}));
  }
}
