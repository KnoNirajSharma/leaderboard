import { Component, Input } from '@angular/core';

import {LeaderModel} from '../../../models/hall-of-fame-page/hall-of-fame.model';

@Component({
  selector: 'app-badge-detail-table',
  templateUrl: './badge-detail-table.component.html',
  styleUrls: ['./badge-detail-table.component.scss'],
})
export class BadgeDetailTableComponent {
  @Input() knolderAchievements: LeaderModel[];
}
