import {Component, Input} from '@angular/core';

import {NavBarItemModel} from '../../../models/header/nav-bar-item.model';
import {LoginService} from '../../../services/login/login.service';

@Component({
    selector: 'app-headers',
    templateUrl: './headers.component.html',
    styleUrls: ['./headers.component.scss'],
})
export class HeadersComponent {
    @Input() backBtn: boolean;
    @Input() backBtnLink: string;
    dropdownMenuVisibility = false;
    menuBoxVisibility = false;
    title = 'LEADERBOARD';
    formUrl = 'https://docs.google.com/forms/d/e/1FAIpQLSfjOGd2TI-zYb2b3_lpLnn-Kk_K57SAKQtjPsb7to9XzY6-tw/viewform';
    navItems: NavBarItemModel[] = [
        {title: 'Hall of Fame', link: '/hall-of-fame-page', imageUrl: './assets/icon/star.svg', isNavbarLevelItem: true},
        {title: 'Vision', link: '/about', imageUrl: './assets/icon/shuttle.svg', isNavbarLevelItem: false},
        {title: 'Report issue', link: '/report-issue', imageUrl: './assets/icon/help.svg', isNavbarLevelItem: false},
    ];
    mainPageLink = '/';
    reportIssuePageLink = '/report-issue';
    isAdmin: boolean = this.loginService.isAdmin();

    constructor(private loginService: LoginService) {
    }

    controlDropDownMenu(action) {
        switch (action) {
            case 'close':
                this.dropdownMenuVisibility = false;
                break;
            case 'toggle':
                this.dropdownMenuVisibility = !this.dropdownMenuVisibility;
                break;
        }
    }

    onLogout() {
        this.loginService.logout();
    }

    openForm() {
        window.open(this.formUrl, '_blank');
    }

    controlMenuBox(action) {
        switch (action) {
            case 'close':
                this.menuBoxVisibility = false;
                break;
            case 'toggle':
                this.menuBoxVisibility = !this.menuBoxVisibility;
                break;
        }
    }
}
