import dayjs from 'dayjs/esm';

import { StatusDemande } from 'app/entities/enumerations/status-demande.model';

import { IDemandeConge, NewDemandeConge } from './demande-conge.model';

export const sampleWithRequiredData: IDemandeConge = {
  id: 30746,
};

export const sampleWithPartialData: IDemandeConge = {
  id: 80165,
  creationDate: dayjs('2022-10-07T07:53'),
  avisTl: true,
  dateFinConge: dayjs('2022-10-07T19:36'),
};

export const sampleWithFullData: IDemandeConge = {
  id: 96151,
  creationDate: dayjs('2022-10-07T22:53'),
  avisTl: false,
  statusDemande: StatusDemande['ENCOURS'],
  dateDebutConge: dayjs('2022-10-07T14:25'),
  dateFinConge: dayjs('2022-10-07T19:55'),
  nombreJour: 68509,
};

export const sampleWithNewData: NewDemandeConge = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
