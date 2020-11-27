import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
    selector: 'app-add-user-form',
    templateUrl: './add-user-form.component.html',
    styleUrls: ['./add-user-form.component.scss'],
})
export class AddUserFormComponent {
    @Output() submitForm = new EventEmitter();
    @Output() closeForm = new EventEmitter();
    addUserForm = new FormGroup({
        name: new FormControl(null, Validators.required),
        emailId: new FormControl(null, [Validators.required, Validators.email]),
        empId: new FormControl(null, Validators.required),
        wordpressId: new FormControl(null, Validators.required),
        role: new FormControl('regular', Validators.required),
    });
    formIsNotValid = null;

    onSubmitForm() {
        if (this.addUserForm.status === 'INVALID') {
            this.formIsNotValid = true;
        } else {
            this.submitForm.emit(this.addUserForm.value);
        }
    }

    onClickCancel() {
        this.closeForm.emit();
    }
}
