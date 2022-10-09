import dayjs from 'dayjs/esm';

import { IDemande, NewDemande } from './demande.model';

export const sampleWithRequiredData: IDemande = {
  id: 19730,
};

export const sampleWithPartialData: IDemande = {
  id: 16492,
  startDemande: dayjs('2022-10-07T12:58'),
  endDemande: dayjs('2022-10-07T09:31'),
};

export const sampleWithFullData: IDemande = {
  id: 9184,
  startDemande: dayjs('2022-10-07T15:37'),
  endDemande: dayjs('2022-10-07T03:35'),
  statusDemande: dayjs('2022-10-07T14:07'),
};

export const sampleWithNewData: NewDemande = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
