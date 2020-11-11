import { Location } from '@angular/common';
import { TestBed } from '@angular/core/testing';
import { AngularFireModule } from '@angular/fire';
import { AngularFireAuth, AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import {mockResponseUserData} from '../../../assets/data/mockFirebaseResponse';
import {environment} from '../../../environments/environment';
import { LoginService } from './login.service';

describe('LoginService', () => {
    let loginService: LoginService;
    let mockAuthService: AngularFireAuth;
    let router: Router;
    let location: Location;
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const promisedData = mockResponseUserData;

    beforeEach(() => {
        const store = {};
        const mockLocalStorage = {
            getItem: (key: string): string => {
                return key in store ? store[key] : null;
            },
            setItem: (key: string, value: string) => {
                store[key] = `${value}`;
            },
            removeItem: (key: string) => {
                delete store[key];
            },
        };

        spyOn(localStorage, 'getItem')
            .and.callFake(mockLocalStorage.getItem);
        spyOn(localStorage, 'setItem')
            .and.callFake(mockLocalStorage.setItem);
        spyOn(localStorage, 'removeItem')
            .and.callFake(mockLocalStorage.removeItem);

        TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
                AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
                AngularFirestoreModule,
                AngularFireAuthModule,
            ],
            providers: [
                LoginService,
                AngularFireAuth,
                { provide: Router, useValue: routerSpy },
            ],
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

    it('should get user credentials after sign in', (done) => {
        spyOn(mockAuthService, 'signInWithPopup').and.returnValue(Promise.resolve(promisedData));
        const result = loginService.signInWithGoogle();
        result
            .then(rslt => expect(rslt).toBe(promisedData))
            .then(done);
    });

    it('should return the auth status as true based on local storage value', () => {
        localStorage.setItem('authenticated', String(true));
        const authStatus = loginService.isAuthenticated();
        expect(authStatus).toEqual(true);
    });

    it('should return the auth status as false based on local storage value', () => {
        localStorage.setItem('authenticated', String(false));
        const authStatus = loginService.isAuthenticated();
        expect(authStatus).toEqual(false);
    });
});
