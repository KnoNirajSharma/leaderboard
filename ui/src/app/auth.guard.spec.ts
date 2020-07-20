import { TestBed, async, inject } from '@angular/core/testing';

import { AuthGuard } from './auth.guard';
import {AuthService, AuthServiceConfig, GoogleLoginProvider, SocialLoginModule, SocialUser} from 'angular-6-social-login';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs';
import {LoginService} from './services/login.service';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let loginService: LoginService;
  const routerMock = {navigate: jasmine.createSpy('navigate')};
  const routeMock: any = { snapshot: {}};
  const routeStateMock: any = { snapshot: {}, url: '/'};
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SocialLoginModule],
      providers: [AuthGuard, {
        provide: AuthServiceConfig,
        useValue: new AuthServiceConfig([
          {id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider('id')} ])
      }, { provide: Router, useValue: routerMock }]
    });

    guard = TestBed.get(AuthGuard);
    loginService = TestBed.get(LoginService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should redirect an unauthenticated user to the login route', () => {
    expect(guard.canActivate(routeMock, routeStateMock)).toEqual(false);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/', 'login']);
  });

  it('should return true for authenticated user', () => {
    spyOn(loginService, 'authenticationStatus').and.returnValue(true);
    expect(guard.canActivate(routeMock, routeStateMock)).toEqual(true);
  });
});
