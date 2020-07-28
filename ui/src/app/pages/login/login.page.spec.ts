import { async, ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { LoginPage } from './login.page';
import { RouterTestingModule } from '@angular/router/testing';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { userData } from '../../../assets/data/mockFirebaseResponse';

describe('LoginPage', () => {
    let component: LoginPage;
    let fixture: ComponentFixture<LoginPage>;
    let loginService: LoginService;
    let router: Router;
    let location: Location;
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const responseData = userData;
    const promisedData = responseData;

    beforeEach(async(() => {

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
            }
        };

        spyOn(localStorage, 'getItem')
            .and.callFake(mockLocalStorage.getItem);
        spyOn(localStorage, 'setItem')
            .and.callFake(mockLocalStorage.setItem);
        spyOn(localStorage, 'removeItem')
            .and.callFake(mockLocalStorage.removeItem);

        TestBed.configureTestingModule({
            declarations: [LoginPage],
            imports: [IonicModule.forRoot(),
                RouterTestingModule,
                AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
                AngularFirestoreModule,
                AngularFireAuthModule
            ],
            providers: [{provide: Router, useValue: routerSpy}]
        }).compileComponents();

        fixture = TestBed.createComponent(LoginPage);
        component = fixture.componentInstance;
        loginService = TestBed.get(LoginService);
        router = TestBed.get(Router);
        location = TestBed.get(Location);
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should store auth Status in local storage, navigate to main page of getting data of user', fakeAsync(() => {
        spyOn(loginService, 'signInWithGoogle').and.returnValue(Promise.resolve(promisedData));
        component.onSignIn();
        tick();
        expect(localStorage.getItem('authenticated')).toEqual(String(true));
        expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
    }));
});
