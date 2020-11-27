import {Component, EventEmitter, Input, Output} from '@angular/core';

import {AdminActionModel} from '../../../models/admin-action.model';
import {UserAccountDetailsModel} from '../../../models/user-account-details.model';

@Component({
  selector: 'app-user-management-table',
  templateUrl: './user-management-table.component.html',
  styleUrls: ['./user-management-table.component.scss'],
})
export class UserManagementTableComponent {
  @Input() userList: UserAccountDetailsModel[];
  @Input() searchTerm: string;
  @Output() userAction = new EventEmitter<AdminActionModel>();

  onUserAction(event: AdminActionModel) {
    this.userAction.emit(event);
  }
}
