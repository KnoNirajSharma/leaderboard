import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

import {DropdownMenuItemModel} from '../../../models/dropdown-menu-item.model';

@Component({
  selector: 'app-dropdown-menu-list',
  templateUrl: './dropdown-menu-list.component.html',
  styleUrls: ['./dropdown-menu-list.component.scss'],
})
export class DropdownMenuListComponent {
  @Input() menuList: DropdownMenuItemModel[];
  @Output() menuItemClicked = new EventEmitter();

  onItemClick(itemName: string) {
    this.menuItemClicked.emit(itemName);
  }
}
