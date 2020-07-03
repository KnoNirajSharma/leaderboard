import {Component, Input, OnInit} from '@angular/core';
import {TrendsModel} from '../../models/trends.model';
import {environment} from '../../../environments/environment';

@Component({
    selector: 'app-veritcal-bar-graph',
    templateUrl: './veritcal-bar-graph.component.html',
    styleUrls: ['./veritcal-bar-graph.component.scss'],
})
export class VeritcalBarGraphComponent implements OnInit {
    @Input() inputResult: TrendsModel[];
    yAxisLabel = 'score';
    barPAdding = 16;
    colorScheme = {
        domain: [environment.chartColorScheme.domain[1]]
    };

    result: { name: string, value: number }[] = [];

    constructor() {
    }

    ngOnInit() {
        this.inputResult.map(obj => this.result.push({name: obj.month.substring(0, 3) + ',' + obj.year, value: obj.score}));
        this.result.reverse();
    }

}
