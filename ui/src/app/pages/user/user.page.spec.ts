import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import {AngularFireModule} from '@angular/fire';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {ReactiveFormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';

import {environment} from '../../../environments/environment';
import {ComponentsModule} from '../../components/components.module';
import {CustomPipesModule} from '../../pipe/custom-pipes.module';
import {UserPage} from './user.page';

describe('UserPage', () => {
    let component: UserPage;
    let fixture: ComponentFixture<UserPage>;

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            declarations: [UserPage],
            imports: [
                IonicModule.forRoot(),
                RouterTestingModule,
                ReactiveFormsModule,
                ComponentsModule,
                AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
                AngularFirestoreModule,
                AngularFireAuthModule,
                CustomPipesModule,
            ],
        }).compileComponents();

        fixture = TestBed.createComponent(UserPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
