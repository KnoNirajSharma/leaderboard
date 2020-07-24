import { Injectable } from '@angular/core';
import { AuthService, GoogleLoginProvider } from 'angular-6-social-login';
import { Router } from '@angular/router';
import {AngularFireAuth} from '@angular/fire/auth';
import * as firebase from 'firebase';

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    private isAuthenticated = false;

    constructor(private socialAuthService: AuthService, private firebaseAuth: AngularFireAuth, private router: Router) {
    }

    googleSignIn() {
        // this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
        //     (userData) => {
        //         this.isAuthenticated = true;
        //         localStorage.setItem('authenticated', String(this.isAuthenticated));
        //         this.router.navigate(['/']);
        //     }
        // );
        return this.firebaseAuth.auth.signInWithPopup(new firebase.auth.GoogleAuthProvider());
    }

    autoLogin() {
        const authStatus: boolean = JSON.parse(localStorage.getItem('authenticated'));
        if (!authStatus) {
            return;
        } else {
            this.isAuthenticated = authStatus;
        }
    }

    setAuthStatus(status: boolean) {
        this.isAuthenticated =  status;
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
