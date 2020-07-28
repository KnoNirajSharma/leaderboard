import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit() {
  }

  onSignIn() {
    this.loginService.signInWithGoogle().then((result) => {
      this.loginService.setAuthStatus(true);
      localStorage.setItem('authenticated', String(true));
      this.router.navigate(['/']);
    });
  }
}
