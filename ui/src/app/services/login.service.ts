import { Injectable } from '@angular/core';
import {AuthService, GoogleLoginProvider} from 'angular-6-social-login';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor( private socialAuthService: AuthService, private router: Router) {
  }

  googleSignIn() {
      this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
        (userData) => {
            this.router.navigate(['/']);
        }
    );
  }
}
