import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserSopra } from '../user-sopra.model';
import { UserSopraService } from '../service/user-sopra.service';

@Injectable({ providedIn: 'root' })
export class UserSopraRoutingResolveService implements Resolve<IUserSopra | null> {
  constructor(protected service: UserSopraService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserSopra | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userSopra: HttpResponse<IUserSopra>) => {
          if (userSopra.body) {
            return of(userSopra.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
