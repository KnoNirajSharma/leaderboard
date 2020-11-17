import {NgModule} from '@angular/core';

import {ClickOutsideDirective} from './click-outside/click-outside.directive';

@NgModule({
    imports: [],
    declarations: [
        ClickOutsideDirective,
    ],
    exports: [
        ClickOutsideDirective,
    ],
})
export class CustomDirectiveModule {
}
