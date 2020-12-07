import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {LoginService} from '../../login/login.service';


@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
    constructor(private loginService: LoginService, private router: Router) {
    }

    canActivate(
        route: ActivatedRouteSnapshot,
        router: RouterStateSnapshot,
    ):
        | boolean
        | UrlTree
        | Promise<boolean | UrlTree>
        | Observable<boolean | UrlTree> {
        if (this.loginService.isAuthenticated() && this.loginService.isAdmin()) {
            return true;
        } else {
            this.router.navigate(['/']);
            return false;
        }
    }
}
