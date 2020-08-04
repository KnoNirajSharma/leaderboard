import { Component, Input, OnInit } from '@angular/core';
import { TrendsModel } from '../../models/trends.model';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-vertical-bar-graph',
  templateUrl: './vertical-bar-graph.component.html',
  styleUrls: ['./vertical-bar-graph.component.scss'],
})
export class VerticalBarGraphComponent implements OnInit {
    @Input() inputResult: TrendsModel[];
    yAxisLabel = environment.ngxChartOptions.verticalBarChart.yAxisLabel;
    barPadding = environment.ngxChartOptions.verticalBarChart.barPadding;
    colorScheme = {
      domain: [environment.ngxChartOptions.chartColorScheme.domain[1]]
    };

    result: { name: string; value: number; }[] = [];

    constructor() {
    }

    ngOnInit() {
      this.inputResult.map(obj => this.result.push({ name: obj.month.substring(0, 3) + ',' + obj.year, value: obj.score }));
      this.result.reverse();
    }

}
