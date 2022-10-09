import { IResponsable } from 'app/entities/responsable/responsable.model';
import { IEquipe } from 'app/entities/equipe/equipe.model';
import { IProjet } from 'app/entities/projet/projet.model';
import { UserRolesSopra } from 'app/entities/enumerations/user-roles-sopra.model';

export interface IUserSopra {
  id: number;
  nomUser?: string | null;
  prenomUser?: string | null;
  emailUser?: string | null;
  matriculeUser?: string | null;
  disponibiliteUser?: boolean | null;
  image?: string | null;
  imageContentType?: string | null;
  userRolesSopra?: UserRolesSopra | null;
  responsable?: Pick<IResponsable, 'id'> | null;
  usersmembers?: Pick<IEquipe, 'id'>[] | null;
  usermembers?: Pick<IProjet, 'id'>[] | null;
}

export type NewUserSopra = Omit<IUserSopra, 'id'> & { id: null };
