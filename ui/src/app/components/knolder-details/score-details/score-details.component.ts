import {Component, Input} from '@angular/core';

import {KnolderDetailsModel} from '../../../models/knolder-details-page/knolder-details.model';

@Component({
    selector: 'app-score-details',
    templateUrl: './score-details.component.html',
    styleUrls: ['./score-details.component.scss'],
})
export class ScoreDetailsComponent {
    @Input() knolderDetails: KnolderDetailsModel;
    @Input() contributionsTypeColorList: string[];
}
