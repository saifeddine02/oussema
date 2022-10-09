import { IUserSopra } from 'app/entities/user-sopra/user-sopra.model';

export interface ICompetance {
  id: number;
  nomCompetance?: string | null;
  dureeCompetance?: number | null;
  niveauCompetance?: number | null;
  image?: string | null;
  imageContentType?: string | null;
  userSopra?: Pick<IUserSopra, 'id'> | null;
}

export type NewCompetance = Omit<ICompetance, 'id'> & { id: null };
