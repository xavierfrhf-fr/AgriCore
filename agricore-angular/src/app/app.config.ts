import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { urlInterceptorInterceptor } from './interceptor/url-interceptor-interceptor';
import { jwtHeaderInterceptorInterceptor } from './interceptor/jwt-header-interceptor-interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(withInterceptors([urlInterceptorInterceptor, jwtHeaderInterceptorInterceptor])),
    provideRouter(routes)
  ]
};
