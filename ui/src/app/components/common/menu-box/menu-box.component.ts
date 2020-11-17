import { Component, Input } from '@angular/core';

import {NavBarItemModel} from '../../../models/nav-bar-item.model';

@Component({
  selector: 'app-menu-box',
  templateUrl: './menu-box.component.html',
  styleUrls: ['./menu-box.component.scss'],
})
export class MenuBoxComponent {
  @Input() menuItemList: NavBarItemModel[];
}
