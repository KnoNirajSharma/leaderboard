import { Component, Input, OnInit } from '@angular/core';
import { ScoreBreakDownModel } from '../../models/ScoreBreakDown.model';
import { NgxChartConfigService } from '../../services/ngxChartConfig.service';
import { NgxPieChartResultModel } from '../../models/ngxPieChartResultModel';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss'],
})
export class PieChartComponent implements OnInit {
  @Input() inputResult: ScoreBreakDownModel[];

  colorScheme: { domain: string[]; };
  result: NgxPieChartResultModel[] = [];

  constructor(private chartConfigs: NgxChartConfigService) {
  }

  ngOnInit() {
    this.colorScheme = this.chartConfigs.colorScheme;
    this.inputResult.map(obj => this.result.push({ name: obj.contributionType, value: obj.contributionScore }));
  }
}
