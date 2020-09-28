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
    title = 'LEADERBOARD';
    formUrl = 'https://docs.google.com/forms/d/e/1FAIpQLSfjOGd2TI-zYb2b3_lpLnn-Kk_K57SAKQtjPsb7to9XzY6-tw/viewform';
    navItems: NavBarItemModel[] = [
      { title: 'Hall of Fame', link: '/hall-of-fame' },
      { title: 'Vision', link: '/about' }
    ];
    mainPageLink = '/';

    constructor(private loginService: LoginService) {
    }

    ngOnInit() {
      this.dropdownMenuVisibility = false;
    }

    onDropdown() {
      this.dropdownMenuVisibility = !this.dropdownMenuVisibility;
    }

    onLogout() {
      this.loginService.logout();
    }

    openForm() {
      window.open(this.formUrl, '_blank');
    }
}
