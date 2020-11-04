import { Component, Input, OnChanges } from '@angular/core';
import { ScoreBreakDownModel } from '../../models/ScoreBreakDown.model';
import { CommonService } from '../../services/common.service';
import { NgxPieChartResultModel } from '../../models/ngxPieChartResultModel';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss'],
})
export class PieChartComponent implements OnChanges {
  @Input() inputResult: ScoreBreakDownModel[];
  colorScheme: { domain: string[]; };
  result: NgxPieChartResultModel[];

  constructor(private commonService: CommonService) {
  }

  ngOnChanges() {
    this.colorScheme = this.commonService.colorScheme;
    this.colorScheme = this.commonService.colorScheme;
    this.result = this.inputResult.map(obj => {
      return { name: obj.contributionType, value: obj.contributionScore };
    });
  }
}
