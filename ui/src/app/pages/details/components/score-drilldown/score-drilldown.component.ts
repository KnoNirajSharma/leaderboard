import { Component, Input } from '@angular/core';

import { KnolderDetailsModel } from '../../../../models/knolder-details.model';

@Component({
  selector: 'app-score-drilldown',
  templateUrl: './score-drilldown.component.html',
  styleUrls: ['./score-drilldown.component.scss'],
})
export class ScoreDrilldownComponent {
  @Input() knolderDetails: KnolderDetailsModel;
}
