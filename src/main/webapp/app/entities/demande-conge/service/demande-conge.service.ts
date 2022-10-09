import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeConge, NewDemandeConge } from '../demande-conge.model';

export type PartialUpdateDemandeConge = Partial<IDemandeConge> & Pick<IDemandeConge, 'id'>;

type RestOf<T extends IDemandeConge | NewDemandeConge> = Omit<T, 'creationDate' | 'dateDebutConge' | 'dateFinConge'> & {
  creationDate?: string | null;
  dateDebutConge?: string | null;
  dateFinConge?: string | null;
};

export type RestDemandeConge = RestOf<IDemandeConge>;

export type NewRestDemandeConge = RestOf<NewDemandeConge>;

export type PartialUpdateRestDemandeConge = RestOf<PartialUpdateDemandeConge>;

export type EntityResponseType = HttpResponse<IDemandeConge>;
export type EntityArrayResponseType = HttpResponse<IDemandeConge[]>;

@Injectable({ providedIn: 'root' })
export class DemandeCongeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-conges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandeConge: NewDemandeConge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeConge);
    return this.http
      .post<RestDemandeConge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(demandeConge: IDemandeConge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeConge);
    return this.http
      .put<RestDemandeConge>(`${this.resourceUrl}/${this.getDemandeCongeIdentifier(demandeConge)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(demandeConge: PartialUpdateDemandeConge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeConge);
    return this.http
      .patch<RestDemandeConge>(`${this.resourceUrl}/${this.getDemandeCongeIdentifier(demandeConge)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDemandeConge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDemandeConge[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDemandeCongeIdentifier(demandeConge: Pick<IDemandeConge, 'id'>): number {
    return demandeConge.id;
  }

  compareDemandeConge(o1: Pick<IDemandeConge, 'id'> | null, o2: Pick<IDemandeConge, 'id'> | null): boolean {
    return o1 && o2 ? this.getDemandeCongeIdentifier(o1) === this.getDemandeCongeIdentifier(o2) : o1 === o2;
  }

  addDemandeCongeToCollectionIfMissing<Type extends Pick<IDemandeConge, 'id'>>(
    demandeCongeCollection: Type[],
    ...demandeCongesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const demandeConges: Type[] = demandeCongesToCheck.filter(isPresent);
    if (demandeConges.length > 0) {
      const demandeCongeCollectionIdentifiers = demandeCongeCollection.map(
        demandeCongeItem => this.getDemandeCongeIdentifier(demandeCongeItem)!
      );
      const demandeCongesToAdd = demandeConges.filter(demandeCongeItem => {
        const demandeCongeIdentifier = this.getDemandeCongeIdentifier(demandeCongeItem);
        if (demandeCongeCollectionIdentifiers.includes(demandeCongeIdentifier)) {
          return false;
        }
        demandeCongeCollectionIdentifiers.push(demandeCongeIdentifier);
        return true;
      });
      return [...demandeCongesToAdd, ...demandeCongeCollection];
    }
    return demandeCongeCollection;
  }

  protected convertDateFromClient<T extends IDemandeConge | NewDemandeConge | PartialUpdateDemandeConge>(demandeConge: T): RestOf<T> {
    return {
      ...demandeConge,
      creationDate: demandeConge.creationDate?.toJSON() ?? null,
      dateDebutConge: demandeConge.dateDebutConge?.toJSON() ?? null,
      dateFinConge: demandeConge.dateFinConge?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDemandeConge: RestDemandeConge): IDemandeConge {
    return {
      ...restDemandeConge,
      creationDate: restDemandeConge.creationDate ? dayjs(restDemandeConge.creationDate) : undefined,
      dateDebutConge: restDemandeConge.dateDebutConge ? dayjs(restDemandeConge.dateDebutConge) : undefined,
      dateFinConge: restDemandeConge.dateFinConge ? dayjs(restDemandeConge.dateFinConge) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDemandeConge>): HttpResponse<IDemandeConge> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDemandeConge[]>): HttpResponse<IDemandeConge[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
