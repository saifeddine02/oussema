import { IUserSopra } from 'app/entities/user-sopra/user-sopra.model';

export interface IEquipe {
  id: number;
  nomEquipe?: string | null;
  equipeusers?: Pick<IUserSopra, 'id'>[] | null;
}

export type NewEquipe = Omit<IEquipe, 'id'> & { id: null };
