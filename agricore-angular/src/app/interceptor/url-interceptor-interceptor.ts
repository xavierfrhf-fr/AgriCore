import { HttpInterceptorFn } from '@angular/common/http';

export const urlInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  var url:string = req.url;
  if (url.startsWith('http')) {
    return next(req);
  } else if (url.startsWith('/api/')) {
    return next(
      req.clone({
        url: 'http://localhost:8080' + req.url,
      }),
    );
  } else if (url.startsWith('/')) {
    return next(
      req.clone({
        url: 'http://localhost:8080/api' + req.url,
      }),
    );
  }else{
    return next(
      req.clone({
        url: 'http://localhost:8080/api/' + req.url,
      }),
    );
  }

};
