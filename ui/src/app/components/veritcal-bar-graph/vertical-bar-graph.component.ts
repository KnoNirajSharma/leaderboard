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
    colorScheme = environment.ngxChartOptions.chartColorScheme;

    result: { name: string; series: { name: string; value: number; }[]; }[] = [];

    ngOnInit() {
      this.inputResult.map(obj => this.result.push({ name: obj.month.substring(0, 3) + ',' + obj.year,
        series: [
          { name: 'Blogs', value: obj.blogScore },
          { name: 'Knolx', value: obj.knolxScore },
          { name: 'Webinar', value: obj.webinarScore },
          { name: 'TechHub Template', value: obj.techHubScore },
          { name: 'OS Contribution', value: obj.osContributionScore },
          { name: 'Book', value: obj.bookScore },
          { name: 'Research Paper', value: obj.researchPaperScore },
        ] }));
      this.result.reverse();
    }

}
