import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from './services/login.service';

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
    constructor(private loginService: LoginService, private router: Router) {
    }

    canActivate(
        route: ActivatedRouteSnapshot,
        router: RouterStateSnapshot
    ):
        | boolean
        | UrlTree
        | Promise<boolean | UrlTree>
        | Observable<boolean | UrlTree> {
        if (this.loginService.authenticationStatus()) {
            return true;
        } else {
            this.router.navigate(['/', 'login']);
            return false;
        }
    }
}
