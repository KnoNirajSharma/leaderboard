import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';

import {AddUserFormComponent} from './add-user-form.component';

describe('AddUserFormComponent', () => {
    let component: AddUserFormComponent;
    let fixture: ComponentFixture<AddUserFormComponent>;

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            declarations: [AddUserFormComponent],
            imports: [IonicModule.forRoot(), ReactiveFormsModule],
        }).compileComponents();

        fixture = TestBed.createComponent(AddUserFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));


    it('should make formIsNotValid to true if invalid form is submitted', () => {
        component.onSubmitForm();
        expect(component.formIsNotValid).toBeTruthy();
    });

    it('should emit event submitForm if valid form is submitted', () => {
        spyOn(component.submitForm, 'emit');
        component.addUserForm.controls.name.setValue('rahul');
        component.addUserForm.controls.emailId.setValue('rahul@knoldus.com');
        component.addUserForm.controls.wordpressId.setValue('rahul');
        component.addUserForm.controls.empId.setValue('123');
        component.onSubmitForm();
        expect(component.submitForm.emit).toHaveBeenCalledWith({
            name: 'rahul',
            emailId: 'rahul@knoldus.com',
            wordpressId: 'rahul',
            empId: '123',
            role: 'regular',
        });
    });

    it('should emit closeForm event', () => {
        spyOn(component.closeForm, 'emit');
        component.onClickCancel();
        expect(component.closeForm.emit).toHaveBeenCalled();
    });
});
