import {Component, Input, OnInit} from '@angular/core';

import {UserDetailsModel} from '../../../models/user-details.model';

@Component({
  selector: 'app-user-management-table',
  templateUrl: './user-management-table.component.html',
  styleUrls: ['./user-management-table.component.scss'],
})
export class UserManagementTableComponent implements OnInit {
  @Input() userList: UserDetailsModel[];
  @Input() searchTerm: string;
  constructor() { }

  ngOnInit() {}

}
