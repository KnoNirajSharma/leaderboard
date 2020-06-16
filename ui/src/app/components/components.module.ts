import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {IonicModule} from '@ionic/angular';
import {HeadersComponent} from './headers/headers.component';
import {RouterModule} from '@angular/router';

@NgModule({
    imports: [IonicModule, CommonModule, RouterModule],
    exports: [
        HeadersComponent
    ],
    declarations: [HeadersComponent]
})
export class ComponentsModule {
}
