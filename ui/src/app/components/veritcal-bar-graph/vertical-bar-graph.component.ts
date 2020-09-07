import { Component, Input, OnInit } from '@angular/core';
import { TrendsModel } from '../../models/trends.model';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-vertical-bar-graph',
  templateUrl: './vertical-bar-graph.component.html',
  styleUrls: ['./vertical-bar-graph.component.scss'],
})
export class VerticalBarGraphComponent implements OnInit {
    @Input() trendsData: TrendsModel[];
    yAxisLabel = environment.ngxChartOptions.verticalBarChart.yAxisLabel;
    barPadding = environment.ngxChartOptions.verticalBarChart.barPadding;
    colorScheme = environment.ngxChartOptions.chartColorScheme;

    result: { name: string; series: { name: string; value: number; }[]; }[] = [];

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
