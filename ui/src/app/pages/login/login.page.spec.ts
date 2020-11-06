import { Location } from '@angular/common';
import { async, ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { AngularFireModule } from '@angular/fire';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { IonicModule } from '@ionic/angular';

import { mockResponseUserData } from '../../../assets/data/mockFirebaseResponse';
import { environment } from '../../../environments/environment';
import { LoginService } from '../../services/login.service';
import { LoginPage } from './login.page';

describe('LoginPage', () => {
  let component: LoginPage;
  let fixture: ComponentFixture<LoginPage>;
  let loginService: LoginService;
  let router: Router;
  let location: Location;
  const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
  const promisedData = mockResponseUserData;

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
      },
    };

    spyOn(localStorage, 'getItem')
        .and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem')
        .and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, 'removeItem')
        .and.callFake(mockLocalStorage.removeItem);

    TestBed.configureTestingModule({
      declarations: [LoginPage],
      imports: [
        IonicModule.forRoot(),
        RouterTestingModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule,
      ],
      providers: [{ provide: Router, useValue: routerSpy }],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginPage);
    component = fixture.componentInstance;
    loginService = TestBed.get(LoginService);
    router = TestBed.get(Router);
    location = TestBed.get(Location);
    fixture.detectChanges();
  }));

  it('should store auth Status in local storage, navigate to main page of getting data of user', async () => {
    spyOn(loginService, 'signInWithGoogle').and.returnValue(Promise.resolve(promisedData));
    spyOn(loginService, 'setAuthStatus');
    await component.onSignIn();
    expect(localStorage.getItem('authenticated')).toEqual(String(true));
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
    expect(loginService.setAuthStatus).toHaveBeenCalled();
  });
});
