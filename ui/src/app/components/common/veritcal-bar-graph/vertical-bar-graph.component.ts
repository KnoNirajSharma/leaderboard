import {Component, Input, OnChanges} from '@angular/core';

import {NgxStackVerticalBarGraphResultModel} from '../../../models/ngxStackVerticalBarGraphResultModel';
import {TrendsModel} from '../../../models/trends.model';
import {CommonService} from '../../../services/common/common.service';

@Component({
    selector: 'app-vertical-bar-graph',
    templateUrl: './vertical-bar-graph.component.html',
    styleUrls: ['./vertical-bar-graph.component.scss'],
})
export class VerticalBarGraphComponent implements OnChanges {
    @Input() trendsData: TrendsModel[];
    yAxisLabel: string;
    barPadding: number;
    colorScheme: { domain: string[]; };
    result: NgxStackVerticalBarGraphResultModel[];

    constructor(private commonService: CommonService) {
    }

    ngOnChanges() {
        this.setBarGraphConfigs();
        this.result = this.trendsData.map(monthData => ({
            name: monthData.month.substring(0, 3) + ',' + String(monthData.year),
            series: [
                {name: 'Blogs', value: monthData.blogScore},
                {name: 'Knolx', value: monthData.knolxScore},
                {name: 'Webinar', value: monthData.webinarScore},
                {name: 'TechHub Template', value: monthData.techHubScore},
                {name: 'OS Contribution', value: monthData.osContributionScore},
                {name: 'Conference', value: monthData.conferenceScore},
                {name: 'Book', value: monthData.bookScore},
                {name: 'Research Paper', value: monthData.researchPaperScore},
                {name: 'Meetup', value: monthData.meetUpScore},
            ]
        }));
        this.result.reverse();
    }

    setBarGraphConfigs() {
        this.yAxisLabel = this.commonService.verticalBarChartYLabel;
        this.barPadding = this.commonService.verticalBarChartPadding;
        this.colorScheme = this.commonService.colorScheme;
    }
}
