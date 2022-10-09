import { ICompetance, NewCompetance } from './competance.model';

export const sampleWithRequiredData: ICompetance = {
  id: 86980,
};

export const sampleWithPartialData: ICompetance = {
  id: 73018,
  nomCompetance: 'Triple-buffered',
};

export const sampleWithFullData: ICompetance = {
  id: 36957,
  nomCompetance: 'AGP c',
  dureeCompetance: 18779,
  niveauCompetance: 5112,
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewCompetance = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
