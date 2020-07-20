import { Injectable } from '@angular/core';
import {AuthService, GoogleLoginProvider, SocialUser} from 'angular-6-social-login';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
    isAuthenticated = false;

  constructor( private socialAuthService: AuthService, private router: Router) {
  }

  googleSignIn() {
      this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
        (userData) => {
            this.isAuthenticated = true;
            this.router.navigate(['/']);
        }
    );
  }

  authenticationStatus(): boolean {
      return this.isAuthenticated;
  }
}
