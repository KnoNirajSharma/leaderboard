import { Injectable } from '@angular/core';
import { AuthService, GoogleLoginProvider } from 'angular-6-social-login';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    isAuthenticated = false;

    constructor(private socialAuthService: AuthService, private router: Router) {
    }

    googleSignIn() {
        this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
            (userData) => {
                this.isAuthenticated = true;
                localStorage.setItem('authenticated', String(this.isAuthenticated));
                this.router.navigate(['/']);
            }
        );
    }

    autoLogin() {
        const authStatus: boolean = JSON.parse(localStorage.getItem('authenticated'));
        if (!authStatus) {
            return;
        } else {
            this.isAuthenticated = authStatus;
        }
    }

    authenticationStatus(): boolean {
        return this.isAuthenticated;
    }

    logout() {
        this.isAuthenticated = false;
        localStorage.removeItem('authenticated');
        this.router.navigate(['/', 'login']);
    }
}
