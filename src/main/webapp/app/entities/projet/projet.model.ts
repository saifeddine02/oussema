import dayjs from 'dayjs/esm';
import { IUserSopra } from 'app/entities/user-sopra/user-sopra.model';

export interface IProjet {
  id: number;
  nomProjet?: string | null;
  paysProjet?: string | null;
  regionProjet?: string | null;
  dateDebut?: dayjs.Dayjs | null;
  nombreRessource?: number | null;
  dateFin?: dayjs.Dayjs | null;
  competanceDemander?: string | null;
  projectMenbers?: Pick<IUserSopra, 'id'>[] | null;
}

export type NewProjet = Omit<IProjet, 'id'> & { id: null };
