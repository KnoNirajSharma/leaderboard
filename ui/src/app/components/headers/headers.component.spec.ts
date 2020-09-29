import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { HeadersComponent } from './headers.component';
import { RouterTestingModule } from '@angular/router/testing';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { LoginService } from '../../services/login.service';
import {MenuBoxComponent} from '../menu-box/menu-box.component';

describe('HeadersComponent', () => {
  let component: HeadersComponent;
  let loginService: LoginService;
  let fixture: ComponentFixture<HeadersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        HeadersComponent,
        MenuBoxComponent
      ],
      imports: [
        IonicModule.forRoot(),
        RouterTestingModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(HeadersComponent);
    component = fixture.componentInstance;
    loginService = TestBed.get(LoginService);
  }));

  it('should set dropDownMenuVisibility and menuBoxVisibility value to false', () => {
    component.ngOnInit();
    expect(component.dropdownMenuVisibility).toEqual(false);
    expect(component.menuBoxVisibility).toEqual(false);
  });

  it('should change the visibility status for dropdown menu button and make menu-box visibility false', () => {
    component.dropdownMenuVisibility = false;
    component.menuBoxVisibility = true;
    component.onDropdown();
    expect(component.dropdownMenuVisibility).toEqual(true);
    expect(component.menuBoxVisibility).toEqual(false);
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

  it('should change the visibility status for menu-box and dropdown menu visibility false', () => {
    component.dropdownMenuVisibility = true;
    component.menuBoxVisibility = false;
    component.onMenuBtnClick();
    expect(component.menuBoxVisibility).toEqual(true);
    expect(component.dropdownMenuVisibility).toEqual(false);
  });
});
