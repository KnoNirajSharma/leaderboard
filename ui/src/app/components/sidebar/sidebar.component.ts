import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
    sideBarData = [
        {title: 'Home', titleIcon: '../../../assets/icon/home-icon.png', link: './'},
    ];

    constructor() {
    }

    ngOnInit() {
    }
}
