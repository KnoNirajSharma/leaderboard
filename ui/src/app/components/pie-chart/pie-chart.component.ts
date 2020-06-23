import {Component, Input, OnInit} from '@angular/core';
import {ScoreBreakDownModel} from '../../models/ScoreBreakDown.model';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss'],
})
export class PieChartComponent implements OnInit {
  @Input() inputResult: ScoreBreakDownModel[];
  @Input() colorScheme: {domain: string[]};

  result: {name: string, value: number}[] = [];

  constructor() {
  }

  ngOnInit() {
    this.inputResult.map(obj => this.result.push({name: obj.contributionType, value: obj.contributionScore}));
  }
}
