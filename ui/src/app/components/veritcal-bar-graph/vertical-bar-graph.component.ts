import { Component, Input, OnInit } from '@angular/core';
import { TrendsModel } from '../../models/trends.model';
import { NgxChartConfigService } from '../../services/ngxChartConfig.service';

@Component({
  selector: 'app-vertical-bar-graph',
  templateUrl: './vertical-bar-graph.component.html',
  styleUrls: ['./vertical-bar-graph.component.scss'],
})
export class VerticalBarGraphComponent implements OnInit {
    @Input() trendsData: TrendsModel[];
    yAxisLabel: string;
    barPadding: number;
    colorScheme: { domain: string[]; };
    result: { name: string; series: { name: string; value: number; }[]; }[] = [];

    constructor(private chartConfigs: NgxChartConfigService) {
      this.yAxisLabel = this.chartConfigs.verticalBarChartYLabel;
      this.barPadding = this.chartConfigs.verticalBarChartPadding;
      this.colorScheme = this.chartConfigs.colorScheme;
    }
    ngOnInit() {
      this.trendsData.map(monthData => this.result.push({ name: monthData.month.substring(0, 3) + ',' + String(monthData.year),
        series: [
          { name: 'Blogs', value: monthData.blogScore },
          { name: 'Knolx', value: monthData.knolxScore },
          { name: 'Webinar', value: monthData.webinarScore },
          { name: 'TechHub Template', value: monthData.techHubScore },
          { name: 'OS Contribution', value: monthData.osContributionScore },
          { name: 'Conference', value: monthData.conferenceScore },
          { name: 'Book', value: monthData.bookScore },
          { name: 'Research Paper', value: monthData.researchPaperScore }
        ] }));
      this.result.reverse();
    }
}
