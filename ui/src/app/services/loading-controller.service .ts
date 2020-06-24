import {Injectable} from '@angular/core';
import {LoadingController} from '@ionic/angular';

@Injectable({
    providedIn: 'root',
})
export class LoadingControllerService {
    isLoading = false;

    constructor(private loadingController: LoadingController) {
    }

    async present() {
        this.isLoading = true;
        return await this.loadingController
            .create({
                message: 'Loading...',
                translucent: false,
            })
            .then(loader => {
                loader.present().then(() => {
                    if (!this.isLoading) {
                        loader.dismiss();
                    }
                });
            });
    }

    async dismiss() {
        this.isLoading = false;
        return await this.loadingController.dismiss();
    }
}
