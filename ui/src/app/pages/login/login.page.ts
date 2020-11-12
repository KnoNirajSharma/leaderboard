import {Component} from '@angular/core';
import {Router} from '@angular/router';

import {LoginService} from '../../services/login/login.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.page.html',
    styleUrls: ['./login.page.scss'],
})

export class LoginPage {

    constructor(private loginService: LoginService, private router: Router) {
    }

    onSignIn() {
        this.loginService.signInWithGoogle().then((result) => {
            localStorage.setItem('authenticated', String(true));
            this.router.navigate(['/']);
        });
    }
}
