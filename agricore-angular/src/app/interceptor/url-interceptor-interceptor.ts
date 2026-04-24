import { HttpInterceptorFn } from '@angular/common/http';

export const urlInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};
