import {Component, Input} from '@angular/core';

import {TribesSummeryModel} from '../../../models/tribe-main-page/tribes-summery.model';

@Component({
    selector: 'app-tribe-card',
    templateUrl: './tribe-card.component.html',
    styleUrls: ['./tribe-card.component.scss'],
})
export class TribeCardComponent {
    @Input() tribeSummeryDetails: TribesSummeryModel;
}
