import {Component, Input, OnInit} from '@angular/core';

import {UserDetailsModel} from '../../../models/user-details.model';

@Component({
    selector: 'app-user-management-table-row',
    templateUrl: './user-management-table-row.component.html',
    styleUrls: ['./user-management-table-row.component.scss'],
})
export class UserManagementTableRowComponent implements OnInit {
    @Input() userDetails: UserDetailsModel;

    ngOnInit() {
    }

}
