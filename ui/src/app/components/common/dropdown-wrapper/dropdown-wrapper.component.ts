import {Component, ElementRef, HostListener, ViewChild} from '@angular/core';

@Component({
    selector: 'app-dropdown-wrapper',
    templateUrl: './dropdown-wrapper.component.html',
})
export class DropdownWrapperComponent {
    dropdownMenuVisibility = false;
    @ViewChild('dropdownIcon') dropdownIconRef: ElementRef;

    controlDropdownMenu(action: string) {
        if (action === 'close') {
            this.dropdownMenuVisibility = false;
        } else {
            this.dropdownMenuVisibility = !this.dropdownMenuVisibility;
        }
    }

    @HostListener('document:click', ['$event.target'])
    public onClick(target) {
        if (!this.dropdownIconRef.nativeElement.contains(target)) {
            this.controlDropdownMenu('close');
        } else {
            this.controlDropdownMenu('toggle');
        }
    }
}
