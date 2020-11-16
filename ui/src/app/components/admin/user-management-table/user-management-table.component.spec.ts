import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {CustomPipesModule} from '../../../pipe/custom-pipes.module';
import {ComponentsModule} from '../../components.module';
import {UserManagementTableComponent} from './user-management-table.component';

describe('UserManagementTableComponent', () => {
    let component: UserManagementTableComponent;
    let fixture: ComponentFixture<UserManagementTableComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [],
            imports: [IonicModule.forRoot(), CustomPipesModule, ComponentsModule],
        }).compileComponents();

        fixture = TestBed.createComponent(UserManagementTableComponent);
        component = fixture.componentInstance;
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
