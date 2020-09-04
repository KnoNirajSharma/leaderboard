import { Component, Input, OnInit } from '@angular/core';
import { ScoreBreakDownModel } from '../../models/ScoreBreakDown.model';
import { NgxChartConfigService } from '../../services/ngxChartConfig.service';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss'],
})
export class PieChartComponent implements OnInit {
  @Input() inputResult: ScoreBreakDownModel[];

  colorScheme: { domain: string[]; };
  result: { name: string; value: number; }[] = [];

  constructor(private chartConfigs: NgxChartConfigService) {
    this.colorScheme = this.chartConfigs.colorScheme;
  }

  ngOnInit() {
    this.inputResult.map(obj => this.result.push({ name: obj.contributionType, value: obj.contributionScore }));
  }
}
