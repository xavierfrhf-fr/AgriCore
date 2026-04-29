import { HttpInterceptorFn } from '@angular/common/http';

export const jwtHeaderInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(
    req.clone({
      setHeaders: {
        Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJYYXYyIiwiaWF0IjoxNzc3NDAwMzA3LCJleHAiOjE3Nzc0MDM5MDd9.oceQsUW8iMkwV2l_jS2TuKPSf5CqIxoUX1yRZ3_bMw5_3Ojmu1FyHFZrldaedWxVWJwvP1jea1MugP__bcB8uQ`,
      },
    }),
  );
};
