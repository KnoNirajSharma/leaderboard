import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {DropdownMenuItemModel} from '../../../models/dropdown-menu-item.model';
import {NavBarItemModel} from '../../../models/nav-bar-item.model';
import {LoginService} from '../../../services/login/login.service';

@Component({
    selector: 'app-headers',
    templateUrl: './headers.component.html',
    styleUrls: ['./headers.component.scss'],
})
export class HeadersComponent implements OnInit {
    @Input() backBtn: boolean;
    @Input() backBtnLink: string;
    dropdownMenuList: DropdownMenuItemModel[];
    title = 'LEADERBOARD';
    formUrl = 'https://docs.google.com/forms/d/e/1FAIpQLSfjOGd2TI-zYb2b3_lpLnn-Kk_K57SAKQtjPsb7to9XzY6-tw/viewform';
    navItems: NavBarItemModel[] = [
        {title: 'Hall of Fame', link: '/hall-of-fame', imageUrl: './assets/icon/menu-box-item-icons/star.svg', isNavbarLevelItem: true},
        {title: 'Vision', link: '/about', imageUrl: './assets/icon/menu-box-item-icons/shuttle.svg', isNavbarLevelItem: false},
        {title: 'Report issue', link: '/report-issue', imageUrl: './assets/icon/menu-box-item-icons/help.svg', isNavbarLevelItem: false},
    ];
    mainPageLink = '/';
    reportIssuePageLink = '/report-issue';

    constructor(private loginService: LoginService, private router: Router) {
    }

    ngOnInit() {
        if (this.loginService.isAdmin()) {
            this.dropdownMenuList = [
                { value: 'Add Contribution'},
                { value: 'Admin'},
                { value: 'Logout'},
            ];
        } else {
            this.dropdownMenuList = [
                { value: 'Add Contribution'},
                { value: 'Logout'},
            ];
        }
    }

    onDropdownClick(event) {
        console.log('enter');
        switch (event) {
            case 'Add Contribution':
                this.openForm();
                break;
            case 'Logout':
                this.onLogout();
                break;
            case 'Admin':
                console.log('admin');
                this.router.navigate(['/', 'admin']);
        }
    }

    onLogout() {
        this.loginService.logout();
    }

    openForm() {
        window.open(this.formUrl, '_blank');
    }
}
