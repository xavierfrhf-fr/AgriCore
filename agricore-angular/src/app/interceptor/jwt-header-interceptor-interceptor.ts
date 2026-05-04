import { HttpInterceptorFn } from '@angular/common/http';



export const jwtHeaderInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  
  const token = localStorage.getItem('token');
  
  if(!token) {
    return next(req);
  }
  
  const jwtRequest = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(jwtRequest);
};