import {Component, Input, OnInit} from '@angular/core';
import {NavBarItemModel} from '../../models/nav-bar-item.model';

@Component({
  selector: 'app-menu-box',
  templateUrl: './menu-box.component.html',
  styleUrls: ['./menu-box.component.scss'],
})
export class MenuBoxComponent implements OnInit {
  @Input() menuItemList: NavBarItemModel[];
  constructor() { }

  ngOnInit() {}

}
