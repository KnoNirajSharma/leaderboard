import { Component, Input } from '@angular/core';
import { LeaderModel } from '../../models/leader.model';
import { BadgeDetailsTableDimensionModel } from '../../models/badgeDetailsTableDimension.model';

@Component({
  selector: 'app-badge-detail-table',
  templateUrl: './badge-detail-table.component.html',
  styleUrls: ['./badge-detail-table.component.scss'],
})
export class BadgeDetailTableComponent {
  @Input() knolderAchievements: LeaderModel[];
  @Input() tableDimensions: BadgeDetailsTableDimensionModel;
}
