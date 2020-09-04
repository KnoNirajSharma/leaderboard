import { Component, Input, OnInit } from '@angular/core';
import { TrendsModel } from '../../models/trends.model';
import { environment } from '../../../environments/environment';
import { NgxChartConfigService } from '../../services/ngxChartConfig.service';

@Component({
  selector: 'app-vertical-bar-graph',
  templateUrl: './vertical-bar-graph.component.html',
  styleUrls: ['./vertical-bar-graph.component.scss'],
})
export class VerticalBarGraphComponent implements OnInit {
    @Input() inputResult: TrendsModel[];
    // yAxisLabel = environment.ngxChartOptions.verticalBarChart.yAxisLabel;
    // barPadding = environment.ngxChartOptions.verticalBarChart.barPadding;
    colorScheme = environment.ngxChartOptions.chartColorScheme;
    yAxisLabel: string;
    barPadding: number;
    // colorScheme: { domain: string[]; };
    result: { name: string; series: { name: string; value: number; }[]; }[] = [];

    constructor(private chartConfigs: NgxChartConfigService) {
      this.yAxisLabel = this.chartConfigs.verticalBarChartYLabel;
      this.barPadding = this.chartConfigs.verticalBarChartPadding;
      this.colorScheme = this.chartConfigs.colorScheme;
    }
    ngOnInit() {
      // this.yAxisLabel = this.chartConfigs.verticalBarChartYLabel;
      // this.barPadding = this.chartConfigs.verticalBarChartPadding;
      // this.colorScheme = this.chartConfigs.chartColoScheme;
      this.inputResult.map(obj => this.result.push({ name: obj.month.substring(0, 3) + ',' + obj.year,
        series: [
          { name: 'Blogs', value: obj.blogScore },
          { name: 'Knolx', value: obj.knolxScore },
          { name: 'Webinar', value: obj.webinarScore },
          { name: 'TechHub Templates', value: obj.techHubScore },
          { name: 'OS Contribution', value: obj.osContributionScore },
          { name: 'Conference', value: obj.conferenceScore }
        ] }));
      this.result.reverse();
    }

}
