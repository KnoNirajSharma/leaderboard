import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

import {AdminActionModel} from '../../../../models/admin-action.model';
import {DropdownMenuItemModel} from '../../../../models/dropdown-menu-item.model';
import {UserAccountDetailsModel} from '../../../../models/user-account-details.model';

@Component({
    selector: 'app-user-management-table-row',
    templateUrl: './user-management-table-row.component.html',
    styleUrls: ['./user-management-table-row.component.scss'],
})
export class UserManagementTableRowComponent implements OnInit {
    @Input() userDetails: UserAccountDetailsModel;
    @Output() actionForUserSelected = new EventEmitter<AdminActionModel>();
    actionsMenuList: DropdownMenuItemModel[];

    ngOnInit() {
        this.actionsMenuList = this.setActionMenuList();
    }

    setActionMenuList(): DropdownMenuItemModel[] {
        if (!this.userDetails.accountActive) {
            return this.actionsMenuList = [{value: 'Enable Account'}];
        } else if (this.userDetails.role === 'admin') {
            return this.actionsMenuList = [{value: 'Disable Account'}, {value: 'Remove Admin'}];
        } else {
            return this.actionsMenuList = [{value: 'Disable Account'}, {value: 'Make Admin'}];
        }
    }

    onActionSelect(event) {
        this.actionForUserSelected.emit({action: event, userAccountDetails: this.userDetails});
    }
}
