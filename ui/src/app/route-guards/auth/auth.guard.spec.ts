import { TestBed } from '@angular/core/testing';
import { AngularFireModule } from '@angular/fire';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import {environment} from '../../../environments/environment';
import {LoginService} from '../../services/login/login.service';
import {AuthGuard} from './auth.guard';


describe('AuthGuard', () => {
  let guard: AuthGuard;
  let loginService: LoginService;
  const routerMock = { navigate: jasmine.createSpy('navigate') };
  const routeMock: any = { snapshot: {} };
  const routeStateMock: any = { snapshot: {}, url: '/' };
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule,
      ],
      providers: [AuthGuard, { provide: Router, useValue: routerMock }],
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
    spyOn(loginService, 'isAuthenticated').and.returnValue(true);
    expect(guard.canActivate(routeMock, routeStateMock)).toEqual(true);
  });
});
