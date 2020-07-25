import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AngularFireAuth } from '@angular/fire/auth';
import * as firebase from 'firebase';

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    private isAuthenticated = false;

    constructor(private router: Router, private firebaseAuth: AngularFireAuth) {
    }

    signInWithGoogle() {
        return this.firebaseAuth.signInWithPopup(new firebase.auth.GoogleAuthProvider());
    }

    setAuthStatus(status: boolean) {
        this.isAuthenticated =  status;
    }

    authenticationStatus(): boolean {
        return this.isAuthenticated;
    }

    autoLogin() {
        const authStatus: boolean = JSON.parse(localStorage.getItem('authenticated'));
        if (!authStatus) {
            return;
        } else {
            this.isAuthenticated = authStatus;
        }
    }

    logout() {
        this.isAuthenticated = false;
        localStorage.removeItem('authenticated');
        this.router.navigate(['/', 'login']);
    }
}
