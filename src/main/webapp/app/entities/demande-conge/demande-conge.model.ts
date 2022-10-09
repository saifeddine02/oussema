import dayjs from 'dayjs/esm';
import { StatusDemande } from 'app/entities/enumerations/status-demande.model';

export interface IDemandeConge {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  avisTl?: boolean | null;
  statusDemande?: StatusDemande | null;
  dateDebutConge?: dayjs.Dayjs | null;
  dateFinConge?: dayjs.Dayjs | null;
  nombreJour?: number | null;
}

export type NewDemandeConge = Omit<IDemandeConge, 'id'> & { id: null };
