import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserSopra, NewUserSopra } from '../user-sopra.model';

export type PartialUpdateUserSopra = Partial<IUserSopra> & Pick<IUserSopra, 'id'>;

export type EntityResponseType = HttpResponse<IUserSopra>;
export type EntityArrayResponseType = HttpResponse<IUserSopra[]>;

@Injectable({ providedIn: 'root' })
export class UserSopraService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-sopras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userSopra: NewUserSopra): Observable<EntityResponseType> {
    return this.http.post<IUserSopra>(this.resourceUrl, userSopra, { observe: 'response' });
  }

  update(userSopra: IUserSopra): Observable<EntityResponseType> {
    return this.http.put<IUserSopra>(`${this.resourceUrl}/${this.getUserSopraIdentifier(userSopra)}`, userSopra, { observe: 'response' });
  }

  partialUpdate(userSopra: PartialUpdateUserSopra): Observable<EntityResponseType> {
    return this.http.patch<IUserSopra>(`${this.resourceUrl}/${this.getUserSopraIdentifier(userSopra)}`, userSopra, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserSopra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserSopra[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUserSopraIdentifier(userSopra: Pick<IUserSopra, 'id'>): number {
    return userSopra.id;
  }

  compareUserSopra(o1: Pick<IUserSopra, 'id'> | null, o2: Pick<IUserSopra, 'id'> | null): boolean {
    return o1 && o2 ? this.getUserSopraIdentifier(o1) === this.getUserSopraIdentifier(o2) : o1 === o2;
  }

  addUserSopraToCollectionIfMissing<Type extends Pick<IUserSopra, 'id'>>(
    userSopraCollection: Type[],
    ...userSoprasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const userSopras: Type[] = userSoprasToCheck.filter(isPresent);
    if (userSopras.length > 0) {
      const userSopraCollectionIdentifiers = userSopraCollection.map(userSopraItem => this.getUserSopraIdentifier(userSopraItem)!);
      const userSoprasToAdd = userSopras.filter(userSopraItem => {
        const userSopraIdentifier = this.getUserSopraIdentifier(userSopraItem);
        if (userSopraCollectionIdentifiers.includes(userSopraIdentifier)) {
          return false;
        }
        userSopraCollectionIdentifiers.push(userSopraIdentifier);
        return true;
      });
      return [...userSoprasToAdd, ...userSopraCollection];
    }
    return userSopraCollection;
  }
}
