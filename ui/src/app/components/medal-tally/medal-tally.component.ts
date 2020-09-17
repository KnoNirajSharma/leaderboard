import { Component, Input } from '@angular/core';
import { MedalTallyModel } from '../../models/medalTally.model';

@Component({
  selector: 'app-medal-tally',
  templateUrl: './medal-tally.component.html',
  styleUrls: ['./medal-tally.component.scss'],
})
export class MedalTallyComponent {
  @Input() medalTally: MedalTallyModel;
}
