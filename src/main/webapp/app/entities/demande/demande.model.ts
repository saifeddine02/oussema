import dayjs from 'dayjs/esm';
import { IUserSopra } from 'app/entities/user-sopra/user-sopra.model';

export interface IDemande {
  id: number;
  startDemande?: dayjs.Dayjs | null;
  endDemande?: dayjs.Dayjs | null;
  statusDemande?: dayjs.Dayjs | null;
  userSopra?: Pick<IUserSopra, 'id'> | null;
}

export type NewDemande = Omit<IDemande, 'id'> & { id: null };
