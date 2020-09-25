import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { AboutPage } from './about.page';
import { ComponentsModule } from '../../components/components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {BsDatepickerModule} from 'ngx-bootstrap/datepicker';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AngularFireModule} from '@angular/fire';
import {environment} from '../../../environments/environment';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {AngularFireAuthModule} from '@angular/fire/auth';

describe('AboutPage', () => {
  let component: AboutPage;
  let fixture: ComponentFixture<AboutPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AboutPage ],
      imports: [
        IonicModule.forRoot(),
        RouterTestingModule,
        ComponentsModule,
        RouterTestingModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AboutPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
