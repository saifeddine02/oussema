import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeConge } from '../demande-conge.model';
import { DemandeCongeService } from '../service/demande-conge.service';

@Injectable({ providedIn: 'root' })
export class DemandeCongeRoutingResolveService implements Resolve<IDemandeConge | null> {
  constructor(protected service: DemandeCongeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeConge | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandeConge: HttpResponse<IDemandeConge>) => {
          if (demandeConge.body) {
            return of(demandeConge.body);
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
