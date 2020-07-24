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

  onSignIn(): void {
    this.loginService.googleSignIn().then((result) => {
      this.loginService.setAuthStatus(true);
      this.router.navigate(['/']);
    // this.loginService.googleSignIn();
    });
}
}
