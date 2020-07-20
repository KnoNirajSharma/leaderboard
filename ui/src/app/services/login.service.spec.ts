import {async, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { LoginService } from './login.service';
import {AuthService, AuthServiceConfig, GoogleLoginProvider, SocialLoginModule, SocialUser} from 'angular-6-social-login';
import {RouterTestingModule} from '@angular/router/testing';
import {getAuthServiceConfigs} from '../app.module';
import {EmployeeActivityService} from './employee-activity.service';
import {HttpTestingController} from '@angular/common/http/testing';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {BehaviorSubject} from 'rxjs';

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

    let store = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in store ? store[key] : null;
      },
      setItem: (key: string, value: string) => {
        store[key] = `${value}`;
      },
      removeItem: (key: string) => {
        delete store[key];
      }
    };

    spyOn(localStorage, 'getItem')
        .and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem')
        .and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, 'removeItem')
        .and.callFake(mockLocalStorage.removeItem);

    TestBed.configureTestingModule({
    imports: [
      SocialLoginModule,
      RouterTestingModule],
    providers: [ LoginService, AuthService, {
      provide: AuthServiceConfig,
      useValue: new AuthServiceConfig([
        {id: GoogleLoginProvider.PROVIDER_ID,
          provider: new GoogleLoginProvider('id')} ])
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

  it('should navigate after getting data from google', fakeAsync(()  => {
    spyOn(mockAuthService, 'signIn').and.returnValue(Promise.resolve(promisedData));
    loginService.googleSignIn();
    tick();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  }));

  it('should store in localStorage after getting data from google', fakeAsync(()  => {
    spyOn(mockAuthService, 'signIn').and.returnValue(Promise.resolve(promisedData));
    loginService.googleSignIn();
    tick();
    expect(localStorage.getItem('userData')).toEqual( JSON.stringify(promisedData));
  }));

  it('should emit user data if present in local storage', () => {
    localStorage.setItem('userData', JSON.stringify(promisedData));
    loginService.autoLogin();
    expect(loginService.user.value).toEqual(promisedData);
  });

  it('should emit null if present in local storage', () => {
    localStorage.setItem('userData', null);
    loginService.autoLogin();
    expect(loginService.user.value).toEqual(null);
  });
});
