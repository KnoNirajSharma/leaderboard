import {Directive, ElementRef, EventEmitter, HostListener, Output} from '@angular/core';

@Directive({
    selector: '[appClickOutside]',
})
export class ClickOutsideDirective {

    @Output() clickOutside = new EventEmitter<void>();

    constructor(private elementRef: ElementRef) {
    }

    @HostListener('document:click', ['$event.target'])
    public onClick(target) {
        if (!this.elementRef.nativeElement.contains(target)) {
            this.clickOutside.emit();
        }
    }
}
