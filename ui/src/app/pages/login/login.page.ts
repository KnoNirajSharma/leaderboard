import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})

export class LoginPage {

  constructor(private loginService: LoginService, private router: Router) { }

  onSignIn() {
    this.loginService.signInWithGoogle().then((result) => {
      console.log('in then block');
      this.loginService.setAuthStatus(true);
      localStorage.setItem('authenticated', String(true));
      this.router.navigate(['/']);
    });
  }
}
