import { Component, Input, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { NavBarItemModel } from '../../models/nav-bar-item.model';

@Component({
  selector: 'app-headers',
  templateUrl: './headers.component.html',
  styleUrls: ['./headers.component.scss'],
})
export class HeadersComponent implements OnInit {
    @Input() backBtn: boolean;
    @Input() backBtnLink: string;
    dropdownMenuVisibility: boolean;
    menuBoxVisibility: boolean;
    title = 'LEADERBOARD';
    formUrl = 'https://docs.google.com/forms/d/e/1FAIpQLSfjOGd2TI-zYb2b3_lpLnn-Kk_K57SAKQtjPsb7to9XzY6-tw/viewform';
    navItems: NavBarItemModel[] = [
      { title: 'Hall of Fame', link: '/hall-of-fame', imgSrc: './assets/icon/star.svg', isNavbarLevelItem: true },
      { title: 'Vision', link: '/about', imgSrc: './assets/icon/shuttle.svg', isNavbarLevelItem: false },
      { title: 'Report issue', link: '/report-issue', imgSrc: './assets/icon/help.svg', isNavbarLevelItem: false }
    ];
    mainPageLink = '/';
    reportIssuePageLink = '/report-issue';

    constructor(private loginService: LoginService) {
    }

    ngOnInit() {
      this.dropdownMenuVisibility = false;
      this.menuBoxVisibility = false;
    }

    onDropdown() {
      this.menuBoxVisibility = false;
      this.dropdownMenuVisibility = !this.dropdownMenuVisibility;
    }

    onLogout() {
      this.loginService.logout();
    }

    openForm() {
      window.open(this.formUrl, '_blank');
    }

    onMenuBtnClick() {
      this.dropdownMenuVisibility = false;
      this.menuBoxVisibility = !this.menuBoxVisibility;
    }
}
