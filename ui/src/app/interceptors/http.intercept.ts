import {
  HttpEvent,
  HttpHandler,
  HttpRequest,
  HttpErrorResponse,
  HttpInterceptor, HttpEventType
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { LoadingController, ToastController } from '@ionic/angular';
import { Injectable } from '@angular/core';

@Injectable()
export class HttpIntercept implements HttpInterceptor {
  constructor(public loadingCtrl: LoadingController, private toastCtrl: ToastController) {
  }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const modifiedRequest = request.clone({
      setHeaders: {
        'Content-Type': 'application/json'
      },
    });

    const loading = this.loadingCtrl.create({
      message: 'Please wait...'
    });

    loading.then(loader => {
      loader.present();
    });

    return next.handle(modifiedRequest)
      .pipe(
        tap(event => {
          if (event.type === HttpEventType.Response) {
            loading.then(loader => {
              loader.dismiss();
            });
          }
        }),
        catchError((error: HttpErrorResponse) => {
          const errorMessage = `Error Status: ${error.status}`;
          console.log('error 2');
          loading.then(loader => {
            loader.dismiss();
          });
          this.toastCtrl.create({
            message: 'Error Occurred\n' + errorMessage,
            buttons: [{ text: 'close' }],
            position: 'top',
            color: 'danger'
          }).then(toaster => toaster.present());
          return throwError(errorMessage);
        })
      );
  }
}
