import { fakeAsync, TestBed, tick } from '@angular/core/testing';

import { LoginService } from './login.service';
import { AuthService, AuthServiceConfig, GoogleLoginProvider, SocialLoginModule, SocialUser } from 'angular-6-social-login';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import {environment} from '../../environments/environment';

describe('LoginService', () => {
    let loginService: LoginService;
    let mockAuthService: AuthService;
    let router: Router;
    let location: Location;
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const promisedData: SocialUser = {
        provider: 'google',
        id: 'studId',
        email: 'user@g.com',
        name: 'userName',
        image: 'userImageUrl'
    };

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                SocialLoginModule,
                RouterTestingModule],
            providers: [LoginService, AuthService, {
                provide: AuthServiceConfig,
                useValue: new AuthServiceConfig([
                    {
                        id: GoogleLoginProvider.PROVIDER_ID,
                        provider: new GoogleLoginProvider(environment.googleClientId)
                    }])
            }, {provide: Router, useValue: routerSpy}]
        }).compileComponents();

        TestBed.configureTestingModule({});
        loginService = TestBed.get(LoginService);
        mockAuthService = TestBed.get(AuthService);
        router = TestBed.get(Router);
        location = TestBed.get(Location);
    });

    it('should be created', () => {
        expect(loginService).toBeTruthy();
    });

    it('should navigate after getting data from google', fakeAsync(() => {
        spyOn(mockAuthService, 'signIn').and.returnValue(Promise.resolve(promisedData));
        loginService.googleSignIn();
        tick();
        expect(loginService.isAuthenticated).toEqual(true);
        expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
    }));

    it('should return the status of authentication', () => {
        loginService.isAuthenticated = false;
        const authStatus = loginService.authenticationStatus();
        expect(authStatus).toEqual(false);
    });
});
