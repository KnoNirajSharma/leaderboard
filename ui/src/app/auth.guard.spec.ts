import { TestBed, async, inject } from '@angular/core/testing';

import { AuthGuard } from './auth.guard';
import {AuthService, AuthServiceConfig, GoogleLoginProvider, SocialLoginModule, SocialUser} from 'angular-6-social-login';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs';
import {LoginService} from './services/login.service';

class MockLoginService {
  promisedData: SocialUser = {
    provider: 'google',
    id: 'studId',
    email: 'user@g.com',
    name: 'userName',
    image: 'userImageUrl'
  };
  user: BehaviorSubject<SocialUser> = new BehaviorSubject<SocialUser>(this.promisedData);
}

class MockRouter {
  navigate(path) {}
}

describe('AuthGuard', () => {
  let guard: AuthGuard;
  const routerMock = {navigate: jasmine.createSpy('navigate')};
  let routeMock: any = { snapshot: {}};
  let routeStateMock: any = { snapshot: {}, url: '/'};
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SocialLoginModule],
      providers: [AuthGuard, {
        provide: AuthServiceConfig,
        useValue: new AuthServiceConfig([
          {id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider('id')} ])
      }, { provide: Router, useValue: routerMock }, {provide: LoginService, useValue: MockLoginService}]
    });

    guard = TestBed.get(AuthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should redirect an unauthenticated user to the login route', () => {
    expect(guard.canActivate(routeMock, routeStateMock)).toEqual(false);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });
});
