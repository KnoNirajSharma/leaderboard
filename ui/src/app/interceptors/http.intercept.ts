import {
    HttpEvent,
    HttpHandler,
    HttpRequest,
    HttpErrorResponse,
    HttpInterceptor
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
export class HttpIntercept implements HttpInterceptor {
    intercept( request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        request = request.clone({ headers: request.headers.set('Accept', '/assets/data/authorProfile.json') });
        return next.handle(request)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    let errorMessage: string;
                    if (error instanceof ErrorEvent) {
                        // client-side error
                        errorMessage = `Error: ${error.message}`;
                    } else {
                        // server-side error
                        errorMessage = `Error Status: ${error.status}\nMessage: ${error.message}`;
                    }
                    return throwError(errorMessage);
                })
            );
    }
}
