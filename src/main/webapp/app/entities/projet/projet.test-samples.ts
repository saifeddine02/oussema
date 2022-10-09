import dayjs from 'dayjs/esm';

import { IProjet, NewProjet } from './projet.model';

export const sampleWithRequiredData: IProjet = {
  id: 59803,
};

export const sampleWithPartialData: IProjet = {
  id: 36959,
  nomProjet: 'Account',
  paysProjet: 'Profit-focused',
  regionProjet: 'IB',
  nombreRessource: 34583,
};

export const sampleWithFullData: IProjet = {
  id: 81836,
  nomProjet: 'Investment',
  paysProjet: 'alarm',
  regionProjet: 'Concrete c payment',
  dateDebut: dayjs('2022-10-07T19:22'),
  nombreRessource: 72234,
  dateFin: dayjs('2022-10-07T23:10'),
  competanceDemander: 'parsing b',
};

export const sampleWithNewData: NewProjet = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
