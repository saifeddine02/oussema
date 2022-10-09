export interface IResponsable {
  id: number;
  nomResponsable?: string | null;
  prenomResponsable?: string | null;
}

export type NewResponsable = Omit<IResponsable, 'id'> & { id: null };
