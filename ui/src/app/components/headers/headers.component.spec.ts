import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { HeadersComponent } from './headers.component';
import { RouterTestingModule } from '@angular/router/testing';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { LoginService } from '../../services/login.service';

describe('HeadersComponent', () => {
  let component: HeadersComponent;
  let loginService: LoginService;
  let fixture: ComponentFixture<HeadersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        HeadersComponent
      ],
      imports: [
        IonicModule.forRoot(),
        RouterTestingModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(HeadersComponent);
    component = fixture.componentInstance;
    loginService = TestBed.get(LoginService);
  }));

  it('should set dropDownMenuVisibility value to false', () => {
    component.ngOnInit();
    expect(component.dropdownMenuVisibility).toEqual(false);
  });

  it('should change the visibility status for dropdown menu button', () => {
    component.dropdownMenuVisibility = false;
    component.onDropdown();
    expect(component.dropdownMenuVisibility).toEqual(true);
  });

  it('should call window.open with form url', () => {
    spyOn(window, 'open');
    component.openForm();
    expect(window.open).toHaveBeenCalledWith(component.formUrl, '_blank');
  });

  it('should call window.open with form url', () => {
    spyOn(loginService, 'logout');
    component.onLogout();
    expect(loginService.logout).toHaveBeenCalled();
  });
});
