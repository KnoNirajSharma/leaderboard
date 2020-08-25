import { Component, Input, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-headers',
  templateUrl: './headers.component.html',
  styleUrls: ['./headers.component.scss'],
})
export class HeadersComponent implements OnInit {
    @Input() backBtn: boolean;
    @Input() backBtnLink: string;
    logoutBtnVisibility: boolean;
    title = 'LEADERBOARD';
    formUrl = 'https://docs.google.com/forms/d/e/1FAIpQLSfjOGd2TI-zYb2b3_lpLnn-Kk_K57SAKQtjPsb7to9XzY6-tw/viewform';

    constructor(private loginService: LoginService) {
    }

    ngOnInit() {
      this.logoutBtnVisibility = false;
    }

    onDropdown() {
      this.logoutBtnVisibility = !this.logoutBtnVisibility;
    }

    onLogout() {
      this.loginService.logout();
    }

    openForm() {
      window.open(this.formUrl, '_blank');
    }
}
