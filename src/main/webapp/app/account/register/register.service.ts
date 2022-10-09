import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Registration } from './register.model';
import { IUserSopra, NewUserSopra } from '../../entities/user-sopra/user-sopra.model';

  export type EntityResponseType = HttpResponse<IUserSopra>;
  export type EntityArrayResponseType = HttpResponse<IUserSopra[]>;
@Injectable({ providedIn: 'root' })
export class RegisterService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-sopras');

  save(registration: Registration): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/register'), registration);
  }
   create(userSopra: NewUserSopra): Observable<EntityResponseType> {
     return this.http.post<IUserSopra>(this.resourceUrl, userSopra, { observe: 'response' });
   }
}
