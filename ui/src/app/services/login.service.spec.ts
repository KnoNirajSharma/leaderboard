import { TestBed } from '@angular/core/testing';
import { LoginService } from './login.service';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import {environment} from '../../environments/environment';
import { AngularFireAuth, AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule } from '@angular/fire';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import {mockResponseUserData } from '../../assets/data/mockFirebaseResponse';

describe('LoginService', () => {
    let loginService: LoginService;
    let mockAuthService: AngularFireAuth;
    let router: Router;
    let location: Location;
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const promisedData = mockResponseUserData;

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
            imports: [RouterTestingModule,
                AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
                AngularFirestoreModule,
                AngularFireAuthModule],
            providers: [LoginService, AngularFireAuth,
                {provide: Router, useValue: routerSpy}]
        }).compileComponents();

        TestBed.configureTestingModule({});
        loginService = TestBed.get(LoginService);
        mockAuthService = TestBed.get(AngularFireAuth);
        router = TestBed.get(Router);
        location = TestBed.get(Location);
    });

    it('should be created', () => {
        expect(loginService).toBeTruthy();
    });

    it('should getFoo', (done) => {
        spyOn(mockAuthService, 'signInWithPopup').and.returnValue(Promise.resolve(promisedData));
        const result = loginService.signInWithGoogle();
        result
            .then(rslt => expect(rslt).toBe(promisedData))
            .then(done);
    });

    it('should return the status of authentication', () => {
        loginService.setAuthStatus(false);
        const authStatus = loginService.authenticationStatus();
        expect(authStatus).toEqual(false);
    });

    it('should change auth status if present in local storage', () => {
        localStorage.setItem('authenticated', String(true));
        loginService.autoLogin();
        const authStatus = loginService.authenticationStatus();
        expect(authStatus).toEqual(true);
    });

    it('should set auth status as false if not present in local storage', () => {
        localStorage.setItem('authenticated', null);
        loginService.autoLogin();
        const authStatus = loginService.authenticationStatus();
        expect(authStatus).toEqual(false);
    });

    it('should set auth status as false and remove data from local storage and navigate to login page', () => {
        localStorage.setItem('authenticated', String(true));
        loginService.logout();
        const authStatus = loginService.authenticationStatus();
        expect(authStatus).toEqual(false);
        expect(localStorage.getItem('authenticated')).toEqual(null);
        expect(routerSpy.navigate).toHaveBeenCalledWith(['/', 'login']);
    });
});
