import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { Router } from '@angular/router';
import * as firebase from 'firebase';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private router: Router, private firebaseAuth: AngularFireAuth) {
  }

  signInWithGoogle() {
    return this.firebaseAuth.signInWithPopup(new firebase.auth.GoogleAuthProvider());
  }

  isAuthenticated(): boolean {
    return JSON.parse(localStorage.getItem('authenticated'));
  }

  isAdmin(): boolean {
    return JSON.parse(localStorage.getItem('admin'));
  }

  logout() {
    localStorage.removeItem('authenticated');
    localStorage.removeItem('admin');
    this.router.navigate(['/', 'login']);
  }
}
