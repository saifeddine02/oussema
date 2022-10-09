import { UserRolesSopra } from 'app/entities/enumerations/user-roles-sopra.model';

import { IUserSopra, NewUserSopra } from './user-sopra.model';

export const sampleWithRequiredData: IUserSopra = {
  id: 16334,
};

export const sampleWithPartialData: IUserSopra = {
  id: 31935,
  nomUser: "Keyboard d'Abbeville",
  prenomUser: 'Provence-Alpes-Côte',
  emailUser: 'la',
  matriculeUser: 'Coordinateur payment',
};

export const sampleWithFullData: IUserSopra = {
  id: 65018,
  nomUser: 'Games b initiative',
  prenomUser: 'mobile Tasty vortals',
  emailUser: 'Fresh',
  matriculeUser: 'a',
  disponibiliteUser: true,
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  userRolesSopra: UserRolesSopra['MANAGER'],
};

export const sampleWithNewData: NewUserSopra = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
