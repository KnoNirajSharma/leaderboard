import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {AngularFireModule} from '@angular/fire';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';

import {environment} from '../../../environments/environment';
import {ComponentsModule} from '../../components/components.module';
import {AdminPage} from './admin.page';

describe('AdminPage', () => {
    let component: AdminPage;
    let fixture: ComponentFixture<AdminPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AdminPage],
            imports: [
                IonicModule.forRoot(),
                ComponentsModule,
                RouterTestingModule,
                AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
                AngularFirestoreModule,
                AngularFireAuthModule,
            ],
        }).compileComponents();

        fixture = TestBed.createComponent(AdminPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
