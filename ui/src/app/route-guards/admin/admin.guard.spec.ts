import {TestBed} from '@angular/core/testing';
import {AngularFireModule} from '@angular/fire';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';

import {environment} from '../../../environments/environment';
import {LoginService} from '../../services/login/login.service';
import {AdminGuard} from './admin.guard';

describe('AdminGuard', () => {
    let guard: AdminGuard;
    let loginService: LoginService;
    const routerMock = {navigate: jasmine.createSpy('navigate')};
    const routeMock: any = {snapshot: {}};
    const routeStateMock: any = {snapshot: {}, url: '/'};
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
                AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
                AngularFirestoreModule,
                AngularFireAuthModule,
            ],
            providers: [AdminGuard, {provide: Router, useValue: routerMock}],
        });

        guard = TestBed.get(AdminGuard);
        loginService = TestBed.get(LoginService);
    });

    it('should redirect an authenticated and not admin user to the / route', () => {
        spyOn(loginService, 'isAuthenticated').and.returnValue(true);
        spyOn(loginService, 'isAdmin').and.returnValue(false);
        expect(guard.canActivate(routeMock, routeStateMock)).toEqual(false);
        expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
    });


    it('should redirect to the / route if null value found in local storage', () => {
        spyOn(loginService, 'isAuthenticated').and.returnValue(true);
        spyOn(loginService, 'isAdmin').and.returnValue(null);
        expect(guard.canActivate(routeMock, routeStateMock)).toEqual(false);
        expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
    });

    it('should return true for authenticated and admin user', () => {
        spyOn(loginService, 'isAuthenticated').and.returnValue(true);
        spyOn(loginService, 'isAdmin').and.returnValue(true);
        expect(guard.canActivate(routeMock, routeStateMock)).toEqual(true);
    });
});
