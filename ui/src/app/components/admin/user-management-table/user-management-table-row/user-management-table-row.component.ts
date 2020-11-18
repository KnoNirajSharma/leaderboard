import {Component, Input, OnInit} from '@angular/core';

import {UserDetailsModel} from '../../../../models/user-details.model';

@Component({
    selector: 'app-user-management-table-row',
    templateUrl: './user-management-table-row.component.html',
    styleUrls: ['./user-management-table-row.component.scss'],
})
export class UserManagementTableRowComponent implements OnInit {
    @Input() userDetails: UserDetailsModel;
    actionsMenuList = [];
    dropdownVisibility = false;

    ngOnInit() {
        this.actionsMenuList = this.setActionMenuList();
    }

    setActionMenuList(): string[]  {
        if (!this.userDetails.accountActive) {
            return this.actionsMenuList = ['Enable Account'];
        } else if (this.userDetails.role === 'admin') {
            return this.actionsMenuList = ['Disable Account', 'Remove Admin'];
        } else {
            return this.actionsMenuList = ['Disable Account', 'Make Admin'];
        }
    }

    controlActionsDropdown(action) {
        switch (action) {
            case 'toggle':
                this.dropdownVisibility = ! this.dropdownVisibility;
                break;
            case 'close':
                this.dropdownVisibility = false;
                break;
        }
    }
}
