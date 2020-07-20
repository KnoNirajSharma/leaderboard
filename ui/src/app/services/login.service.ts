import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AuthService, GoogleLoginProvider, SocialUser } from 'angular-6-social-login';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    user: BehaviorSubject<SocialUser> = new BehaviorSubject<SocialUser>(null);
    isAuthenticated = false;

    constructor(private socialAuthService: AuthService, private router: Router) {
    }

    googleSignIn() {
        this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
            (userData) => {
                this.user.next(userData);
                this.isAuthenticated = true;
                localStorage.setItem('userData', JSON.stringify(userData));
                localStorage.setItem('authenticated', String(this.isAuthenticated));
                this.router.navigate(['/']);
            }
        );
    }

    autoLogin() {
        const userData: SocialUser = JSON.parse(localStorage.getItem('userData'));
        const authStatus: boolean = JSON.parse(localStorage.getItem('authenticated'));
        if (!authStatus) {
            return;
        } else {
            this.isAuthenticated = authStatus;
            this.user.next(userData);
        }
    }

    authenticationStatus(): boolean {
        return this.isAuthenticated;
    }
}
